# Dependency Injection with Metro

The dependency injection library in OrbitX is [Metro](https://zacsweers.github.io/metro/latest/). It replaces legacy Dagger and Anvil setups.

Features:
- Fast compile-time dependency injection without runtime reflection overhead.
- Direct integration with Kotlin KSP.
- Constructor injection using `@Inject`.
- Contributed bindings using `@ContributesBinding(AppScope::class)`.
