# Java 21 daily results

## Setup — 26 October 2026

- Read `NumberUtil`, `PasswordHasher`, `DiscountUtil`, and their model inputs.
- Added 20 unit tests against current production code; no pricing/login logic changed.
- Added JUnit 5.13.4 in test scope, Surefire 3.5.4, and the separate `test/` root.
- Baseline: JDK 17.0.15; focused Maven compiler/test goals; **20 passed, 0 failed, 0 skipped**.
- Runtime comparison: the same compiled tests also pass on JDK 21.0.7 via `surefire:test`; this does not change the production compiler target.
- Observed: negative default money values display as zero; three-place values keep negatives; `2.675` rounds to `2.67`; item percent discount uses a unit price; minimum quantities count complete groups.
- Manual tracker: Day 1 is Done for this initial test setup; baseline ZIP/UI capture and fixture work remain Todo under Day 2.
- Append before/after commands, results, blockers, and next action only after a manually requested workday.
- Correction: the user wants documentation, not automated work. The timer created by mistake is disabled and the service stopped. Its empty start timestamp confirms no automated migration run occurred.
- Keep existing files and build changes. Do not run more tests or migration work until explicitly requested.

## Coverage plan update — 26 October 2026

- Read-only package/entrypoint inventory added; no tests were run for this update.
- Added `JAVA_21_SYSTEM_COVERAGE.md` with 30 system areas, review state, assigned days and behavior checks.
- The whole system remains unverified. Missing permissions, cash, shifts, customer/booking/delivery, kitchen, network/proxy, settings and vendor-code checks are now explicit plan items.
- Day 1 remains Done only for initial test setup; other required reviews remain Todo or partial.

## Daily-use plan update — 26 October 2026

- Keep using the current verified app while migration work and copied data stay separate.
- Added a daily runnable gate: app-changing days need a saved runnable candidate before Done; otherwise the previous verified package remains available.
- Java 21 candidate is targeted for Day 3; final daily-use replacement still needs whole-system acceptance.
- Documentation update only; no app changes, builds or tests were run.
