import extension.buildConfigFieldBoolean

/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

plugins {
    id("tech.orbitweb.orbitx-library")
}

android {
    namespace = "org.rustls.platformverifier"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigFieldBoolean("TEST", false)
    }
}
