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
    namespace = "tech.orbitweb.orbitx.libraries.push.test"
}

dependencies {
    api(projects.libraries.push.api)
    api(projects.libraries.pushproviders.api)
    implementation(projects.libraries.designsystem)
    implementation(projects.libraries.matrix.api)
    implementation(projects.libraries.preferences.api)
    implementation(projects.libraries.matrixui)
    implementation(projects.libraries.workmanager.api)
    implementation(projects.tests.testutils)
    implementation(libs.androidx.core)
    implementation(libs.coil.compose)
    implementation(libs.coil.test)
    implementation(libs.test.robolectric)
}
