/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */
plugins {
    id("tech.orbitweb.orbitx-compose-library")
}

android {
    namespace = "tech.orbitweb.orbitx.tests.testutils"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.test.junit)
    implementation(libs.test.truth)
    implementation(libs.test.robolectric)
    implementation(libs.coroutines.test)
    implementation(projects.libraries.androidutils)
    implementation(projects.libraries.architecture)
    implementation(projects.libraries.core)
    implementation(projects.libraries.designsystem)
    implementation(projects.libraries.uiStrings)
    implementation(projects.services.toolbox.api)
    implementation(libs.test.turbine)
    implementation(libs.molecule.runtime)
    implementation(libs.androidx.compose.ui.test.junit)
}
