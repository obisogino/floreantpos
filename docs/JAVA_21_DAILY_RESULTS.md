# Java 21 daily results

This file is historical evidence, not the task queue. Select current work from
the numbered checkboxes in main-plan section 6. Day summaries follow those
tasks. Old status statements below never override current task evidence.

For each requested task/day, record its ID, requested scope, findings, relevant
verification, artifacts, blockers and next unfinished ID. A review request alone
does not authorize implementation. The user chooses one task, multiple tasks
or a whole planned day; there is no one-task-per-calendar-day limit.

## Setup — 26 October 2026

- Read `NumberUtil`, `PasswordHasher`, `DiscountUtil`, and their model inputs.
- Added 20 unit tests against current production code; no pricing/login logic changed.
- Added JUnit 5.13.4 in test scope, Surefire 3.5.4, and the separate `test/` root.
- Baseline: JDK 17.0.15; focused Maven compiler/test goals; **20 passed, 0 failed, 0 skipped**.
- Runtime comparison: the same compiled tests also pass on JDK 21.0.7 via `surefire:test`; this does not change the production compiler target.
- Observed: negative default money values display as zero; three-place values keep negatives; `2.675` rounds to `2.67`; item percent discount uses a unit price; minimum quantities count complete groups.
- Initial test setup is completed prework. The prior Day 1 Done label was incorrect; baseline ZIP/data/UI records remain unfinished under Day 1.
- Append before/after commands, results, blockers and next action after a manually requested task or day.
- Correction: the user wants documentation, not automated work. The timer created by mistake is disabled and the service stopped. Its empty start timestamp confirms no automated migration run occurred.
- Keep existing files and build changes. Do not run more tests or migration work until explicitly requested.

## Coverage plan update — 26 October 2026

- Read-only package/entrypoint inventory added; no tests were run for this update.
- Added `JAVA_21_SYSTEM_COVERAGE.md` with 30 system areas, review state, assigned days and behavior checks.
- The whole system remains unverified. Missing permissions, cash, shifts, customer/booking/delivery, kitchen, network/proxy, settings and vendor-code checks are now explicit plan items.
- Initial test setup covers only the three helpers; Day 1 baseline tasks and other required reviews remain unfinished.

## Daily-use plan update — 26 October 2026

- Keep using the current verified app while migration work and copied data stay separate.
- Added a daily runnable gate: app-changing days need a saved runnable candidate before Done; otherwise the previous verified package remains available.
- Java 21 candidate is targeted for Day 3; final daily-use replacement still needs whole-system acceptance.
- Documentation update only; no app changes, builds or tests were run.

## Automation cleanup — 27 October 2026

- Removed the Python migration runner, its Python tests, and the local timer/service files at the user's request.
- The workflow is manual: request tasks or a day in chat, then update Markdown after the selected work and checks are complete.
- Existing Java behavior tests, test setup, app code and migration documentation remain in place.
- No builds or tests were run during this cleanup. No automated migration run occurred.

## Day status correction — 27 October 2026

- Day 1 is **Todo**, not Done. The earlier status incorrectly counted initial test setup as a completed day.
- Completed prework: JUnit/Surefire setup and 20 tests covering number formatting, password hashes and discounts, with historical JDK 17/21 results.
- Still Todo for Day 1: preserve the app ZIP and hashes, record tools/dependencies/build provenance, preserve data/plugin inputs, capture UI/work flows and record used features/known failures.
- The interrupted single-task attempt only inspected files and archive hashes; it did not copy a baseline, run tests or change application code.
- Documentation was corrected only. Do not advance to Day 2 until Day 1's required tasks are complete.

## Task-selection review — 27 October 2026

- Reviewed the four migration Markdown files and project README; found conflicting day-only commands and duplicate task grouping.
- Main-plan section 6 now has stable task IDs and is the only task-completion queue. Day summaries, generic checklists and historical results do not choose the next task.
- Chat commands support one task, a whole day, a stated task count or an explicit ID/day. The user may request more work on the same calendar day.
- Added root `AGENTS.md` so future sessions can find the rules, inspect prerequisites, stay in scope and update evidence before marking Done.
- First unfinished task remains **D01.01**: preserve the app ZIP outside `target/` and verify its hash. It has not been executed.
- This update changed Markdown only. No app builds, tests, migration tasks, commits, pushes or background jobs ran.
