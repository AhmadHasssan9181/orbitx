# OrbitX Android Design System

<!--- TOC -->

* [Introduction](#introduction)
* [Design Language](#design-language)
* [Colors & Theme](#colors-theme)
* [Typography](#typography)
* [Icons & Assets](#icons-assets)

<!--- END -->

## Introduction

OrbitX Android utilizes a modern Jetpack Compose design system designed for seamless, high-performance messaging with privacy and beauty at its core.

## Design Language

- **Cosmic Dark Palette**: Deep canvas tones (`#12111A`, `#181724`), accented with vibrant electric indigo and celestial violet gradients.
- **Glassmorphic Elevations**: Translucent elevated surfaces with subtle borders and gentle blur backdrops.
- **Micro-Animations & Feedback**: Smooth entry/exit animations, progress indicators, and rich visual feedback on interactive elements.

## Colors & Theme

Theme attributes and tokens are located in the `compound` and `libraries/designsystem` modules:
- Primary Text: `ElementTheme.colors.textPrimary`
- Secondary Text: `ElementTheme.colors.textSecondary`
- Action Primary: `ElementTheme.colors.iconPrimary`
- Background Canvas: `ElementTheme.colors.bgCanvasDefault`

## Typography

OrbitX uses clean, modern sans-serif typography scales:
- Titles: `fontHeadingLgBold`, `fontHeadingMdBold`
- Body: `fontBodyLgRegular`, `fontBodyMdMedium`, `fontBodySmRegular`

## Icons & Assets

Vector drawables and Compound icons are packaged in `libraries/designsystem`. When adding new assets, ensure SVG vector drawables are placed in `res/drawable/` and have adequate accessibility labels.
