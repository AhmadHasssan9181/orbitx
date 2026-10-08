import extension.testCommonDependencies

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
    namespace = "tech.orbitweb.orbitx.libraries.preferences.api"
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(projects.libraries.matrix.api)
    implementation(libs.androidx.datastore.preferences)

    testCommonDependencies(libs)
    testImplementation(projects.libraries.preferences.test)
}
