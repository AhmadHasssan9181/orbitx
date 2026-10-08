# Installing OrbitX from CI Artifacts

<!--- TOC -->

* [Installing from GitHub Actions](#installing-from-github-actions)
* [Steps](#steps)

<!--- END -->

## Installing from GitHub Actions

OrbitX CI builds universal APKs on pull requests and pushes to `master`.

## Steps

1. Navigate to the Actions tab on [GitHub](https://github.com/AhmadHasssan9181/orbitx/actions).
2. Open the desired workflow run (e.g. `Build debug APKs`).
3. Scroll down to the **Artifacts** section.
4. Download the `orbitx-apk-maestro` or `app-gplay-debug` zip file.
5. Extract the archive and install the APK via `adb install -r <path_to_apk>`.
