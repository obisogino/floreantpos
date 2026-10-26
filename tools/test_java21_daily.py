import contextlib
import datetime as dt
import importlib.util
import io
from pathlib import Path
import tempfile
import unittest
from unittest.mock import patch

spec = importlib.util.spec_from_file_location("daily", Path(__file__).with_name("run_java21_daily.py"))
daily = importlib.util.module_from_spec(spec)
spec.loader.exec_module(daily)


class DailyScheduleTest(unittest.TestCase):
    def test_calendar_has_exactly_twenty_weekdays(self):
        days = [daily.START + dt.timedelta(days=n)
                for n in range((daily.END - daily.START).days + 1)]
        self.assertEqual(20, sum(daily.scheduled_day(day) for day in days))

    def test_weekends_and_dates_outside_window_do_not_run(self):
        for value in ("2026-10-26", "2026-10-31", "2026-11-01", "2026-11-24"):
            self.assertFalse(daily.scheduled_day(dt.date.fromisoformat(value)))

    def test_resume_uses_first_unfinished_task_not_todays_number(self):
        text = "- [x] D01 — done\n- [ ] D02 — blocked\n- [ ] D03 — next\n"
        self.assertEqual("D02", daily.next_task(text))

    def test_complete_ledger_does_not_start_more_work(self):
        self.assertIsNone(daily.next_task("- [x] D01 — done\n"))

    def test_prompt_requires_before_and_after_behavior_checks(self):
        prompt = daily.make_prompt("D04")
        self.assertIn("unchanged app code", prompt)
        self.assertIn("SAME tests after", prompt)
        self.assertIn("leave it unchecked", prompt)

    def test_dry_run_does_not_spawn_codex_or_create_logs(self):
        with tempfile.TemporaryDirectory() as folder:
            root = Path(folder)
            ledger = root / daily.LEDGER
            ledger.parent.mkdir()
            ledger.write_text("- [ ] D01 — first\n")
            logs = root / "logs"
            with patch.object(daily, "find_codex", return_value="/mock/codex"), \
                    patch.object(daily.subprocess, "Popen") as spawn, \
                    contextlib.redirect_stdout(io.StringIO()):
                result = daily.main(["--project", str(root), "--log-dir", str(logs),
                                     "--date", "2026-10-27", "--dry-run"])
            self.assertEqual(0, result)
            spawn.assert_not_called()
            self.assertFalse(logs.exists())

    def test_date_override_cannot_start_a_real_session(self):
        with contextlib.redirect_stderr(io.StringIO()), self.assertRaises(SystemExit):
            daily.main(["--project", ".", "--log-dir", "logs", "--date", "2026-10-27"])

    def test_same_date_is_attempted_only_once(self):
        with tempfile.TemporaryDirectory() as folder:
            root = Path(folder)
            ledger = root / daily.LEDGER
            ledger.parent.mkdir()
            ledger.write_text("- [ ] D01 — first\n")
            with patch.object(daily, "scheduled_day", return_value=True), \
                    patch.object(daily, "find_codex", return_value="/mock/codex"), \
                    patch.object(daily.subprocess, "Popen") as spawn, \
                    contextlib.redirect_stdout(io.StringIO()):
                spawn.return_value.returncode = 0
                args = ["--project", str(root), "--log-dir", str(root / "logs")]
                self.assertEqual(0, daily.main(args))
                self.assertEqual(0, daily.main(args))
                self.assertEqual(1, spawn.call_count)

    def test_timeout_stops_process_and_preserves_unfinished_task(self):
        with tempfile.TemporaryDirectory() as folder:
            root = Path(folder)
            ledger = root / daily.LEDGER
            ledger.parent.mkdir()
            original = "- [ ] D01 — first\n"
            ledger.write_text(original)
            with patch.object(daily, "scheduled_day", return_value=True), \
                    patch.object(daily, "find_codex", return_value="/mock/codex"), \
                    patch.object(daily.subprocess, "Popen") as spawn, \
                    patch.object(daily, "stop_process") as stop, \
                    contextlib.redirect_stdout(io.StringIO()):
                spawn.return_value.communicate.side_effect = daily.subprocess.TimeoutExpired("codex", 3600)
                spawn.return_value.returncode = -2
                result = daily.main(["--project", str(root), "--log-dir", str(root / "logs")])
                self.assertEqual(1, result)
                stop.assert_called_once_with(spawn.return_value)
            self.assertEqual(original, ledger.read_text())


if __name__ == "__main__":
    unittest.main()
