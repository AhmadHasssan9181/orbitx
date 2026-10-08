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
    namespace = "tech.orbitweb.orbitx.libraries.androidutils"

    buildFeatures {
        buildConfig = true
    }
}

setupDependencyInjection()

dependencies {
    implementation(projects.libraries.di)

    implementation(projects.libraries.core)
    implementation(projects.services.toolbox.api)
    implementation(libs.timber)
    implementation(libs.androidx.corektx)
    implementation(libs.androidx.activity.activity)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.exifinterface)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.serialization.json)
    api(libs.androidx.browser)

    testCommonDependencies(libs)
    testImplementation(libs.coroutines.core)
    testImplementation(projects.services.toolbox.test)
}
