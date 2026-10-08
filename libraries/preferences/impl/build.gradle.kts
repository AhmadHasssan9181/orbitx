import extension.setupDependencyInjection
import extension.testCommonDependencies

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
    namespace = "tech.orbitweb.orbitx.libraries.preferences.impl"
}

setupDependencyInjection()

dependencies {
    api(projects.libraries.preferences.api)
    implementation(libs.androidx.datastore.preferences)
    implementation(projects.libraries.androidutils)
    implementation(projects.libraries.di)
    implementation(projects.libraries.core)
    implementation(projects.libraries.matrix.api)
    implementation(projects.libraries.sessionStorage.api)
    testCommonDependencies(libs)
    testImplementation(projects.libraries.preferences.test)
}
