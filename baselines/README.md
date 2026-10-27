# Migration baselines

Keep migration snapshots here so they can be committed and cloned on another
computer. This folder is outside `target/`; Maven clean does not remove it.

| Snapshot | File | SHA-256 |
| --- | --- | --- |
| Before Java 21 migration | [2026-10-27 app ZIP](2026-10-27/floreantpos-1.4-build-20261025-164421.zip) | `2f01e20840d1a42ef4bdfad03c3fffda677b7110cd44e2ad89d63a0ecd55aa27` |

The ZIP is 55,324,806 bytes. It is an unchanged copy of the preserved local
package. The matching `.sha256` file is in the same folder. From that folder:

```sh
sha256sum --check floreantpos-1.4-build-20261025-164421.zip.sha256
```

The snapshot includes application settings and embedded Derby databases. Static
comparison found that the server database matches the repository seed's 405
`.dat` files; the single-user database differs in 173 of those files. Its data
has not been fully reviewed. Review the bundled data before sharing the snapshot
with other people. This is not a clean seed fixture.

Preserving the ZIP does not verify application startup or behavior. The
[D01.02 record](2026-10-27/baseline-record.md) lists tools, dependencies and
package build evidence. The full build commit is unknown; the embedded POM
matches the old build-fix commit. Runtime checks remain unfinished. No Java
runtime is bundled in this ZIP.

Store later shareable inputs under this folder or `test-resources/`, and use
repository-relative paths in the plan. Keep live databases and private local
settings out of versioned fixtures. Git tracking does not prove a snapshot is
safe to restore over live data.
