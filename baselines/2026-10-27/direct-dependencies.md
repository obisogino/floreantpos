# Direct dependencies — 27 October 2026

Current POM: 51 direct entries (49 compile, one system, one test).
Packaged POM: 50 direct entries. The only declared dependency change is the added test-scope JUnit entry.
Versions below are declarations, not proof of the files shipped. Use `package-jars.tsv` for ZIP contents and `dependency-tree.txt` for current resolution.
The HTTP client range is open; its resolved version belongs to this capture only.

| Dependency | Current declared version | Scope | Packaged POM version |
| --- | --- | --- | --- |
| `org.junit.jupiter:junit-jupiter` | `5.13.4` | test | absent |
| `javax.xml.bind:jaxb-api` | `2.3.1` | compile | `2.3.1` |
| `com.sun.xml.bind:jaxb-core` | `2.3.0.1` | compile | `2.3.0.1` |
| `com.sun.xml.bind:jaxb-impl` | `2.3.3` | compile | `2.3.3` |
| `org.hibernate:hibernate` | `3.2.6.ga` | compile | `3.2.6.ga` |
| `javax.transaction:jta` | `1.1` | compile | `1.1` |
| `org.hibernate:hibernate-annotations` | `3.3.1.GA` | compile | `3.3.1.GA` |
| `com.miglayout:miglayout` | `3.7.4` | compile | `3.7.4` |
| `ecs:ecs` | `1.4.2` | compile | `1.4.2` |
| `xerces:xerces` | `2.4.0` | compile | `2.4.0` |
| `com.jgoodies:forms` | `1.2.1` | compile | `1.2.1` |
| `org.swinglabs:swingx-core` | `1.6.2-2` | compile | `1.6.2-2` |
| `org.apache.derby:derby` | `10.14.2.0` | compile | `10.14.2.0` |
| `org.apache.derby:derbynet` | `10.14.2.0` | compile | `10.14.2.0` |
| `org.apache.derby:derbyclient` | `10.14.2.0` | compile | `10.14.2.0` |
| `org.apache.derby:derbytools` | `10.14.2.0` | compile | `10.14.2.0` |
| `com.sun.jersey:jersey-client` | `1.18.1` | compile | `1.18.1` |
| `com.jgoodies:looks` | `2.2.2` | compile | `2.2.2` |
| `com.intellij:forms_rt` | `7.0.3` | compile | `7.0.3` |
| `net.sf.jasperreports:jasperreports` | `4.0.1` | compile | `4.0.1` |
| `foxtrot:foxtrot` | `2.0` | compile | `2.0` |
| `org.swinglabs:swing-layout` | `1.0.3` | compile | `1.0.3` |
| `commons-lang:commons-lang` | `2.6` | compile | `2.6` |
| `commons-configuration:commons-configuration` | `1.2` | compile | `1.2` |
| `org.apache.logging.log4j:log4j-core` | `2.25.4` | compile | `2.25.4` |
| `mysql:mysql-connector-java` | `8.0.28` | compile | `8.0.28` |
| `commons-codec:commons-codec` | `1.9` | compile | `1.9` |
| `commons-io:commons-io` | `2.14.0` | compile | `2.14.0` |
| `org.metawidget.modules:metawidget-all` | `3.9` | compile | `3.9` |
| `net.xeoh:jspf.core` | `1.0.2` | compile | `1.0.2` |
| `commons-cli:commons-cli` | `1.2` | compile | `1.2` |
| `net.authorize:anet-java-sdk` | `1.8.1` | compile | `1.8.1` |
| `org.apache.httpcomponents:httpclient` | `[4.3.6,)` | compile | `[4.3.6,)` |
| `org.apache.httpcomponents:httpcore` | `4.0.1` | compile | `4.0.1` |
| `commons-validator:commons-validator` | `1.4.0` | compile | `1.4.0` |
| `com.jidesoft:jide-oss` | `3.6.1` | compile | `3.6.1` |
| `javax.json:javax.json-api` | `1.0` | compile | `1.0` |
| `org.glassfish:javax.json` | `1.0.4` | compile | `1.0.4` |
| `net.sourceforge.barbecue:barbecue` | `1.5-beta1` | compile | `1.5-beta1` |
| `xerces:xercesImpl` | `2.12.2` | compile | `2.12.2` |
| `us.fatehi:magnetictrackparser` | `1.0` | compile | `1.0` |
| `org.jdom:jdom2` | `2.0.6.1` | compile | `2.0.6.1` |
| `jaxen:jaxen` | `1.1.4` | compile | `1.1.4` |
| `c3p0:c3p0` | `0.9.1.2` | compile | `0.9.1.2` |
| `joda-time:joda-time` | `2.3` | compile | `2.3` |
| `org.scream3r:jssc` | `2.8.0` | compile | `2.8.0` |
| `org.postgresql:postgresql` | `42.7.12` | compile | `42.7.12` |
| `com.pax.poslink:poslink` | `1.0` | system | `1.0` |
| `com.google.maps:google-maps-services` | `0.1.12` | compile | `0.1.12` |
| `net.java.dev.jna:jna` | `4.2.2` | compile | `4.2.2` |
| `net.java.dev.jna:jna-platform` | `4.2.2` | compile | `4.2.2` |

## Build plugins

Explicit plugins from the current POM; Maven defaults are not listed here.

| Plugin | Version |
| --- | --- |
| `maven-surefire-plugin` | `3.5.4` |
| `maven-compiler-plugin` | `3.10.0` |
| `maven-resources-plugin` | `2.4` |
| `maven-jar-plugin` | `2.3.1` |
| `maven-assembly-plugin` | `2.4` |
| `maven-antrun-plugin` | `1.6` |

The packaged POM has the same build plugin declarations except Surefire, which was added during test prework.
Both POMs set compiler source/target to `1.7` in properties and compiler configuration.
`org.eclipse.m2e:lifecycle-mapping:1.0.0` is IDE-only plugin management.
