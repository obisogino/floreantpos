# Java 21: four-week work plan

Written: 26 October 2026. Status: baseline test setup added; migration changes have not started.

**Current instruction:** update the plan only; do not run tests now. Keep the
test files and build changes already made. Run the planned behavior/unit tests
during manually requested workdays, before and after each affected module change.

**Manual tracker:** Day 1 is Done for the initial behavior tests and test setup;
Days 2–20 are Todo. No automation is active. Start a day only when requested.
See [daily tasks and behavior tests](JAVA_21_DAILY_TASKS.md) for each day's
required module tests and Done/Todo checkboxes.
Every day must read current code, write/run a baseline test, then make its change
and rerun that same test. The daily file is the run checklist; this file gives
the dependency choices and wider plan.

**System coverage:** the whole app has not been checked yet. The
[whole-system coverage checklist](JAVA_21_SYSTEM_COVERAGE.md) now assigns all
identified system areas to manual workdays and records what is still Todo.
Day 1 being Done covers initial tests only; it is not system approval.

## 1. Goal and limits

Run the current POS on Java 21. Keep the same screens, shapes, sizes, fonts,
colors, icons, button order, shortcuts, print layouts, and work flow.

Use 20 manual workdays: five planned days per week; dates are flexible.
Allow about 60–90 minutes of review and coding per day. Tests may run longer.
This is a part-time target, not a promise: the earlier 2–4 week estimate assumed
full-time work. Carry unfinished tasks forward; do not raise the daily budget.

| Path | Result | When |
| --- | --- | --- |
| A: compatibility | Java 21, current UI and data model, small library fixes, useful tests | These four weeks |
| B: modern core | Maintained data and report tools behind the same Swing screens | After A; separate stages below |

**Recommended:** A first, then B in small stages. A keeps some old libraries;
it is not the same as a fully maintained stack.

- [ ] Keep the UI and report layout unchanged.
- [ ] Keep model and plugin method names and return types unchanged in Path A.
- [ ] Keep the current database engines and data format in Path A.
- [ ] Use one library change at a time; test before the next change.
- [ ] Treat linked parts as one change: JAXB, Log4j, or JNA plus JNA Platform.
- [ ] Use one small commit per passing step. Never commit unrelated local work.
- [ ] Use copied data and sandbox payment accounts for tests.
- [ ] Leave untested devices and databases marked as blocked, not passed.
- [ ] Review every used area in the whole-system coverage checklist before release; carry unfinished checks forward if four weeks is not enough.
- [ ] Keep the current working app available every day; isolate migration code and copied data from daily use.
- [ ] From Day 2 onward, leave a saved runnable checkpoint before marking any app-changing day Done; failed work keeps the prior verified package available.

