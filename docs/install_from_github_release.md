# Installing OrbitX Android from a GitHub Release

This document explains how to install OrbitX Android from a GitHub Release.

<!--- TOC -->

* [Installing the universal APK](#installing-the-universal-apk)
  * [Instructions](#instructions)
  * [Steps](#steps)
  * [Existing installations](#existing-installations)
* [Installing from the App Bundle](#installing-from-the-app-bundle)
  * [Requirements](#requirements)
  * [Steps](#steps-1)

<!--- END -->

## Installing the universal APK

### Instructions

The easiest way to install OrbitX from a GitHub release is to use the universal APK attached to the release.

### Steps

1. Open the [OrbitX Releases](https://github.com/AhmadHasssan9181/orbitx/releases) page on your device.
2. Download the `.apk` asset.
3. Tap the downloaded file to install OrbitX.

### Existing installations

If you already have OrbitX installed:
- Upgrading to a newer version retains all local encrypted session data.
- Switching between debug and release builds requires uninstalling first due to signature differences.

## Installing from the App Bundle

### Requirements

Releases provide an Android App Bundle (`.aab`). You can use `bundletool` to generate an APK tailored specifically to your target architecture:

```bash
git clone https://github.com/AhmadHasssan9181/orbitx.git
```

### Steps

1. Download the release bundle from [Releases](https://github.com/AhmadHasssan9181/orbitx/releases).
2. Generate device-optimized APKs:
```bash
bundletool build-apks --bundle=app-release.aab --output=./orbitx.apks \
      --ks=./app/signature/debug.keystore --ks-pass=pass:android --ks-key-alias=androiddebugkey --key-pass=pass:android \
      --overwrite
```
3. Install to connected device:
```bash
bundletool install-apks --apks=./orbitx.apks
```
