# OAuth / OIDC Configuration in OrbitX

This document outlines OAuth 2.0 / OpenID Connect (MSC2967) authentication flows supported by OrbitX.

## Client Metadata

Client metadata configured in OrbitX:

```kotlin
clientName = "OrbitX"
redirectUri = "tech.orbitweb.orbitx:/"
clientUri = "https://orbitweb.tech"
tosUri = "https://orbitweb.tech/terms"
policyUri = "https://orbitweb.tech/privacy"
```

## Matrix Rust SDK Integration

Authentication flows leverage `matrix-rust-sdk` native OIDC clients to handle token exchange, PKCE challenges (S256), and secure session establishment with Matrix homeservers.
