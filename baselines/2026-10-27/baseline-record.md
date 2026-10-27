# D01.02 — tools, dependencies and build source

Recorded 27 October 2026, Asia/Manila. This records the saved package and
current checkout. It does not qualify the app for Java 21.

## Tools

| Item | Observed version / state |
| --- | --- |
| Local baseline-check JDK | Microsoft OpenJDK `17.0.15+6-LTS`; javac `17.0.15` |
| Local Java 21 JDK | JetBrains Runtime `21.0.7+9-b895.130`; javac `21.0.7` |
| Maven used for this record | `3.9.9`, running on Microsoft JDK 17 |
| Shell PATH | `java`, `javac` and `mvn` not found; used explicit paths |
| Repository Maven wrapper | No tracked `mvnw`, `mvnw.cmd` or `.mvn` wrapper; Maven is in this computer's wrapper cache |
| Saved app build JDK | Manifest says `1.8.0_504`; vendor and original Maven version are not recorded |
| Saved app bytecode | `Main.class` major version `51` (Java 7 target) |
| Current compiler settings | Source and target `1.7`, in both properties and plugin settings |
| Future Java 21 tool choice | Temurin 21 remains planned; no Temurin build/download hash selected or installed by this task |

[tools.txt](tools.txt) has exact commands, paths and version output. These are
observed local tools, not a claim that they are current security releases.
Install matching tools on another computer and use its own paths.

## Build source

| Item | Evidence |
| --- | --- |
| Saved ZIP | `floreantpos-1.4-build-20261025-164421.zip` |
| ZIP SHA-256 | `2f01e20840d1a42ef4bdfad03c3fffda677b7110cd44e2ad89d63a0ecd55aa27` |
| Checkout when recorded | `bb2315fc302ff0c5a8306dfa6c72bc026c4e4830` |
| App coordinates | `org.floreantpos:floreantpos:1.4-SNAPSHOT` |
| Main app JAR SHA-256 | `bfdf30bf78b765dbd756d62fd1b35b56a1a41b2daf5a4ad40b95cd060c36c66b` |
| Embedded POM SHA-256 | `4a50858fa12e4d1a146a627687000787b9484f78bb6d521b10ce52727cacbbdb` |
| Matching POM commit | Exact byte match to `5f08e1e69a9871bfb3dc71a66f81b0c436d0fe31` (`fix build issues`) |
| Full app build commit | **Unknown.** No Git revision stamp found; matching a POM does not identify all source/input files |
| JAR metadata date | Maven properties say `Mon Oct 19 04:41:18 PST 2026`; differs from the ZIP filename date |

Do not label the package as built from today's checkout. The ZIP filename date
does not prove when the JAR was compiled. The root app JAR and
`lib/floreantpos-1.4-SNAPSHOT.jar` are byte-identical.

## Dependency records

| File | Covers |
| --- | --- |
| [direct-dependencies.md](direct-dependencies.md) | All 51 current direct declarations, scope, packaged-POM comparison and explicit build plugins |
| [dependency-tree.txt](dependency-tree.txt) | Current Maven resolution, transitive versions and omitted conflicts |
| [package-jars.tsv](package-jars.tsv) | All 88 JAR entries in the ZIP, sizes, SHA-256 and embedded Maven coordinates where present |
| [app-manifest.txt](app-manifest.txt) | Saved main class, build JDK and full manifest classpath |

The current POM has 49 compile entries, one system entry (PosLink) and one test
entry (JUnit). The packaged POM has 50 direct entries. JUnit and Surefire were
added in test prework; the other direct declarations are unchanged.

The TSV counts files, including the duplicate app JAR and three extra Derby
server copies. It is not a count of distinct dependencies. Embedded coordinates
are metadata, not proof that a vendor/plugin JAR was never patched.

### Differences and follow-up checks

| Finding | Later check |
| --- | --- |
| Manifest lists `lib/xercesImpl-2.12.2.jar` and `lib/xml-apis-1.4.01.jar`, but both are absent from the ZIP | Reproduce existing XML/report behavior before the Day 4 parser change |
| ZIP instead contains `lib/xercesImpl-2.4.0.jar`; its manifest confirms version `2.4.0` | Keep declared, resolved and shipped parser versions separate |
| HTTP client resolves to `4.5.14`, while direct HTTP core `4.0.1` wins over transitive `4.4.16` | Day 8 HTTP family tests and pins |
| Authorize SDK brings in Log4j `1.2.16`; Log4j `2.25.4` is also present | Day 7 logging tests and bridge change |
| Both JAXB API routes and both Commons Logging API routes are present | Day 6/7 duplicate-class review |
| PosLink uses a project-local system path; inventory plugin is outside the Maven dependency tree | Preserve/review local inputs in D01.03; test plugin/device routes later |
| Patched inventory ZIP hash matches the local runtime plugin | D01.03 still must preserve original/patched inputs and review sharing |

Current resolution succeeded with Maven dependency plugin `3.5.0`. Offline
attempts failed: `3.7.0` was not cached, and `3.5.0` lacked three supporting
artifacts. The online retry fetched tool dependencies and saved the tree.
Maven reported a project-local system-path warning, MigLayout model warnings
and a blocked legacy HTTP metadata endpoint. These did not stop the tree goal.
The HTTP version range is still open; this snapshot is not a dependency lock.

Recreate the current tree from the repository root, using installed tools:

```sh
JAVA_HOME=/path/to/jdk17 /path/to/maven-3.9.9/bin/mvn -B -ntp \
  org.apache.maven.plugins:maven-dependency-plugin:3.5.0:tree \
  -Dverbose -DoutputType=text \
  -DoutputFile=baselines/2026-10-27/dependency-tree.txt
```

This invokes only dependency inspection. It does not run `validate`, seed-data
extraction, compilation, packaging or tests. No app was launched, UI changed,
dependency declaration edited, commit made or push sent. Runtime qualification,
UI evidence and tests remain in their own planned tasks.
