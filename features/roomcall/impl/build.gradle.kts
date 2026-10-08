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
    namespace = "tech.orbitweb.orbitx.features.roomcall.impl"
}

setupDependencyInjection()

dependencies {
    api(projects.features.roomcall.api)
    implementation(libs.kotlinx.collections.immutable)
    implementation(projects.features.call.api)
    implementation(projects.features.enterprise.api)
    implementation(projects.libraries.architecture)
    implementation(projects.libraries.matrix.api)
    implementation(projects.libraries.matrixui)

    testCommonDependencies(libs, true)
    testImplementation(projects.libraries.matrix.test)
    testImplementation(projects.features.call.test)
    testImplementation(projects.features.enterprise.test)
}
