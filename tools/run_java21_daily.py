#!/usr/bin/env python3
"""Run one behavior-first migration step on each of 20 scheduled weekdays."""

import argparse
import datetime as dt
import fcntl
import json
import os
from pathlib import Path
import re
import shutil
import signal
import subprocess
import sys
from zoneinfo import ZoneInfo

START = dt.date(2026, 10, 27)
END = dt.date(2026, 11, 23)
ZONE = ZoneInfo("Asia/Manila")
LEDGER = Path("docs/JAVA_21_DAILY_TASKS.md")


def scheduled_day(day):
    return START <= day <= END and day.weekday() < 5


def next_task(text):
    match = re.search(r"^- \[ \] (D\d{2})\b", text, re.MULTILINE)
    return match.group(1) if match else None


def find_codex(requested):
    if requested and Path(requested).is_file():
        return str(Path(requested).resolve())
    found = shutil.which("codex")
    if found:
        return found
    base = Path.home() / ".var/app/com.visualstudio.code/data/vscode/extensions"
    candidates = list(base.glob("openai.chatgpt-*/bin/linux-x86_64/codex"))
    if candidates:
        return str(max(candidates, key=lambda p: p.stat().st_mtime))
    raise FileNotFoundError("Codex CLI was not found; no model call was made")


def make_prompt(task):
    return """Work on exactly {task} from docs/JAVA_21_DAILY_TASKS.md.
Read that file, docs/JAVA_21_MIGRATION_PLAN.md, and applicable AGENTS.md first.
Stay in this dedicated checkout. Read the affected producer, consumer, and
existing tests. Explain the current behavior with concrete inputs and outputs.
Before any dependency or app change, write or extend at least one meaningful
unit or behavior test for that module. Run it against unchanged app code and
record the baseline command, JDK, and result. Preserve observed rules, including
quirks. Record suspected bugs separately; do not silently fix business behavior.
If the baseline cannot be run or expected behavior is unclear, record BLOCKED
and stop before changing that module. A prior known failure needs a failing
repro first and a separate, explicit fix; do not hide it in a version bump.
Then make only the day's planned change, one dependency family at a time.
Run the SAME tests after the change, plus relevant earlier regressions.
Use real application classes, copied DBs, synthetic data, local service stubs,
and sandbox payment accounts. Never use live DBs or real payments.
Keep UI shape, theme, font, component bounds, icons and workflow unchanged.
Do not disable validation/tests or regenerate expected results to make tests pass.
Retain unrelated user work. Do not push, merge, publish, or deploy.
No subagents and no new work after this one day. Use GPT-6.1 Sol / High and
normal speed. The 20% allowance cap is manual: check it if available, otherwise
report usage unknown and keep to one small task. Do not claim it is enforced.
The runner has a 60-minute limit; aim to finish within 45 minutes and save notes.
Use installed JDK 17 until the Java 21 build step; JDK 21 is available at
/home/gino/.local/share/JetBrains/idea-IU-251.26094.121/jbr. Record a maintained
Temurin build for release; save its path in .java21-java-home when ready.
At the end update this checkout's daily checklist and append a dated record to
docs/JAVA_21_DAILY_RESULTS.md: source/method read, behavior learned, test added,
before/after command and result, files changed, blockers, and next exact command.
Mark {task} done only when its code/tests and required checks are complete.
If blocked or out of budget, leave it unchecked for the next scheduled run.
Do not advance by the calendar date or attempt several missed days at once.
Return a short result with the tests passed/failed/blocked and paths to evidence.
""".format(task=task)


def stop_process(process):
    for sig, wait in ((signal.SIGINT, 45), (signal.SIGTERM, 10), (signal.SIGKILL, 10)):
        if process.poll() is not None:
            return
        try:
            os.killpg(process.pid, sig)
        except ProcessLookupError:
            return
        try:
            process.wait(timeout=wait)
            return
        except subprocess.TimeoutExpired:
            pass


def main(argv=None):
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--project", type=Path, required=True)
    parser.add_argument("--log-dir", type=Path, required=True)
    parser.add_argument("--codex")
    parser.add_argument("--dry-run", action="store_true")
    parser.add_argument("--date", type=dt.date.fromisoformat,
                        help="Override date only for a dry run")
    args = parser.parse_args(argv)
    if args.date and not args.dry_run:
        parser.error("--date requires --dry-run")
    today = args.date or dt.datetime.now(ZONE).date()
    if not scheduled_day(today):
        print("No run: outside the scheduled weekdays")
        return 0
    project = args.project.resolve(strict=True)
    task = next_task((project / LEDGER).read_text())
    if not task:
        print("No run: all daily tasks are complete")
        return 0
    codex = find_codex(args.codex)
    run_dir = args.log_dir.resolve() / today.isoformat()
    command = [codex, "-a", "never", "exec", "-m", "gpt-6.1-sol",
               "-c", 'model_reasoning_effort="high"',
               "-c", "sandbox_workspace_write.network_access=true",
               "-s", "workspace-write", "-C", str(project),
               "--add-dir", str(Path.home() / ".m2"),
               "--add-dir", str(args.log_dir.resolve()),
               "--json", "-o", str(run_dir / "result.md"), "-"]
    if args.dry_run:
        print(json.dumps({"date": today.isoformat(), "task": task,
                          "project": str(project), "command": command,
                          "model_call_made": False}, indent=2))
        return 0
    args.log_dir.mkdir(parents=True, exist_ok=True)
    with (args.log_dir / ".lock").open("a") as lock:
        try:
            fcntl.flock(lock, fcntl.LOCK_EX | fcntl.LOCK_NB)
        except BlockingIOError:
            print("No run: another migration session is active")
            return 0
        run_dir.mkdir(parents=True, exist_ok=True)
        attempt = run_dir / "attempt.json"
        if attempt.exists():
            print("No run: today's session was already attempted")
            return 0
        record = {"date": today.isoformat(), "task": task, "status": "started",
                  "project": str(project), "usage_cap": "manual, not enforced"}
        attempt.write_text(json.dumps(record, indent=2) + "\n")
        env = os.environ.copy()
        java_file = project / ".java21-java-home"
        java_home = (java_file.read_text().strip() if java_file.exists()
                     else str(Path.home() / ".jdks/ms-17.0.15"))
        env["JAVA_HOME"] = java_home
        mvn_bin = Path.home() / ".m2/wrapper/dists/apache-maven-3.9.9-bin/4nf9hui3q3djbarqar9g711ggc/apache-maven-3.9.9/bin"
        env["PATH"] = str(Path(java_home) / "bin") + ":" + str(mvn_bin) + ":" + env.get("PATH", "")
        try:
            with (run_dir / "events.jsonl").open("w") as events, (run_dir / "stderr.log").open("w") as errors:
                process = subprocess.Popen(command, cwd=project, env=env,
                                           stdin=subprocess.PIPE, stdout=events,
                                           stderr=errors, text=True, start_new_session=True)
                try:
                    process.communicate(make_prompt(task), timeout=3600)
                    record.update(status="finished" if process.returncode == 0 else "failed",
                                  exit_code=process.returncode)
                except subprocess.TimeoutExpired:
                    stop_process(process)
                    record.update(status="time_limit", exit_code=process.returncode)
        except OSError as error:
            record.update(status="failed_to_start", error=str(error))
        attempt.write_text(json.dumps(record, indent=2) + "\n")
        print(json.dumps(record))
        return 0 if record["status"] == "finished" else 1


if __name__ == "__main__":
    sys.exit(main())
