import extension.testCommonDependencies

/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

plugins {
    id("tech.orbitweb.orbitx-compose-library")
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "tech.orbitweb.orbitx.compound"

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(libs.showkase)
    testCommonDependencies(libs)
    testImplementation(libs.test.roborazzi)
    testImplementation(libs.test.roborazzi.compose)
    testImplementation(libs.test.roborazzi.junit)
}
