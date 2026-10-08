# Analytics in OrbitX

<!--- TOC -->

* [Privacy-First Philosophy](#privacy-first-philosophy)
* [Sentry Crash Reporting (Optional)](#sentry-crash-reporting-optional)

<!--- END -->

## Privacy-First Philosophy

OrbitX operates with a strict zero-telemetry policy. All external tracking, analytics endpoints, and behavioral telemetry are disabled by default.

## Sentry Crash Reporting (Optional)

For private enterprise deployments wishing to enable self-hosted crash reporting, configure the Sentry DSN in your `local.properties` file or set the `ORBIT_ANDROID_SENTRY_DSN` environment variable:

```properties
services.analyticsproviders.sentry.dsn=https://your-sentry-dsn/project-id
```
