# Java 21: manual daily checklist

## Current instruction

This is documentation only. **Do not run tests, change dependencies, or start
background work from this checklist.** Work starts only when you ask for a day.
Keep the test files and build changes already made; do not revert them.

## Status

| Setting | Value |
| --- | --- |
| Day 1 | **Done** — initial behavior tests and test setup |
| Days 2–20 | **Todo** — start each day manually when requested |
| Pace | Four weeks of planned work; dates are flexible |
| Usage | Target at most 20% of the day's allowance; checked manually |
| Progress | Update the status below after the requested day's work |
| Automation | **Disabled and stopped. No automated migration run occurred.** |

The timer was created by mistake and disabled before its first run. Existing
helper files and the separate checkout remain untouched. They are not active.

## Can the app be used every day?

**Yes: keep using the last verified app while migration work stays separate.**
You do not need to wait until Day 20 to use the POS. A work-in-progress migration
build can fail; it must not replace your working app or use its live database.

| Point in the plan | App available for use |
| --- | --- |
| Days 1–2 | Existing working app with its existing Java runtime; preserve its ZIP and data backup |
| Day 3 onward | First Java 21 candidate is targeted for Day 3; use it for copied-data checks only after its daily gate passes |
| After each completed implementation day | Keep a named, runnable checkpoint with its exact JDK, flags, package and check results |
| A change fails or time runs out | Keep the previous verified package available; mark the day's migration work Blocked or Todo |
| Final acceptance | After all required system checks pass, decide separately when to replace the daily-use app |

Day 1's Done label covers test setup only. From Day 2 onward, verify and save the
working app baseline before changing app code. For days that change the app,
the daily runnable gate is required before marking Done:

- [ ] Build/package the candidate and start it outside the IDE using its recorded JDK and flags.
- [ ] On copied data, check login, menu/order entry, ticket save/reopen, payment recording without real charges, and a used receipt/report.
- [ ] Check the affected module plus earlier migration checks; include inventory/plugin paths when enabled.
- [ ] Confirm the usual screen shapes and work flow remain the same.
- [ ] Save the package, exact launch command, data-fixture version, results and known issues outside disposable build output.
- [ ] Keep the previous verified package; keep unfinished work separate and leave the day open.

For a documentation-only day, reference the last verified package instead of
rebuilding it. A daily candidate passing these checks is not yet proof that all
devices, databases and production work flows passed final acceptance. No build
can be promised to have zero issues; the saved working version is the fallback.

## Whole-system coverage

The whole system has **not** been checked yet. Use the
[system coverage checklist](JAVA_21_SYSTEM_COVERAGE.md) for all 30 areas,
their current review state, assigned days, and required behavior checks.
It adds permissions, back-office edits, shifts, cash accounting, customers,
bookings, delivery, kitchen display, settings, encoding, POS servers/proxies,
vendor code, and resource cleanup to the existing migration checks.

Before each day, read its assigned coverage rows. Record the behavior, write
the test, then change that module. Day 1 is Done for the initial tests only.
Unreviewed features stay Todo; checks do not disappear to meet the day limit.

## Every day: understand, test, change, test again

1. Read the affected method, its callers, and saved fixtures. Write down the
   current inputs, outputs, side effects, and failure behavior.
2. **Write or extend a real test before changing that module or dependency.**
   Use the current app classes, not a copy of their logic.
3. Run the test on the unchanged app and save the result. Capture old report/UI
   output when needed. Expected values must come from observed behavior and
   checked examples, not guesses about the desired future app.
4. If the baseline fails, save a failing reproduction and mark the day blocked.
   Fix a known old bug separately; do not hide it in a dependency update.
5. Make the day's small change. Run **the same tests** again, then the relevant
   earlier tests. Keep the UI shape and business rules unchanged.
6. Record before/after evidence. Mark the day done only when the task and tests
   pass. Otherwise leave it Todo or mark it Blocked for the next manual session.

