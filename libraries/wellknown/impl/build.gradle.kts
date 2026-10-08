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
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "tech.orbitweb.orbitx.libraries.wellknown.impl"
}

setupDependencyInjection()

dependencies {
    api(projects.libraries.wellknown.api)
    implementation(libs.androidx.annotationjvm)
    implementation(libs.coroutines.core)
    implementation(platform(libs.network.retrofit.bom))
    implementation(libs.network.retrofit)
    implementation(libs.serialization.json)
    implementation(projects.libraries.core)
    implementation(projects.libraries.androidutils)
    implementation(projects.libraries.architecture)
    implementation(projects.libraries.matrix.api)
    implementation(projects.libraries.network)
    implementation(projects.libraries.cachestore.api)
    implementation(projects.services.toolbox.api)

    testCommonDependencies(libs)
    testImplementation(libs.coroutines.core)
    testImplementation(projects.libraries.cachestore.test)
    testImplementation(projects.libraries.matrix.test)
    testImplementation(projects.libraries.wellknown.test)
    testImplementation(projects.services.toolbox.test)
}