The first Java 21 candidate is targeted for Day 3, not promised on that date.
Until it passes its daily checks, use the existing app and runtime. Daily
checkpoints do not replace the final whole-system acceptance checks. See the
[daily runnable gate](JAVA_21_DAILY_TASKS.md#can-the-app-be-used-every-day).

## 2. Daily allowance: Sol 6.1 High

Use **GPT-6.1 Sol, High reasoning, Standard speed** for this project.
The 20% limit is a spending cap, not an amount that must be used.

The account's allowance is not a fixed token count. Model, reasoning, context,
tools, and caching affect use. I cannot read or enforce your account's remaining
allowance from this chat. Use the Usage screen; record the reading before and
after each work block. See [OpenAI usage guidance](https://learn.chatgpt.com/docs/pricing).

| Budget item | Rule |
| --- | --- |
| Daily budget | Spend at most 20% of the allowance available for that day; leave 80% for regular work |
| Example | If today's available amount is 100 units, this project gets at most 20 |
| Weekly-only meter | Default to 20% of the weekly pool for this project: 4 percentage points per workday; keep 80% for regular work |
| Multiple meters | Respect both the daily/session cap and the weekly share |
| Planning within the cap | About 10% read, 50% edit, 30% test/fix, 10% save notes; these are shares of the project budget |
| Early stopping point | Check use after each small change; leave room to finish tests and notes |
| Meter missing | Use one small task and the time limit; record usage as unknown, never invent a token figure |
| Unfinished work | Save the failing test and next command; resume next day |

- [ ] Record the meter, its reset period, and today's cap before work.
- [ ] Avoid Fast/Ultrafast, parallel agents, broad rewrites, and repeated full-repo reads.
- [ ] Run the changed area's tests first; run the full suite at weekly gates.
- [ ] Stop after the day's task or budget cap, whichever comes first.
- [ ] Update the checklist and daily log before stopping.

This is a manual budget rule. A hard token cap needs a known numeric budget and
usage tracking; it cannot be promised from an allowance percentage alone.

## 3. Starting point

| Finding | Evidence and next step |
| --- | --- |
| Java target | Maven and Eclipse still target Java 7 |
| Size | 918 Java files; about 153,000 lines; 80 Hibernate mapping files |
| Prior source check | On 19 October, all 918 files compiled with Java 21 settings; repeat on Day 3 |
| Prior runtime check | CGLIB failed on access to `ClassLoader.defineClass`; a narrow JVM flag passed the isolated proxy check |
| Reports | Compiled report load passed; JRXML compile failed with a schema error; the cause is not yet proved to be Java 21 |
| JAXB and plugins | JAXB context and JSPF manager start passed isolated checks, not full feature tests |
| Inventory | A patched plugin JAR exists locally; the earlier repair script is absent in this checkout |
| Tests | A first suite of 20 behavior tests now runs on JDK 17 and 21; compiled with the old target on JDK 17; extend it daily |
| Logging | Both Log4j 2 and an old Log4j 1 runtime appear in the built distribution |
| Local changes | `.classpath`, `.project`, and `nb-configuration.xml` already have local changes; preserve them |

Historical checks above are not completed migration tasks. Recheck from a fresh
build: `target/` may contain old JARs.

## 4. Build and test tools

These are chosen pins, not claims that each is the newest release. Maven Central
was checked for the listed plugin and test artifacts. Availability does not prove
app compatibility. Record the tested versions and hashes before release.

| Tool | Use | Reason / check |
| --- | --- | --- |
| JDK | Eclipse Temurin 21, current security patch at Day 1; freeze exact build and SHA-256 | Same JDK family in dev, CI, and the package |
| Old runtime | Saved working Java 8 runtime, if available | Baseline comparison only |
| Maven | Wrapper pinned to 3.9.9 | Already available locally; repeatable commands |
| Compiler plugin | `maven-compiler-plugin:3.14.1`; `release=21` | Remove both old source/target settings |
| Unit tests | `org.junit.jupiter:junit-jupiter:5.13.4`, test scope | Can compare baseline Java 8 and Java 21 where practical |
| Test runners | Surefire and Failsafe `3.5.4` | Unit tests and separate integration tests |
| Resources plugin | `3.3.1`, only if the old one blocks the build | Check icons, translations, report files |
| JAR plugin | `3.4.2`, only if needed | Keep manifest main class and library paths |
| Assembly plugin | `3.7.1`, only if needed | Check descriptor changes and full ZIP contents |
| AntRun plugin | `3.1.0`, only if needed | Keep seed extraction and ZIP steps working |
| UI tests | JUnit plus Swing APIs and `java.awt.Robot` | No new UI framework or theme |

Add tests under **`test/`**, with `<testSourceDirectory>test</testSourceDirectory>`.
Use `test-resources/` for fixtures. Do not use `src/test/java`: this project's
main source root is `src`, so nested tests can be picked up as app source.
Do not add modules, Spring, JavaFX, or a new source tree in Path A.

## 5. Dependency choices

`Keep` means keep and test, not skip review. Four weeks does not mean upgrading
all 50 direct dependencies. Test each used family; change only what blocks the
goal or is explicitly scheduled below.

### Changes and key checks, in order

| Order | Current | Path A choice | Pass condition |
| --- | --- | --- | --- |
| 1 | Hibernate `3.2.6.ga`, annotations `3.3.1.GA`, CGLIB `2.1_3` | Keep as a bridge; use only `--add-opens=java.base/java.lang=ALL-UNNAMED` if still needed | Real Hibernate save/load, lazy relations, rollback, restart, and plugin queries pass |
| 2 | `xerces:xerces:2.4.0` plus `xercesImpl:2.12.2` | Remove the 2.4.0 route; retain only Xerces Impl 2.12.2; inspect transitive routes | XML, mappings, import/export, and reports parse correctly |
| 3 | JasperReports `4.0.1`, old `jdtcore:3.1.0` | Keep Jasper 4; try `org.eclipse.jdt:ecj:3.37.0` only if old JDT blocks Java 21 report compilation | Compile and fill every source template; compare text, totals, page sizes, and print layout |
| 4 | JAXB API `2.3.1`, core `2.3.0.1`, impl `2.3.3` | Replace core/impl with `org.glassfish.jaxb:jaxb-runtime:2.3.9`; retain `javax.xml.bind:jaxb-api:2.3.1`; exclude duplicate API from runtime | XML round trip, old file import, POS request/response tests pass |
| 5 | Log4j core `2.25.4` plus transitive Log4j 1 | Keep 2.25.4; align `log4j-api`, `log4j-jcl`, and `log4j-1.2-api` at 2.25.4; retain one Commons Logging API JAR; exclude Log4j 1 | One log event per call; file rotation and startup/plugin errors work |
| 6 | HTTP client `[4.3.6,)`, core `4.0.1` | Pin `httpclient:4.5.14` and matching `httpcore:4.4.16` as one family | Local HTTP success, error, timeout, XML, and TLS sandbox tests pass |
| 7 | JNA / JNA Platform `4.2.2` | Test upgrade to matching `5.18.1` | Native load and actual used device/OS calls pass |
| 8 | `org.scream3r:jssc:2.8.0` | Test replacement with `io.github.java-native:jssc:2.9.6`; keep `jssc.*` imports | Port open/close, weight read, drawer, unplug, and timeout checks pass |
| 9 | JSPF `1.0.2`, patched inventory plugin | Keep loader and plugin API; save plugin hash and restore its build/repair steps | Discovery, license rules, menu actions, decimal stock, and reports pass |
| 10 | Derby `10.14.2.0`, MySQL driver `8.0.28`, PostgreSQL driver `42.7.12` | Keep all versions; qualify each used database separately | Transactions, rollback, date/decimal values, reconnect, and restart pass |

ECJ is a **trial pin**, not a proved Jasper 4 fix. Change it only after the XML
fix has been tested on its own. Do not mask the schema error by disabling XML
validation. If the trial fails, keep the last passing dependency set and mark
report work blocked; do not silently skip compile tests or jump to Jasper 7.

JAXB 2.3 retains `javax.xml.bind.*`. Do not apply a bulk `javax` to `jakarta`
rename. For logging, keep one full Commons Logging JAR: `log4j-jcl` needs its
API. Remove the separate duplicate `commons-logging-api` JAR and select the
Log4j factory. Do not add a bridge back to JCL and create a loop. Convert the
old log configuration to Log4j 2 while keeping log paths and useful output.

Keep any device upgrade that lacks hardware proof on the trial branch. It does
not become a release dependency until its device tests pass.

### Other direct dependencies: keep for Path A

| Family / artifacts | Current versions to retain | Test focus |
| --- | --- | --- |
| `javax.transaction:jta`; `c3p0:c3p0` | `1.1`; `0.9.1.2` | Commit/rollback, pool close, reconnect |
| MigLayout; JGoodies Forms / Looks | `3.7.4`; `1.2.1` / `2.2.2` | Component bounds; Plastic XP + ExperienceBlue |
| SwingX Core; Swing Layout; IntelliJ Forms runtime | `1.6.2-2`; `1.0.3`; `7.0.3` | Dialogs, tables, generated forms |
| JIDE OSS; Metawidget; Foxtrot | `3.6.1`; `3.9`; `2.0` | UI startup, editors, background tasks on target OS |
| ECS; Barbecue | `1.4.2`; `1.5-beta1` | Generated output and barcode scan/print |
| Commons Lang; Configuration; CLI | `2.6`; `1.2`; `1.2` | Text rules, config read/write, startup flags |
| Commons Codec; IO; Validator | `1.9`; `2.14.0`; `1.4.0` | Existing password hashes, file IO, validation |
| Jersey Client | `1.18.1` | Existing web-service calls against a local stub |
| Authorize.Net SDK; PAX PosLink | `1.8.1`; local `1.0` | Sandbox approve/decline/cancel; vendor JAR hash |
| JSON API / GlassFish JSON | `1.0` / `1.0.4` | Parse/write old payloads; one provider |
| Magnetic Track Parser | `1.0` | Saved synthetic card-track fixtures |
| JDOM2; Jaxen | `2.0.6.1`; `1.1.4` | XML/XPath output matches baseline |
| Joda-Time | `2.3` | Shop dates, shifts across midnight, time zone |
| Google Maps Services | `0.1.12` | Stubbed mapping request/response and timeout |

Capture all transitive dependencies too: old ASM, Xalan, XML APIs, collections,
and logging can enter through other JARs. Review conflicts and known issues.
Do not bulk-upgrade them or call Path A fully modern because the app starts.

## 6. Four-week checklist

Work in order. Each day has one main result. If a required check fails, keep the
next dependency unchanged and carry the task forward. Days 19–20 provide some
buffer; larger blockers extend the plan.

### Week 1: a tested Java 21 base

| Day | Main task | Required proof |
| --- | --- | --- |
| 1: Done | Initial test setup and behavior tests | 20 existing-behavior tests; money display, login hashes, and discounts |
| 2: Todo | Save baseline files; add fixture and inventory tests | Existing ZIP, copied data, UI samples, hashes; inventory import and isolation tests |
| 3 | Move build and launch to Java 21 | Full source compile, boot test, Hibernate integration test |
| 4 | Fix duplicate XML parser routes | One parser version; XML/JAXB/report parsing tests pass |
| 5 | Fix report compile support, then run Week 1 gate | ECJ trial if needed; all available JRXML compile; current compiled reports fill |

- [x] **D1:** Add JUnit, Surefire, and the separate `test/` root; keep the old app target.
- [x] **D1:** Record money formatting, password hash, and discount behavior in 20 tests.
- [ ] **D2:** Save the current ZIP outside `target/`; record its hash, commit, JDK, Maven, and dependency tree.
- [ ] **D2:** Copy the seed and test data. Save the patched and original plugin separately; record both hashes.
- [ ] **D2:** Capture login, tables, order, payment, back office, inventory, and print preview at fixed screen scale.
- [ ] **D2:** Add Failsafe and `test-resources/`; add XML round-trip and decimal inventory import tests.
- [ ] **D2:** Add isolated DB fixture setup and teardown; first test must prove it cannot use the live DB path.
- [ ] **D2:** Review the system coverage table; record used screens, databases, devices, services and plugins; save settings/UI baselines and list existing failures.
- [ ] **D3:** Use JDK 21, compiler 3.14.1, and `release=21`; remove both property and plugin Java 7 settings.
- [ ] **D3:** Probe Hibernate without flags first. If needed, add the one documented flag to tests, every launcher, setup/proxy tools, and restart.
- [ ] **D3:** Check restart passes the same flags; avoid relying on `sun.java.command` parsing to preserve them.
- [ ] **D4:** Remove old Xerces 2.4.0; inspect the resolved tree and assembled `lib/`; run T03, T04, T06, T07.
- [ ] **D5:** Trial the newer ECJ only if needed; remove the old JDT route; keep generated report language settings explicit.
- [ ] **D5:** Compile/fill the full report set; find the source of `ItemSalesReport.jasper` or block its upgrade. Run the Week 1 gate.

**Week 1 gate:** Java 21 boot, DB round trip, import/export, and report tests pass.
No new UI shape changes. Separate old failures from new failures in the log.

### Week 2: small dependency changes

| Day | Main task | Required proof |
| --- | --- | --- |
| 6 | Align JAXB 2.3 | Old and new XML files round-trip; requests stay compatible |
| 7 | Route logging through Log4j 2 | One provider, expected file/console logs, no Log4j 1 runtime |
| 8 | Pin the HTTP client family | Success/error/timeout checks and sandbox TLS pass |
| 9 | Trial matching JNA libraries | Native tests on the deployed OS pass |
| 10 | Trial the JSSC fork; run Week 2 gate | Serial/device tests pass; previous tests remain green |

- [ ] **D6:** Replace JAXB core/impl with runtime 2.3.9; keep one API and one activation route; run T06, T07.
- [ ] **D6:** Test old import fixtures with non-ASCII text, decimal stock, empty fields, and invalid XML.
- [ ] **D7:** Add matching Log4j bridges, remove Log4j 1 and duplicate JCL providers, and convert log config.
- [ ] **D7:** Test file rollover, full exception causes, app start, and plugin errors; run T08.
- [ ] **D8:** Replace the open HTTP version range and align HTTP Core; run T09 against a local server.
- [ ] **D8:** Run a real sandbox TLS call for each used payment/web-service route; never use live card data.
- [ ] **D9:** Trial JNA and JNA Platform together; test native load and all used APIs on the actual OS.
- [ ] **D9:** Keep the old pin if hardware checks are pending; record the trial result rather than calling it passed.
- [ ] **D10:** Replace only JSSC; verify `jssc.*` API use, device read, port release, disconnect, and timeout.
- [ ] **D10:** Run the full suite and compare the UI baseline; resolve failures before Week 3.

**Week 2 gate:** Each retained dependency change has focused tests and a recorded
version. Device features with no hardware proof remain blocked for release.

### Week 3: plugins, POS work flows, and packaging

| Day | Main task | Required proof |
| --- | --- | --- |
| 11 | Inventory plugin and repeatable patch | Load once, license rules kept, all menu actions work |
| 12 | Inventory and ticket data checks | Decimal stock and sales persist after restart |
| 13 | Qualify configured databases | Supported engines pass the same DB tests |
| 14 | Fix build/package plugins only where needed | ZIP has correct JARs, config, icons, reports, and plugin |
| 15 | Full POS and UI check | Same screens and outcomes; weekly gate passes |

- [ ] **D11:** Find the old inventory repair steps in history/backups; if missing, restore a repeatable script and keep the original JAR intact.
- [ ] **D11:** Verify all plugin menu actions, license behavior, missing-plugin behavior, and duplicate-plugin detection; run T10.
- [ ] **D12:** Save/edit inventory, stock IN/OUT, recipe deduction, imports, and reports with fractional quantities; run T05.
- [ ] **D12:** Create, save, reopen, split, pay, and void fixture tickets; confirm totals, tax, discount, and stock effects; run T02, T04.
- [ ] **D12:** Capture menu/modifier/pizza pricing, order types, tables, customers/delivery, cash movement, shifts, tips, and kitchen ticket states; use the coverage table to track each separate flow.
- [ ] **D13:** Run T04/T05 for Derby, MySQL, and PostgreSQL used by the deployment; record server and driver versions.
- [ ] **D13:** Use separate test servers/databases; unavailable engines stay unqualified and cannot be claimed as supported by this release.
- [ ] **D14:** Build from clean output. Upgrade resources, JAR, assembly, and AntRun only when needed, one plugin per passing step.
- [ ] **D14:** Keep both assembly profiles working; adapt old descriptor options only as required; check PAX JAR and plugin inclusion.
- [ ] **D14:** Make app, setup, proxy, database tools, and restart choose the tested JDK and flags; run T13/T14.
- [ ] **D15:** Run T11/T12 on the actual screen/OS; compare bounds, theme, fonts, icons, shortcuts, and printouts.
- [ ] **D15:** Review login permissions, user transfer, back-office edits, booking/floor/customer screens, attendance/driver actions, saved settings and language/encoding; add tests for each affected flow before changes.
- [ ] **D15:** Run `verify` and clean ZIP launch tests; document all remaining failures before Week 4.

**Week 3 gate:** A fresh ZIP runs outside the IDE. Required plugin, database,
money, stock, and screen checks pass. User settings and live data stay untouched.

### Week 4: fix, prove, and hand over

| Day | Main task | Required proof |
| --- | --- | --- |
| 16 | Test physical printers, drawer, scale, and payment terminal | Real device evidence for each used feature |
| 17 | Fresh install, upgrade copy, backup, and restore | Old data opens correctly; rollback drill passes |
| 18 | Repeat tests and observe a long test session | No stuck tasks, pool/port leaks, or growing failures |
| 19 | Buffer for proven blockers; freeze candidate | Clean build, fixed dependency tree, all required checks green |
| 20 | Final review, docs, and next phase | A reviewable candidate and a clear pass/blocked record |

- [ ] **D16:** Test receipt and kitchen print, paper size, cutter, drawer, weight read, and barcode scan where used.
- [ ] **D16:** Test payment approve, decline, cancel, and timeout in sandbox; confirm retry does not double-record payment.
- [ ] **D16:** Include custom/gift payments, gratuity, refund/void, drawer balances, POS socket/proxy behavior and each actually used gateway; document unused or blocked routes.
- [ ] **D17:** Run a new install and an upgrade on data copies; compare row counts, balances, IDs, reports, and settings.
- [ ] **D17:** Rehearse restoring the saved old ZIP and database backup together; compare schema before/after startup.
- [ ] **D18:** Run a four-hour local test session with repeated tickets, reports, reconnects, logout/login, and restart; review timeouts, memory, threads, and open ports.
- [ ] **D18:** Add CI for headless unit/integration tests on Java 21; keep GUI/device checks as explicit separate jobs or manual gates.
- [ ] **D19:** Fix only blockers; if time remains, remove proved-unused duplicate JAR routes one at a time.
- [ ] **D19:** Build from a fresh checkout plus recorded private plugin/vendor inputs; save ZIP hash and dependency tree.
- [ ] **D20:** Complete the release checklist and run guide; record the exact next unchecked task for Path B.
- [ ] **D20:** Produce the candidate and findings for review. Production rollout is a separate action.

## 7. Tests to add

Use behavior tests, not tests that just repeat getters/setters. Start with each
bug or changed family; grow coverage as that code is touched.
The [daily calendar](JAVA_21_DAILY_TASKS.md) assigns a test to every workday.
Write and pass the current-behavior test before changing that day's module.

| ID | Test / kind | Required cases |
| --- | --- | --- |
| T01 | Build and boot / integration | Java 21 compile; start without missing classes; quit/restart; JDK and flags logged |
| T02 | Money and login / unit | Existing password hash fixture unchanged; quantity, tax, discount, rounding, split and payment totals match baseline |
| T03 | XML stack / integration | One chosen parser; mapping load; valid/invalid XML; no duplicate schema error |
| T04 | Database / integration | Save/load ticket with child rows; lazy load; commit; rollback; ID reuse rules; decimal/date values; reconnect; restart |
| T05 | Inventory / integration | Start with 2.5 packages; IN 1.25 gives 3.75; OUT 0.5 gives 3.25; recipe deduction and save/reopen keep fractions |
| T06 | JAXB files / integration | Export/import same menu and settings; old fixture import; accents and peso sign; empty/null fields; invalid XML fails clearly |
| T07 | POS XML service / integration | Existing request/response fields and names; success/error response; malformed request; no protocol rename |
| T08 | Logs / integration | JCL, legacy Log4j API, and Log4j 2 API events each appear once; file rotation; app/plugin exception cause kept |
| T09 | HTTP / integration | Local success, 4xx/5xx, timeout, lost connection, XML/JSON body; used TLS routes checked in sandbox |
| T10 | Plugin / integration + UI | Discover once; `pluginsPath`; absent/duplicate JAR; license rules; each inventory menu action; no method-link errors |
| T11 | UI / display test | Login, floor, order, pay, back office, inventory: same component bounds, preferred sizes, fonts, colors, icons, tab order and shortcuts |
| T12 | Reports / integration + print | Load all 35 built-in compiled templates; compile all 34 source templates; fill with fixtures; check used custom layouts, page size, totals and printed output |
| T13 | Package / integration | Both build profiles; no duplicate parser/logging/JDT JARs; vendor/plugin hashes; resources and manifest paths correct |
| T14 | Launch / process test | ZIP extracted outside repo, path with spaces, normal launch, setup/proxy, restart, rejected wrong Java version |
| T15 | Hardware / manual + narrow tests | Native load; scale response/timeout/unplug; drawer; receipt and kitchen printer; cutter; barcode; sandbox terminal |
| T16 | Upgrade / integration + manual | Copy of old DB/config; schema diff; same row counts and totals; backup/restore; old ZIP plus old DB restore |
| T17 | Long session / manual | Four-hour cycle; no duplicate payment/stock updates, deadlock, unclosed port, or pool leak |

### Test setup and commands

- [ ] Put unit classes named `*Test` and integration classes named `*IT` under `test/`.
- [ ] Bind Surefire to `test`; Failsafe to `integration-test` and `verify`.
- [ ] Run GUI tests only with a display. Use a `gui` tag/profile; never claim headless GUI success.
- [ ] Use a fresh seed copy, temporary work folder, config, ports, and app user-home for each DB/process test.
- [ ] Redirect logs and generated reports into the test work folder; block live DB URLs in test setup.
- [ ] Close session factories, connections, windows, HTTP servers, child processes, and serial ports after tests.
- [ ] Run UI work on Swing's event thread; compare at the same OS, resolution, locale, and scaling as baseline.
- [ ] Use a local HTTP stub built with the JDK HTTP server; avoid live services in the default suite.
- [ ] Save failed logs, screenshots, and report samples under `target/`; keep only small synthetic fixtures in Git.
- [ ] Fail the required test gate if zero tests run. Record optional missing hardware/servers as blocked.

**Planned commands** below become valid after the wrapper and profiles are added.
The default `verify` includes headless integration tests; GUI/device/server
profiles are opt-in. The packaged app test must run in a fresh work directory.

```sh
./mvnw -version
./mvnw -Dtest=MoneyTest,PasswordHasherTest test
./mvnw -Dit.test=DatabaseIT,InventoryIT verify
./mvnw -Pgui verify
./mvnw -Pmysql-it verify
./mvnw -Ppostgres-it verify
./mvnw clean verify
./mvnw -Pdevinepos clean verify
```

Keep the existing default assembly profile active when test profiles are chosen;
set its build profile property explicitly so Maven profile activation does not
drop it. Use distinct DB names per engine. External server tests use supplied
test credentials, never credentials checked into Git.

## 8. Rule for every library change

Copy this checklist into the day's notes. Linked family members may move
together; unrelated libraries may not.

- [ ] Record old/new coordinates, reason, changed transitive JARs, and expected tests.
- [ ] Keep the last passing commit and package available.
- [ ] Make the smallest change. Do not clean up nearby UI or business code.
- [ ] Run the focused test set and inspect the assembled classpath if affected.
- [ ] Run a short boot, DB, inventory, and report check.
- [ ] Pass the daily runnable gate and save a named package with its JDK, launch flags and copied-data results before marking the day Done.
- [ ] Record passed, failed, and blocked checks separately.
- [ ] Commit only when the step passes; otherwise fix it or restore only that step.
- [ ] Update this plan with the result, remaining issue, and next command.

## 9. A more modern plan, with the same UI

Keep Swing and its current theme. Place small service interfaces between the
screens, data access, reporting, and devices. This lets each part move without
changing screen code or every dependency at once. Avoid a web/UI rewrite.

This is a **follow-on roadmap**, not a second four-week promise. The earlier
8–16 week modernization estimate assumed full-time work. At 60–90 minutes per
day and this allowance cap, plan in stages over several months and re-estimate
after the first data-access pilot.

| Stage | Recommended target | Change | Exit test |
| --- | --- | --- | --- |
| B1 | Same Swing UI; small ticket, inventory, report, and device services | Move data/device calls behind interfaces in touched screens; no bulk move | Same UI bounds and business results |
| B2 | Hibernate ORM 7.4, Jakarta Persistence 3.2; recheck supported patch at stage start | Pilot one read/write flow; replace legacy Criteria, old session APIs, custom ID integration and XML mappings as needed | Same SQL/data results and transactions on copied DBs |
| B3 | One new persistence stack; maintained connection pool selected after pilot | Finish DAOs and plugin adapter; remove Hibernate 3, old CGLIB/ASM, JTA and c3p0 routes only once unused | Full sales/stock suite and plugin compatibility pass |
| B4 | JasperReports 7.x, matching report design tool/compiler | Recover missing template source; convert and recompile templates; keep paper layout | All report samples and actual printouts match |
| B5 | JDK 21 HTTP Client; current supported vendor SDKs | Replace Jersey/Apache client routes one service at a time; preserve payloads, TLS, timeout and retry rules | Contract tests plus sandbox payments pass |
| B6 | JAXB 4.x / Jakarta XML Binding where needed; current JSON provider | Keep XML wire names unchanged; change Java packages only in the chosen service boundary | Old client and file fixtures still work |
| B7 | JDK APIs where suitable; current Commons libraries where useful | Retire unused libraries; move dates to `java.time` in small tested steps; retain required UI libraries | All baseline behavior and UI tests pass |
| B8 | Standard plugin boundary, bundled Java runtime, repeatable CI package | Pilot `ServiceLoader` behind a legacy JSPF adapter; later remove compatibility flags when tests prove they are unused | Current plugin features, license rules, ZIP install and restart pass |

Hibernate 7.4 is listed as stable and supports Java 21 in the
[official compatibility table](https://hibernate.org/orm/releases/7.4/).
It is not a drop-in upgrade: legacy Criteria was removed in Hibernate 6, and
Hibernate 7 removes old save/update APIs. Review all 80 legacy XML mappings;
include a move to a supported mapping style in the pilot. See the [6.0 migration guide](https://docs.hibernate.org/orm/6.0/migration-guide/)
and [7.0 migration guide](https://docs.hibernate.org/orm/7.0/migration-guide/).

JasperReports 7 changes source and compiled template compatibility. Plan
conversion and recompilation, not just a POM bump. See the
[project's change notes](https://github.com/Jaspersoft/jasperreports).

### Modern data and plugin rules

- [ ] Start with a single small data flow; estimate the rest from actual effort.
- [ ] Use one ORM version per app classloader. Do not drop Hibernate 3 and 7 together into `lib/`.
- [ ] Keep old plugin-visible method signatures through an adapter; rebuild or replace incompatible plugin code.
- [ ] Preserve license checks and private vendor inputs; do not bypass them while replacing the loader.
- [ ] Prove ID generation, decimal quantities, cascades, locking, date values, and transactions before moving more DAOs.
- [ ] Compare schema changes on copies. Replace automatic startup schema updates with reviewed migrations before changing production schema.
- [ ] Choose Flyway or Liquibase only after checking support for every retained DB/version; do not assume Derby support.
- [ ] Move persistent state behind the new service boundary before deleting old data APIs.
- [ ] Remove each JVM compatibility flag only after tests pass without it.
- [ ] Recheck library support status and pin exact tested versions at the start of each later stage.

## 10. Done, handover, and daily notes

### Path A release checklist

- [ ] Java 21 build and clean ZIP start pass with recorded JDK/Maven/JAR versions.
- [ ] Tests run from a fresh checkout using documented private vendor/plugin inputs.
- [ ] All required checks pass; skipped tests and missing devices/servers are listed.
- [ ] UI shapes, sizes, theme, fonts, icons, shortcuts, and work flow match baseline.
- [ ] Existing data, plugin methods, fractional inventory, login, money totals, and reports remain correct.
- [ ] No new unreviewed schema changes; backup and restore drill passes.
- [ ] Each used payment route and device has actual sandbox/hardware proof.
- [ ] Logs, dependency tree, test reports, ZIP hash, and sample outputs are saved.
- [ ] Run guide covers Java setup, launch, restart, test commands, plugin setup, backup, rollback, and known limits.
- [ ] Old libraries and remaining compatibility flags have a named Path B task.
- [ ] Every used row in the whole-system coverage table has before/after evidence; pending reviews and required blockers keep release approval open.
- [ ] Candidate status says either **ready for review** or **blocked**, with a reason; no automatic production rollout.

### Daily log template

| Field | Entry |
| --- | --- |
| Date / day | |
| Model / reasoning | GPT-6.1 Sol / High / Standard |
| Usage meter and reset period | |
| Start / stop reading and cap | |
| Main task and changed dependency | |
| Tests passed / failed / blocked | |
| Passing commit / package | |
| Next unchecked item and exact command | |

### Source notes

Sources checked on 26 October 2026. Recheck support and security patches before
implementation; selected pins still need app-level tests.

- [OpenAI model settings](https://learn.chatgpt.com/docs/models) and [allowance guidance](https://learn.chatgpt.com/docs/pricing).
- [Maven `release` setting](https://maven.apache.org/plugins/maven-compiler-plugin/examples/set-compiler-release.html), [Surefire JUnit support](https://maven.apache.org/surefire/maven-surefire-plugin/examples/junit-platform.html), and [JUnit 5.13.4](https://docs.junit.org/5.13.4/user-guide/).
- [JAXB 2.3.9 release](https://github.com/eclipse-ee4j/jaxb-ri/releases/tag/2.3.9-RI) and [Log4j 1 migration](https://logging.apache.org/log4j/2.x/migrate-from-log4j1.html).
- [JNA changes](https://github.com/java-native-access/jna/blob/master/CHANGES.md) and [JSSC fork](https://github.com/java-native/jssc).
- Artifact pins were checked against the [Maven Central repository](https://repo.maven.apache.org/maven2/); this check proves availability only.
