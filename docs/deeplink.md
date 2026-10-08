# OrbitX Android Deeplinks

<!--- TOC -->

* [Introduction](#introduction)
  * [Asset Links](#asset-links)
  * [Supported Links](#supported-links)
* [Developer Tools](#developer-tools)

<!--- END -->

## Introduction

OrbitX Android supports deep linking to specific screens, rooms, and users.

### Asset Links

The asset links verification file is hosted at:
`https://orbitweb.tech/.well-known/assetlinks.json`

### Supported Links

Link to a user:
> `https://orbitweb.tech/#/user/@alice:orbitweb.tech`

Link to a room by id or alias:
> `https://orbitweb.tech/#/room/!roomid:orbitweb.tech`
> `https://orbitweb.tech/#/room/#orbit:orbitweb.tech`

Link to a room with a specific event:
> `https://orbitweb.tech/#/room/!roomid:orbitweb.tech/$eventid`

## Developer Tools

Using an Android 12+ emulator:

Ensure link verification is enabled:
```bash
adb shell am compat enable 175408749 tech.orbitweb.orbitx.debug
```

Reset link verifications for OrbitX:
```bash
adb shell pm set-app-links --package tech.orbitweb.orbitx.debug 0 all
```

Force verification:
```bash
adb shell pm verify-app-links --re-verify tech.orbitweb.orbitx.debug
```

Inspect verification state:
```bash
adb shell pm get-app-links tech.orbitweb.orbitx.debug
```
