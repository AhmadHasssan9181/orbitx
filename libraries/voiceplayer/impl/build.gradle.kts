import extension.setupDependencyInjection
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
    namespace = "tech.orbitweb.orbitx.libraries.voiceplayer.impl"
}

setupDependencyInjection()

dependencies {
    api(projects.libraries.voiceplayer.api)

    implementation(projects.libraries.architecture)
    implementation(projects.libraries.audio.api)
    implementation(projects.libraries.core)
    implementation(projects.libraries.di)
    implementation(projects.libraries.matrix.api)
    implementation(projects.libraries.mediaplayer.api)
    implementation(projects.libraries.preferences.api)
    implementation(projects.libraries.uiUtils)
    implementation(projects.services.analytics.api)

    implementation(libs.androidx.annotationjvm)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.coroutines.core)

    testCommonDependencies(libs)
    testImplementation(libs.coroutines.core)
    testImplementation(projects.libraries.matrix.test)
    testImplementation(projects.libraries.mediaplayer.test)
    testImplementation(projects.services.analytics.test)
}
