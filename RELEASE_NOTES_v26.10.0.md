# OrbitX Android v26.10.0 — Production Release

<p align="center">
  <img alt="OrbitX" src="https://img.shields.io/badge/OrbitX-Android%20v26.10.0-7C3AED?style=for-the-badge&logo=android&logoColor=white" />
  <img alt="Matrix" src="https://img.shields.io/badge/Matrix-Protocol%20v1.11-000000?style=for-the-badge&logo=matrix&logoColor=white" />
  <img alt="FCM" src="https://img.shields.io/badge/FCM%20v1-Operational-FFA000?style=for-the-badge&logo=firebase&logoColor=white" />
  <img alt="License" src="https://img.shields.io/badge/License-AGPL--3.0-blue?style=for-the-badge" />
</p>

---

## 🌟 What's New in OrbitX v26.10.0

### 🚀 Push Notification Gateway & Fast Fetch Engine
- **Dedicated Matrix Push Gateway**: Live, sovereign push gateway deployed at `https://orbitweb-tech.onrender.com/_matrix/push/v1/notify` supporting Google Cloud FCM HTTP v1 API (`orbit-296fd`).
- **Fast Fetch Direct Resolver**: Completely eliminates Android `WorkManager` / `JobScheduler` queue latency (~2.5–3.0s) by resolving encrypted Matrix events immediately in the active background coroutine under high-priority WakeLock.
- **24/7 Keepalive Monitoring**: Automated background health-check cron ensuring zero cold-start delay for push dispatch.

### 🎨 Next-Generation Media Editor Suite
- **Interactive Stickers & Overlays**: Full 360° pinch-to-rotate, two-finger scaling, and unrestricted drag across the entire image canvas. Fixed sticker halfway boundary clamp.
- **Precision Crop & Transforms**: Aspect ratio presets (`Free`, `1:1`, `4:5`, `16:9`, `9:16`), 90° rotation, and horizontal/vertical flipping.
- **Cosmic Color Matrix Filters**: Instant GPU-accelerated filters (`Vivid`, `Mono`, `Warm`, `Cool`, `Vintage`).
- **Coordinate-Accurate Bitmap Baking**: Fixed coordinate translation offset that previously caused cropped elements to jump to the top-left on save.
- **Freehand Brush Tool**: Smooth drawing with variable stroke widths (`Fine`, `Medium`, `Bold`), curated color palette, and undo stack.

### 🔒 Privacy & Cryptographic Hardening
- **Ephemeral View-Once Media**: Envelope encryption with iterated HMAC-SHA256, runtime screenshot prevention (`FLAG_SECURE`), and secure multi-pass disk shredding upon destruction.
- **Zero Third-Party Telemetry**: Disabled external tracking SDKs, analytics, and Element-specific telemetry.
- **Hardened Network Security**: Scoped user CA certificate trust exclusively to debug builds, enforcing strict system CA validation in production.

### ⚖️ Legal & Upstream Compliance
- **AGPL-3.0 Attribution**: Included complete credit notices, written source code availability offer, and prominent SQLCipher (Zetetic LLC) attribution in compliance with AGPL-3.0 §5(a) and §6.
- **Rebranding Finalization**: Cleaned all deep links to `orbitx://`, eliminated `REQUEST_INSTALL_PACKAGES` permission, and purged legacy upstream changelogs.

---

## 📦 Release Binaries & Verification

| Asset | Target Architecture | Size | SHA-256 Checksum |
| :--- | :--- | :--- | :--- |
| **`OrbitX-v26.10.0-arm64-v8a.apk`** | Modern 64-bit Android (Phones & Tablets) | 115 MB | `AFF75A1365A1D978F14A35E9A8DD5BF2E947A436DC1A2A02D4CA6A7357C9458F` |
| **`OrbitX-v26.10.0-universal.apk`** | Universal (All CPU Architectures) | 362 MB | `4CDFD3E76F357BEF4F0C845518FE0395CAB71158569C7DDD91FF3A9929BD61B1` |

### Verifying APK Checksum
```bash
# On Linux / macOS:
sha256sum OrbitX-v26.10.0-arm64-v8a.apk

# On Windows (PowerShell):
Get-FileHash OrbitX-v26.10.0-arm64-v8a.apk -Algorithm SHA256
```