Each day must add or extend at least one affected-module test. A packaging,
launch, UI, or process integration test counts; a manual check alone does not.
Hardware tests still need the actual device. Do not mark a stub as device proof.
Keep source/JDK differences visible: tests failing before a change are not new
migration regressions, and tests only run after a change are not a baseline.

## Days and required tests

Day numbers are work items, not automatic dates. A blocked day stays open.
Future test names below are planned, not existing passing tests.

| Day | Status | Affected module / task | Test to write or extend before the change |
| --- | --- | --- | --- |
| Day 1 | Done | Baseline: money display, login, discounts | Added 20 tests in `NumberUtilBehaviorTest`, `PasswordHasherBehaviorTest`, and `DiscountUtilBehaviorTest`; existing results recorded |
| Day 2 | Todo | System inventory, saved baseline, fixtures and inventory import | Review coverage rows, used features/settings, ZIP/data/UI samples; add inventory CSV/isolation tests and list existing failures |
| Day 3 | Todo | Java 21 build, boot, Hibernate | `PersistenceBehaviorIT`: ticket with children, reload, rollback, persisted totals; compare JDKs; add restart/flag test |
| Day 4 | Todo | Xerces/parser routes | `XmlParserBehaviorIT`: same mapping/import/report fixtures on both parsers; keep namespaces, field values and invalid-input behavior |
| Day 5 | Todo | Jasper/ECJ and report compile | `ReportTemplateBehaviorIT`: compile/fill templates with fixed data; compare text, totals, pages and paper sizes; reproduce schema error first |
| Day 6 | Todo | JAXB runtime | `JaxbRoundTripBehaviorIT`: old menu/config/POS XML fixtures; same field names, fractions, accents and peso sign |
| Day 7 | Todo | Logging | `LoggingBehaviorIT`: app/plugin events appear once; message, cause, file path and rollover stay correct |
| Day 8 | Todo | HTTP clients, maps/update calls, POS/proxy services | Local HTTP/socket stubs capture real payloads, encoding, headers, responses, errors and timeouts; include bundled Mercury client calls |
| Day 9 | Todo | JNA native calls | `NativeDeviceBehaviorIT`: real native load and used OS/device calls before/after; missing hardware stays blocked |
| Day 10 | Todo | JSSC scale/drawer routes | `SerialPortBehaviorTest`: actual command bytes, weight text, timeout and port close through a small test seam; real-device proof separately |
| Day 11 | Todo | JSPF and inventory plugin | `InventoryPluginBehaviorIT`: discovery, alternate `pluginsPath`, menu actions, current license and missing-plugin rules |
| Day 12 | Todo | Inventory, ordering and business work flows | Fractional stock/recipes; menu/modifier/pizza/tax rules; ticket/table/customer/delivery states; cash, tips, shifts and kitchen states; separate tests per coverage row |
| Day 13 | Todo | Derby/MySQL/PostgreSQL | Extend persistence tests across available engines: transaction, rollback, ID, date and decimal fixtures |
| Day 14 | Todo | Packaging and launchers | `DistributionBehaviorIT`: both profile ZIPs, paths with spaces, resources, vendor/plugin inputs and actual launch |
| Day 15 | Todo | UI, permissions, back office, employee/customer screens and settings | Same bounds/theme/fonts/shortcuts; allowed/denied actions; edit/save/reopen; bookings, driver/attendance, locale and saved settings |
| Day 16 | Todo | Devices, gateways, cash accounting and payment services | Approve/decline/cancel/timeout; custom/gift/tip/refund/void effects; drawer balances; POS/proxy requests; repeat calls do not add duplicate side effects |
| Day 17 | Todo | Upgrade copy and rollback | `UpgradeRestoreBehaviorIT`: copied old data/config, schema/ID/balance checks, restore old app plus old DB |
| Day 18 | Todo | Long session, kitchen/network workers and CI | Repeat real actions; close sessions, kitchen timers, windows, sockets and ports; no duplicate effects or stuck workers |
| Day 19 | Todo | Blocker fix and frozen package | Add a failing test for the blocker before fixing it; otherwise extend package tests for the clean candidate |
| Day 20 | Todo | Whole-system review, candidate and handover | Existing fixtures through the package; every used coverage row has evidence; remaining required blockers keep approval open |

