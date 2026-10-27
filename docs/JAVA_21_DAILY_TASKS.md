# Java 21: manual daily checklist

## Current instruction

Reading or updating this plan does not start implementation or tests. A direct
chat request such as **"run a task"** or **"run a day"** starts only the chosen
amount of work. Follow any explicit limits in that request. Keep existing tests
and unrelated local work intact. No scripts, timer or background migration job
should be created.

## Chat commands and task selection

| Your request | What to do |
| --- | --- |
| "Run a task", "do a task", "next task", "do a single task" | Complete one first-unfinished numbered task |
| "Run a day", "do a day" | Complete remaining tasks in the first unfinished planned day |
| "Run 3 tasks" | Complete up to three unfinished tasks in order |
| "Run D01.03" or "run Day 2" | Select that task/day; inspect prerequisites and explain any real blocker |
| "Review the plan", a question, or a quoted command example | Inspect/update documentation only; do not start the quoted work |

You choose the amount. Day numbers are planning groups, not dates or a limit on
how often you may request work. You can run one task, several tasks, or a day,
and request more work on the same calendar day. A request for one task must not
grow into the whole day; a request for a day must not stop after one task unless
blocked or limited by your budget/instructions.

### Source of truth

| File | Purpose |
| --- | --- |
| [Main plan, section 6](JAVA_21_MIGRATION_PLAN.md#6-four-week-checklist) | **Authoritative numbered task queue and completion checkboxes** |
| This daily tracker | Command meanings, day summaries and daily acceptance rules |
| [System coverage](JAVA_21_SYSTEM_COVERAGE.md) | Related modules and required behavior checks |
| [Daily results](JAVA_21_DAILY_RESULTS.md) | Historical evidence, blockers and next-action notes |
| [Project instructions](../AGENTS.md) | Entry instructions for future Codex sessions |

Select only task checkboxes with IDs such as `D01.01` in main-plan section 6,
in listed order. Do not select generic acceptance checkboxes, day summaries,
completed prework, old log statements or future Path B items as the next task.
The day summaries below must follow the numbered tasks, never override them.

### How to work without extra steering

1. Read the next task and its latest evidence. Inspect Git status, relevant
   source/callers, artifacts and fixtures. If a checked task lacks evidence,
   correct it to unfinished rather than skipping required work.
2. Announce the selected ID and inspect what is actually needed. Resolve
   routine choices and discoverable facts yourself. Do not ask the user where a
   file is or which small implementation step to use when the repo answers it.
3. Do only the selected scope and its small necessary prerequisites. For an
   oversized task, first split it into ordered child tasks; a one-task request
   then completes one unfinished child. Do not silently reorder the plan.
4. For an app change, write/extend a current-behavior test, run it before the
   change, then run it again afterward. For a backup, check the copied file and
   hash; do not launch unrelated app tests. Honor explicit test restrictions.
5. If the first task is partial or blocked, resume it first. Resolve the blocker
   within scope if possible; otherwise record it and ask only for the genuinely
   missing input. Do not silently move to the next task/day.
6. Check the task only after its required evidence is complete. Update the
   results and day summary. A day is Done only when all its tasks and applicable
   daily gates pass; removed tasks are not counted as completed.
7. Stop at the requested amount or explicit budget. Report results and the next
   task. Do not commit, push, deploy or start another work group unless asked.

## Status

| Setting | Value |
| --- | --- |
| Day 1 | **Closed** — 3 tasks done; 3 standalone recording tasks removed, not completed |
| Days 2–20 | **Todo** — D03.01 compiler change complete; remaining tasks unfinished |
| Pace | Four weeks of planned work; dates are flexible |
| Usage | Target at most 20% of the day's allowance; checked manually |
| Progress | Update the status below after the requested day's work |
| Automation | **Removed. Work is requested manually in chat.** |

The automation scripts and timer have been removed. No automated migration run
occurred. Keep using this chat and this checklist to choose and track the work.

## Can the app be used every day?

**Yes: keep using the last verified app while migration work stays separate.**
You do not need to wait until Day 20 to use the POS. A work-in-progress migration
build can fail; it must not replace your working app or use its live database.

| Point in the plan | App available for use |
| --- | --- |
| Days 1–2 | Existing working app with its existing Java runtime |
| Day 3 onward | First Java 21 candidate is targeted for Day 3; use it for copied-data checks only after its daily gate passes |
| After each completed implementation day | Verify the candidate works and keep the existing working app available |
| A change fails or time runs out | Keep the previous verified package available; mark the day's migration work Blocked or Todo |
| Final acceptance | After all required system checks pass, decide separately when to replace the daily-use app |

Day 1 is closed under the reduced scope; its removed recording tasks were not
done. For days that change the app, the daily runnable gate is required before
marking Done:

- [ ] Build/package the candidate and start it outside the IDE using its recorded JDK and flags.
- [ ] On copied data, check login, menu/order entry, ticket save/reopen, payment recording without real charges, and a used receipt/report.
- [ ] Check the affected module plus earlier migration checks; include inventory/plugin paths when enabled.
- [ ] Confirm the usual screen shapes and work flow remain the same.
- [ ] Note the launch command and test result briefly in the existing results log.
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

Before each day, read its assigned coverage rows. Understand the behavior, write
the test, then change that module.
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

Each app-changing day must add or extend affected-module behavior tests. A
packaging, launch, UI or process integration test counts. For preservation or
documentation tasks, verify artifacts/records without inventing unrelated tests.
Hardware tests still need the actual device. Do not mark a stub as device proof.
Keep source/JDK differences visible: tests failing before a change are not new
migration regressions, and tests only run after a change are not a baseline.

## Days and required tests

Day numbers are work items, not automatic dates. A blocked day stays open.
Future test names below are planned, not existing passing tests.

| Day | Status | Affected module / task | Test to write or extend before the change |
| --- | --- | --- | --- |
| Day 1 | Closed — 3 done; 3 removed | Initial preservation | UI and feature checks move to the relevant implementation tasks |
| Day 2 | Todo | Integration test setup, fixtures and inventory import | Add Failsafe, isolated fixtures, XML round-trip and inventory CSV tests; use the baseline saved in Day 1 |
| Day 3 | Todo — D03.01 compiler change complete | Java 21 build, boot, Hibernate | `PersistenceBehaviorIT`: ticket with children, reload, rollback, persisted totals; compare JDKs; add restart/flag test |
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

- [x] Day 1: Closed under revised scope — 3 done; 3 recording tasks removed, not completed
- [ ] Day 2: Todo — integration tests, inventory import and fixture isolation
- [ ] Day 3: Todo — compiler change done; boot, persistence and restart remain
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

### Day 1 task references

The completion checkboxes live only in main-plan section 6. This is a short
index, not a second task queue:

| ID | Task |
| --- | --- |
| D01.01 | Preserve app ZIP in repository-local `baselines/`, outside `target/`; verify and record its hash |
| D01.02 | Record tools, dependency inventory and package provenance |
| D01.03 | Preserve seed/test data and original/patched plugin inputs |
| D01.04–D01.06 | Removed by request; not completed. Check the affected behavior during implementation. |

The preserved ZIP and checksum are now in
[`baselines/2026-10-27/`](../baselines/2026-10-27/). They can be committed and
cloned; the workflow no longer depends on a sibling folder on this computer.
See the [baseline notes](../baselines/README.md) for the bundled database content
and verification command. Keep future shareable baseline paths inside the repo.

D01.02 is complete: [tools, dependencies and build source](../baselines/2026-10-27/baseline-record.md).
The full package build commit is unknown. Its embedded POM matches the old
build-fix commit; this is not proof of the full source revision. D01.03 saved
the [seed and plugin inputs](../baselines/README.md#saved-test-inputs).
**D02.01 is next:** add integration test setup and behavior tests.

## Tests already written during setup

The setup run on 26 October recorded 20 passing tests on JDK 17 and 21. These
historical results are completed **prework**, not proof that a later build
passes. Do not rerun them during plan edits. Review/extend
them when the selected execution task needs it.

That setup compiled with JDK 17 and the old Java 7 target, then ran the same
compiled tests on Java 21. Runtime library versions were unchanged. It covered:

| Test class | Count | Examples of current behavior kept |
| --- | --- | --- |
| `NumberUtilBehaviorTest` | 11 | Hide negative money by default; keep negative three-place values; null is zero; `2.675` rounds to `2.67`; grouping and five-place limit |
| `PasswordHasherBehaviorTest` | 3 | Existing numeric PIN, empty value and UTF-8 password produce the same stored hashes |
| `DiscountUtilBehaviorTest` | 6 | Fixed/percent ticket discounts; per-unit item percent; complete minimum-quantity groups; below-minimum gets zero |

These tests document existing rules. They do not endorse old password storage
or decide whether a surprising rounding/discount rule is correct. Any business
rule change needs a separate task and revised examples.

The following was the pre-change JDK 17 command for the old Java 7 target. It is
historical: the current POM now requires JDK 21. The focused goals avoid the old
validation step that unpacks seed data:

```sh
JAVA_HOME=/home/gino/.jdks/ms-17.0.15 \
  /home/gino/.m2/wrapper/dists/apache-maven-3.9.9-bin/4nf9hui3q3djbarqar9g711ggc/apache-maven-3.9.9/bin/mvn \
  -B -ntp resources:resources compiler:compile compiler:testCompile surefire:test \
  -Dtest=NumberUtilBehaviorTest,PasswordHasherBehaviorTest,DiscountUtilBehaviorTest
```

## Daily evidence to save

After each requested task or day, append an entry to `docs/JAVA_21_DAILY_RESULTS.md`:

| Item | Record |
| --- | --- |
| Scope | Requested amount, selected task ID(s), date/time, JDK, dependency before/after |
| Read | Source file, method, caller and fixture |
| Learned | Concrete current inputs, outputs, side effects and failures |
| Before | New/extended test, exact command, unchanged-app result |
| After | Same test command/result, extra regression results |
| Runnable app | Saved package, JDK/flags, exact start command and daily gate result |
| Status | Done / Todo / Blocked, with test evidence |
| Next | Remaining checkbox and exact next command |

## How to continue

Say **"run a task"** for the next unfinished numbered task, **"run a day"** for
the next unfinished day, or state the number/ID you want. You do not have to
choose a calendar date or name the day for the default commands.

Update task status first, then its day summary. Keep unfinished work Todo or
Blocked with a reason. A planning question alone never starts implementation.
