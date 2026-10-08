# Contributing to OrbitX Android

<!--- TOC -->

* [Contributing to OrbitX](#contributing-to-orbitx)
  * [Translations](#translations)
  * [Reporting Issues & Bugs](#reporting-issues-bugs)
  * [Feature Proposals](#feature-proposals)
  * [Etiquette](#etiquette)
  * [Developer Onboarding](#developer-onboarding)
  * [Submitting Pull Requests](#submitting-pull-requests)
  * [Android Studio Settings](#android-studio-settings)
  * [Compilation](#compilation)
  * [Strings](#strings)
  * [Kotlin Standards](#kotlin-standards)
  * [Code Quality](#code-quality)
  * [Testing](#testing)
  * [Accessibility](#accessibility)
  * [Jetpack Compose](#jetpack-compose)
  * [Authors](#authors)

<!--- END -->

## Contributing to OrbitX

OrbitX Android support and community discussion is hosted on the Orbit Matrix network (`https://orbitweb.tech`).

### Translations

To help translating OrbitX into additional languages, contributions are welcome via the UI string resource files in `libraries/ui-strings`.

- New English strings are placed in `temporary.xml`.
- Key naming follows project conventions:
  - Verbs: `action_`
  - Common nouns: `common_`
  - Screen-specific: `screen_<name>_<key>`
  - Accessibility: `a11y_`

### Reporting Issues & Bugs

Please check if a corresponding issue exists in the [OrbitX GitHub Repository](https://github.com/AhmadHasssan9181/orbitx/issues). If not, please open a new issue using our bug report template.

### Feature Proposals

Before making substantial contributions, please open a GitHub issue describing the user story, interface mockup, and architectural approach.
OrbitX prioritizes:
- Sovereign decentralization and end-to-end encrypted messaging.
- Ephemeral, privacy-first media (view-once photo, audio, video).
- Rich local media editing and optimization.
- Zero telemetry and no tracking.

### Etiquette

- Communicate significant changes through an issue before opening a PR.
- Keep pull requests focused, concise, and well-tested.
- Write clean commit messages explaining the rationale.

### Developer Onboarding

For architecture overview and developer onboarding, see [Developer Onboarding](./docs/_developer_onboarding.md).

### Submitting Pull Requests

1. Fork the repository and create your feature branch.
2. Ensure `./gradlew compileGplayDebugKotlin` passes.
3. Test UI changes on both light and dark themes.
4. Verify accessibility labels.

### Android Studio Settings

Please set the "hard wrap" setting of Android Studio to 160 characters. Format files prior to committing.

### Compilation

Build debug APK:
```bash
./gradlew assembleGplayDebug
```

Run Kotlin compilation:
```bash
./gradlew compileGplayDebugKotlin
```

### Strings

Project strings are located in `libraries/ui-strings/src/main/res/values/`.

### Kotlin Standards

This project is 100% Kotlin. Ensure modern coroutines, Flow, and Jetpack Compose patterns are followed.

### Code Quality

Run static analysis checks:
```bash
./gradlew detekt
./gradlew ktlintCheck
./gradlew lint
```

Format code:
```bash
./gradlew ktlintFormat
```

### Testing

Run unit tests:
```bash
./gradlew test
```

### Accessibility

Ensure all interactive elements have appropriate `contentDescription` and accessibility semantics.

### Jetpack Compose

Wrap all new previews with `ElementPreview` and use `@PreviewsDayNight`.

### Authors

Feel free to add your name to [AUTHORS.md](./AUTHORS.md).
