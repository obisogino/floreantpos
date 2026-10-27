# Java 21: whole-system coverage

## What has actually been checked

**The whole system has not been reviewed or tested yet.** The initial work
covered the build, dependency list, selected source code, isolated runtime
probes, and 20 tests for three utility classes. Compiling all Java files does
not prove every feature works.

The source inventory covers 918 Java files: 914 under `com.floreantpos` and four
bundled support files. It also includes 80 Hibernate mappings, 34 source report
templates, 35 compiled report templates, local vendor/plugin JARs, and the build
and launch files. This is a map of the code, not a claim that every line was read.

| Check | Current state |
| --- | --- |
| Build and direct dependency review | Reviewed; proposed changes are in the main plan |
| Package and feature inventory | Mapped; coverage below is now part of the plan |
| Detailed source/behavior review | Partial; required before changing each module |
| Existing behavior tests | 20 initial tests for number formatting, password hashes, discounts |
| Full POS work flow | Todo |
| All database engines and real devices | Todo |
| Whole-system Java 21 approval | Todo; no release sign-off yet |

This file maps coverage; it is not the task queue. A planning question does not
start tests or implementation. An execution request for a task, day or stated
number of tasks follows the [daily command rules](JAVA_21_DAILY_TASKS.md#chat-commands-and-task-selection)
and the numbered tasks in main-plan section 6. No automation is installed.

## Module checklist and assigned days

`Partial` means selected code or isolated behavior was checked. `Todo` means
the files/feature were found, but their behavior has not been fully reviewed or
tested. Every row still needs its own before/after evidence before sign-off.

| ID | System area | Checked so far | Behavior to read and capture in tests | Days |
| --- | --- | --- | --- | --- |
| S01 | Build, Java settings, dependencies | Partial | Same source/resources/classpath; test libraries excluded from app; fixed versions | 2, 3, 14 |
| S02 | Startup, setup, headless start, restart, shutdown | Partial | Config/database failures, same restart flags, saved window state, clean process exit | 3, 14, 18 |
| S03 | Login, roles, permissions, user changes | Partial: password hashes only | Same PIN/password result; allowed/denied actions; logout and user transfer | 2, 15 |
| S04 | Menu, prices, modifiers, sizes, pizza, coupons, taxes | Partial: discount/number helpers only | Current pricing per order type/shift; modifier/quantity rules; tax/rounding totals | 4, 12, 15 |
| S05 | Tickets, order types, split/group settlement, bar tabs | Partial: service code excerpts | Save/reopen/split/transfer; due/paid/closed states; partial/full payment; bar-tab data cleanup | 3, 12, 16 |
| S06 | Payments: cash, card, gift, custom, gratuity | Partial: settlement code excerpts | Same transaction rows, change/cashback/tips, approve/decline/cancel/timeout and retries | 12, 16 |
| S07 | Void and refund | Partial: refund service excerpt | Terminal balance, refund amount, ticket state, gratuity state, audit/transaction rows; rollback on error | 12, 16 |
| S08 | Drawer assignment, cash drop, payout, cash bleed, close-out | Todo | Cash movement changes balance once; assignment checks; drawer-pull totals and saved reports | 12, 16 |
| S09 | Employee clock-in/out, driver in/out, shifts, tips | Todo | Shift boundaries, midnight/time-zone cases, attendance rows, tips and labor/payroll totals | 12, 15 |
| S10 | Customers, bookings, delivery | Todo | Same customer lookup/edit, table booking, delivery address/charge/driver and ticket links | 12, 15 |
| S11 | Floor layout and tables | Todo | Assign/move/merge/split tables, occupancy and booking state; same table shapes and actions | 12, 15 |
| S12 | Inventory, purchases, recipes, import and plugin screens | Partial: CSV/model code and prior plugin evidence | Fractional quantities, field positions, IN/OUT, purchase rows, recipe deduction, reports and plugin actions | 2, 11, 12 |
| S13 | Back-office editors and setup screens | Todo | Menu/user/tax/currency/shift/config edits save and reopen correctly; validation and permissions stay the same | 4, 12, 15 |
| S14 | Model, DAO, mappings, IDs, sessions and pool | Partial: configuration and selected DAO code | All mappings load; used DAO paths; relation/cascade behavior; IDs, decimals, dates, transactions, reconnect and close | 3, 13, 18 |
| S15 | Database engines: embedded/server Derby, MySQL, PostgreSQL | Partial: drivers/configuration identified | Same used flows per deployed engine/version; copied-data startup, rollback, reconnect and restart | 3, 13, 17 |
| S16 | Import/export and database transfer tools | Partial: JAXB dependencies and entrypoints | Old files and XML fields remain usable; Unicode/fractions/relations kept; errors leave data unchanged | 2, 4, 6, 17 |
| S17 | Sales, stock, cash, attendance and payroll reports | Partial: one compiled load and one compile probe | All used templates compile/load/fill; totals, page sizes, subreports, fonts and previews match old samples | 5, 12, 15 |
| S18 | Receipts, kitchen print, printer routing, drawer kick | Todo | Same destination and byte/paper layout; totals, cutter/drawer commands, print retries and duplicate-print behavior | 5, 10, 16 |
| S19 | Kitchen display, filters, ticket state, refresh timers | Todo | Same ticket visibility/status changes; refresh/filter rules; timers stop when windows close | 12, 15, 18 |
| S20 | POS socket server/client and Dejavoo HTTP proxy | Partial: request/response and thread entrypoints | Existing payload/framing/encoding, response/error behavior, ports, timeouts, disconnect and cleanup | 6, 8, 16, 18 |
| S21 | Remote web calls, maps and version checks | Partial: HTTP dependencies and service entrypoint | Local stub captures existing requests/responses; TLS, missing credentials and timeout behavior; no live side effects | 8, 16 |
| S22 | Plugins, discovery, licenses and proprietary SDKs | Partial: loader code and local JAR inventory | Same discovery/order/config, license outcomes and plugin-visible methods; record private input hashes | 9, 11, 14, 16 |
| S23 | Serial ports, scale, native OS/device code | Partial: serial source and library probes | Same protocol/weight text, native load, unplug/timeout behavior and port release on each used OS | 9, 10, 16 |
| S24 | App, terminal, card, restaurant, printer and UI settings | Todo | Defaults, old settings read/write, reopen after restart, selected terminal/database/printer; synthetic secrets only | 2, 3, 15, 17 |
| S25 | Language, fonts, currency, dates, encoding and encryption | Partial: hash/number helpers only | Translations/icons; peso/accent text; old date and currency behavior; same AES-encrypted fixture compatibility | 4, 6, 12, 15 |
| S26 | Swing components, generated forms, bundled source files | Partial: theme and source inventory | Same bounds/theme/font/keyboard flow; dialogs, custom tables, Mercury client, terminal support and CardPanel behavior | 8, 15, 16 |
| S27 | ZIPs, profiles, launcher scripts, Windows wrapper | Partial: build and launcher settings reviewed | Both profiles; correct resource/vendor JARs; paths with spaces; supported OS start/setup/proxy/restart | 14, 17, 20 |
| S28 | Data backup, upgrade copy, schema and restore | Todo | Same IDs/counts/balances; reviewed schema changes; restore old app and old data together | 13, 17, 20 |
| S29 | Threads, timers, resources, long sessions and CI | Partial: thread/timer entrypoints located | No duplicate actions, stuck windows, growing sessions, open sockets/ports or leaked workers; CI runs actual tests | 18, 19 |
| S30 | Final release and docs | Todo | Every used area above has evidence or an explicit blocker; record versions and known limits | 19, 20 |

## Extra care from the source scan

- `PosTransactionService` changes terminal balances and ticket/payment states
  together. Test the transaction and rollback, not just the displayed total.
- `PosRequestHandler` uses the default charset for socket bytes. Compare old
  bytes before choosing an explicit encoding; Java 21's default can differ.
- The POS response code contains an unfinished implementation marker. Check
  whether the deployment uses it. Record its actual old behavior; do not treat
  building a missing feature as part of the Java version change.
- Kitchen display and POS/proxy code create timers/threads. Include close,
  disconnect, restart and repeated-use cases, not just successful startup.
- Four support files outside `com.floreantpos` are still part of the app:
  Mercury request/response, terminal support and `CardPanel`. Include them when
  reviewing their callers; a package-only scan would miss them.

These are source findings, not new executed test results.

## Before changing each area

- [ ] Confirm which screens, engines, devices and external services are used.
- [ ] Read the entrypoint, business method, callers, persistence side effects and failure path.
- [ ] Write a current-behavior test with fixed inputs and checked expected values.
- [ ] Save the unchanged-app result; use copied data and synthetic credentials.
- [ ] Make one small dependency/module change, then run the same test again.
- [ ] Check the related rows above; a passing helper test does not cover its whole feature.
- [ ] Record Done / Todo / Blocked, commands, results and evidence in the daily log.

## Whole-system completion rule

Day 1 is closed under the reduced scope; the removed recording tasks were not
done. Check feature use and current behavior as each affected area is changed.
No row passes because setup exists. A listed feature is planned, not verified.

Each used area must pass its baseline and after-change checks. An unused area
may be marked deferred only with its reason and impact recorded. Missing device,
server, plugin source or vendor access stays Blocked. Existing failures are
listed separately from new regressions.

The four-week budget remains a target. If this wider coverage does not fit the
manual daily allowance, carry tasks forward and keep release approval open.
Do not remove checks or mark whole-system completion to meet Day 20.
