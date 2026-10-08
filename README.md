# OrbitX Android

[![OrbitX](https://img.shields.io/badge/OrbitX-Android-7C3AED.svg)](https://orbitweb.tech)
[![License](https://img.shields.io/badge/License-AGPL--3.0-blue.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/Platform-Android%207.0%2B-green.svg)](https://developer.android.com)
[![Matrix](https://img.shields.io/badge/Matrix-Protocol%20v1.11-000000.svg?logo=matrix)](https://matrix.org)

**OrbitX Android** is the next-generation, high-performance decentralized Matrix communication client for Android. Built from the ground up for speed, security, and digital sovereignty, OrbitX combines native **Jetpack Compose**, the **Matrix Rust SDK**, hardware-accelerated end-to-end encryption (Olm/Megolm), zero-telemetry privacy, ephemeral view-once media self-destruction, and rich cosmic design aesthetics.

---

## Highlights & Features

- **Decentralized & Sovereign:** Connect to your own homeserver (`https://orbitweb.tech` or any self-hosted Matrix server) with 100% data ownership.
- **End-to-End Encryption (E2EE):** Military-grade cryptographic privacy powered by the audited Matrix Rust SDK (Olm & Megolm double ratchets).
- **Ephemeral View-Once Media:** Send photos, videos, and voice messages with WhatsApp-style view-once self-destruction. Envelope AES-256-CTR encryption with PBKDF2 key derivation, runtime screen capture protection (`FLAG_SECURE`), event redaction, and disk cache overwriting.
- **Built-in Media Editing Suite:**
  - **Photo Editor:** Precision cropping with aspect ratio presets (`Free`, `1:1`, `4:5`, `16:9`, `9:16`), 90° rotation, horizontal/vertical flipping, and cosmic color matrix filters (`Vivid`, `Mono`, `Warm`, `Cool`, `Vintage`).
  - **Video Editor:** Direct native audio stripping (`stripAudioFromVideo`), compression presets, and video preview controls.
  - **Voice Notes & Audio:** Waveform recording and interactive scrubber, playback speed controls (`1.0x`, `1.5x`, `2.0x`), and ephemeral audio self-destruction.
- **Zero External Telemetry:** All third-party analytics trackers (PostHog, Sentry, telemetry gateways) are completely disabled. No analytics servers are contacted.
- **Cosmic UI Design System:** Stunning dark mode with rich violet/indigo palettes, glassmorphism cards, micro-interactions, and custom typography.
- **Cross-Platform Matrix Federation:** Interoperate seamlessly with other federated Matrix clients across Web, Desktop, and iOS.

---

## Architecture

OrbitX Android follows modern Android architecture principles:
- **Core Engine:** [Matrix Rust SDK](https://github.com/matrix-org/matrix-rust-sdk) via UniFFI bindings for encryption, sync, and networking.
- **UI Toolkit:** 100% [Jetpack Compose](https://developer.android.com/jetpack/compose) declarative UI with Compose Material 3 and Compound design tokens.
- **Navigation:** Scalable hierarchical backstack and node lifecycle management powered by [Appyx](https://github.com/bumble-tech/appyx).
- **Dependency Injection:** Compile-time DI via [Metro](https://github.com/zacsweers/metro) and Kotlin Multiplatform abstractions.
- **Concurrency:** Kotlin Coroutines & Reactive Flows with unidirectional state flows (`Presenter` -> `State` -> `View`).

---

## Getting Started

### Prerequisites
- **JDK:** OpenJDK 21 or later
- **Android SDK:** Compile SDK 35, Min SDK 24 (Android 7.0+)
- **Build Tool:** Gradle 9.5.1 wrapper (`./gradlew`)

### Build Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/AhmadHasssan9181/orbitx.git
   cd orbitx
   ```

2. **Build debug APK:**
   ```bash
   ./gradlew assembleGplayDebug
   ```

3. **Compile Kotlin validation check:**
   ```bash
   ./gradlew compileGplayDebugKotlin
   ```

4. **Install on connected device or emulator:**
   ```bash
   ./gradlew installGplayDebug
   ```

---

## Privacy & Security

- **Zero Third-Party Telemetry:** OrbitX does not transmit telemetry, analytics, crashes, or diagnostics to external tracking services.
- **Local Key Storage:** Cryptographic session keys are stored locally in Android EncryptedSharedPreferences and hardware-backed Android KeyStore.
- **No Proprietary Server Reliance:** Complete decoupling from proprietary push or identity servers.

---

## Repositories in the Orbit Ecosystem

- **Orbit Android Client:** [AhmadHasssan9181/orbitx](https://github.com/AhmadHasssan9181/orbitx)
- **Orbit Web Client:** [AhmadHasssan9181/orbit-web](https://github.com/AhmadHasssan9181/orbit-web)
- **Orbit Ecosystem Portal:** [AhmadHasssan9181/orbitweb.tech](https://github.com/AhmadHasssan9181/orbitweb.tech)

---

## Copyright & License

Copyright (c) 2026 Orbit Team.

This software is licensed under the GNU Affero General Public License v3.0 (AGPL-3.0) or commercial licensing terms from Orbit Team.
