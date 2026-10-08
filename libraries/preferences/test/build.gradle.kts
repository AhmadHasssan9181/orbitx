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
    namespace = "tech.orbitweb.orbitx.libraries.preferences.test"
}

dependencies {
    api(projects.libraries.preferences.api)
    implementation(projects.libraries.matrix.api)
    implementation(projects.tests.testutils)
    implementation(libs.coroutines.core)
    implementation(libs.androidx.datastore.preferences)
}
