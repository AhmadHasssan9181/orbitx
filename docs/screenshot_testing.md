# Screenshot Testing

<!--- TOC -->

* [Overview](#overview)
* [Setup](#setup)
* [Recording](#recording)
* [Verifying](#verifying)
* [Contributing](#contributing)

<!--- END -->

## Overview

- Screenshot tests record the visual rendering of Composables and verify subsequent runs to detect visual regressions.
- OrbitX uses [Paparazzi](https://github.com/cashapp/paparazzi) to render, record, and verify Composables via [ComposablePreviewScanner](https://github.com/sergio-sastre/ComposablePreviewScanner).
- Screenshot verification runs on PRs as part of the `tests.yml` workflow.

## Setup

- Install Git LFS:
```shell
git lfs install --local
```

## Recording

Recording screenshots locally:
```shell
./gradlew recordPaparazziDebug
```

Paparazzi outputs images to `:tests:uitests/src/test/snapshots`.

## Verifying

```shell
./gradlew verifyPaparazziDebug
```

In case of failure, difference deltas are written to `:tests:uitests/build/paparazzi/failures`.

## Contributing

- Every new Composable screen/component should include an `@PreviewsDayNight` preview.
- Run `./tools/git/validate_lfs.sh` before pushing changes.
