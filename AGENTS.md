# Java 21 migration work

These rules apply to the Java 21 migration. Follow the user's current request
and any more specific instructions first. Preserve unrelated local changes.

## Choose work from chat

- A planning question, quoted command example, or request to edit Markdown does
  not authorize implementation or tests.
- **"Run a task"**, **"do a task"**, **"next task"**, or **"do a single task"**:
  complete one first-unfinished numbered task.
- **"Run a day"** or **"do a day"**: complete the remaining tasks in the first
  unfinished planned day.
- **"Run 3 tasks"**: complete up to three unfinished tasks in order.
- An explicit task ID or day number selects that work. Check prerequisites;
  explain a real blocker instead of silently doing a different day.
- Day numbers group work; they are not calendar dates or a one-task-per-day
  limit. The user chooses the amount and can request more work the same day.

## Source of truth and order

1. Read `docs/JAVA_21_DAILY_TASKS.md` for execution rules.
2. Select work from the numbered checkboxes in **section 6** of
   `docs/JAVA_21_MIGRATION_PLAN.md`, in listed order: `D01.01`, `D01.02`, etc.
3. Use `docs/JAVA_21_SYSTEM_COVERAGE.md` to find related modules/checks and
   `docs/JAVA_21_DAILY_RESULTS.md` to verify prior evidence and blockers.
4. Day summaries, generic acceptance checklists, historical log entries,
   prework, and the later modernization roadmap are not the task queue.

## Execute the selected scope

- Announce the selected ID/day and inspect the actual files, callers, fixtures,
  Git status and existing results before deciding how to implement it.
- Resolve discoverable facts and small necessary prerequisites yourself. Ask
  only for missing information that genuinely blocks the selected work.
- For app/module changes, capture current behavior in tests before editing,
  then run the same tests after the change. For file backups, verify the copy
  and hashes; do not add unrelated app tests.
- Respect any explicit "do not run tests" limit. Work requiring those results
  stays unverified; do not mark it Done.
- If the first unfinished task is blocked or partial, resume it first; do not
  silently skip it. Stop and record a blocker if it cannot be resolved in scope.
- Finish only the requested amount. If a task is too large, split it into
  ordered child tasks first; one-task requests cover one unfinished leaf task.
- Honor the daily budget; report unknown allowance honestly. The user can
  change scope/budget explicitly. Do not claim a hard token cap is enforced.
- Keep the UI and business behavior unchanged. Use copied data, preserve the
  working app, and keep unrelated work intact.
- Store shareable migration baselines inside `baselines/`, outside `target/`,
  so they can be committed and cloned. Use repository-relative artifact paths.
  Do not create new baseline storage outside the workspace unless requested.
- Do not recreate automation, background jobs, or the removed Python runner.
  Do not commit, push, merge, deploy, or create agents unless requested.

## Record progress

- Check a numbered task only after its required work and verification finish.
- Add the task ID, behavior learned, commands/results, artifacts, blockers and
  next unfinished task to `docs/JAVA_21_DAILY_RESULTS.md`.
- Update the day summaries in the daily tracker. Mark a day Done only when all
  its numbered tasks and applicable daily gates pass. Existing prework alone
  never completes Day 1.
- Report the result and next task. Do not start more work after that report
  without another execution request.
