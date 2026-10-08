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
    namespace = "tech.orbitweb.orbitx.libraries.mediaplayer.impl"
}

setupDependencyInjection()

dependencies {
    api(projects.libraries.mediaplayer.api)
    implementation(libs.androidx.media3.exoplayer)

    implementation(projects.libraries.audio.api)
    implementation(projects.libraries.core)
    implementation(projects.libraries.di)

    implementation(libs.coroutines.core)

    testCommonDependencies(libs)
    testImplementation(projects.libraries.audio.test)
    testImplementation(libs.coroutines.core)
}
