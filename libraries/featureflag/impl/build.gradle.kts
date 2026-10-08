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
    id("kotlin-parcelize")
}

android {
    namespace = "tech.orbitweb.orbitx.libraries.featureflag.impl"
}

setupDependencyInjection()

dependencies {
    api(projects.libraries.featureflag.api)
    implementation(libs.androidx.datastore.preferences)
    implementation(projects.appconfig)
    implementation(projects.libraries.di)
    implementation(projects.libraries.androidutils)
    implementation(projects.libraries.core)
    implementation(projects.libraries.preferences.api)
    implementation(libs.coroutines.core)

    testCommonDependencies(libs)
    testImplementation(projects.libraries.matrix.test)
    testImplementation(projects.libraries.featureflag.test)
}
