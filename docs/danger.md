## Danger

<!--- TOC -->

* [What does danger check](#what-does-danger-check)
  * [PR check](#pr-check)
  * [Quality check](#quality-check)
* [Setup](#setup)
* [Run danger locally](#run-danger-locally)
* [Useful links](#useful-links)

<!--- END -->

## What does danger check

### PR check

See the [dangerfile](../tools/danger/dangerfile.js).

Checks performed by Danger:
- PR description is not empty
- Warn on oversized PRs (recommending splitting)
- PR contains a clear title and label
- Verifies frozen classes are not modified inappropriately
- Files containing translations are not modified directly by non-authorized scripts

### Quality check

After running checks that generate checkstyle XML reports (Ktlint, lint, Detekt), Danger posts inline PR comments highlighting detected errors and warnings.

## Setup

Run:
```shell
bundle exec danger init
```

## Run danger locally

```shell
bundle exec danger pr <PR_URL> --dangerfile=./tools/danger/dangerfile.js
```

## Useful links

- https://danger.systems/
- https://danger.systems/js/
- https://danger.systems/js/guides/getting_started.html
- https://github.com/danger/awesome-danger