## Progress

- [x] Day 1: Done — initial behavior tests and test setup
- [ ] Day 2: Todo — baseline files, inventory import and fixture isolation
- [ ] Day 3: Todo — Java 21 build, boot, persistence, restart
- [ ] Day 4: Todo — XML parser behavior
- [ ] Day 5: Todo — report compilation and output
- [ ] Day 6: Todo — JAXB file and wire compatibility
- [ ] Day 7: Todo — logging routes and output
- [ ] Day 8: Todo — HTTP contracts and timeouts
- [ ] Day 9: Todo — native library behavior
- [ ] Day 10: Todo — serial protocol and device behavior
- [ ] Day 11: Todo — plugin actions and compatibility
- [ ] Day 12: Todo — inventory and ticket side effects
- [ ] Day 13: Todo — database behavior by engine
- [ ] Day 14: Todo — packaged app and launch behavior
- [ ] Day 15: Todo — UI shapes and work flow
- [ ] Day 16: Todo — devices and payment side effects
- [ ] Day 17: Todo — upgrade copy and rollback
- [ ] Day 18: Todo — resource lifetime and CI
- [ ] Day 19: Todo — blocker regression and candidate freeze
- [ ] Day 20: Todo — candidate checks and handover

## Tests already written during setup

These files and results predate the instruction to stop testing in this chat.
Keep them as the starting point; do not rerun them during plan edits. Review and
extend them only when the relevant implementation day is requested.

20 tests pass on both JDK 17 and JDK 21 against current app code. Compilation
still uses JDK 17 and the old Java 7 target; the Java 21 check runs the same
compiled tests on that runtime. Runtime libraries were kept at their old
versions. The suite checks:

| Test class | Count | Examples of current behavior kept |
| --- | --- | --- |
| `NumberUtilBehaviorTest` | 11 | Hide negative money by default; keep negative three-place values; null is zero; `2.675` rounds to `2.67`; grouping and five-place limit |
| `PasswordHasherBehaviorTest` | 3 | Existing numeric PIN, empty value and UTF-8 password produce the same stored hashes |
| `DiscountUtilBehaviorTest` | 6 | Fixed/percent ticket discounts; per-unit item percent; complete minimum-quantity groups; below-minimum gets zero |

These tests document existing rules. They do not endorse old password storage
or decide whether a surprising rounding/discount rule is correct. Any business
rule change needs a separate task and revised examples.

Before the Java 21 build step, use installed JDK 17 to compile the unchanged
Java 7 target. This command runs only the focused compiler/test goals and avoids
the old validation step that unpacks seed data:

```sh
JAVA_HOME=/home/gino/.jdks/ms-17.0.15 \
  /home/gino/.m2/wrapper/dists/apache-maven-3.9.9-bin/4nf9hui3q3djbarqar9g711ggc/apache-maven-3.9.9/bin/mvn \
  -B -ntp resources:resources compiler:compile compiler:testCompile surefire:test \
  -Dtest=NumberUtilBehaviorTest,PasswordHasherBehaviorTest,DiscountUtilBehaviorTest
```

## Daily evidence to save

After a manually requested workday, append an entry to `docs/JAVA_21_DAILY_RESULTS.md`:

| Item | Record |
| --- | --- |
| Run | Date, task ID, time, JDK, dependency before/after |
| Read | Source file, method, caller and fixture |
| Learned | Concrete current inputs, outputs, side effects and failures |
| Before | New/extended test, exact command, unchanged-app result |
| After | Same test command/result, extra regression results |
| Runnable app | Saved package, JDK/flags, exact start command and daily gate result |
| Status | Done / Todo / Blocked, with test evidence |
| Next | Remaining checkbox and exact next command |

## How to continue

Ask for a specific day, for example: **"Start Day 2."** Until then, update the
documentation only. No timed task, test command, or migration step should run.
