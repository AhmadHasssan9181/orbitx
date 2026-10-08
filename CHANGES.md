# OrbitX Android Changelog

## OrbitX v1.0.0

### ✨ Features
- **Sovereign Matrix Architecture**: Complete sovereign independence pointing directly to `https://orbitweb.tech` by default.
- **View-Once Media Bubble & Self-Destruction**:
  - Ephemeral view-once photo, video, and audio playback.
  - Automatic cryptographic fek key purging and file byte overwriting on viewing completion.
  - Distinct burned/destroyed visual indicators preventing replay.
- **Rich Media Editing Studio**:
  - Crop and aspect ratio selector (`Free`, `1:1`, `4:5`, `16:9`, `9:16`) with high-precision gestures.
  - Cosmic filter presets (Cyberpunk, Neon Orbit, Synthwave, Twilight, Matrix Mono) with real-time Compose shader matrix effects.
  - Audio stripping and in-memory direct NIO buffer media muxing for videos.
  - Interactive voice waveform recording and scrubbable playback.
- **Privacy & Zero-Telemetry**:
  - Disabled all 3rd-party tracking, analytics, Sentry, and PostHog endpoints.
  - Zero external calls to legacy matrix/element servers.
- **Modern Jetpack Compose UI**:
  - Cosmic dark-mode palette with indigo/violet accents and glassmorphic elevated bubbles.
  - Smooth micro-interactions and transitions.
