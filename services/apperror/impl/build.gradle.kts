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

setupDependencyInjection()

android {
    namespace = "tech.orbitweb.orbitx.services.apperror.impl"
}

dependencies {
    implementation(projects.libraries.core)
    implementation(projects.libraries.di)
    implementation(projects.services.toolbox.api)

    implementation(libs.coroutines.core)
    implementation(libs.androidx.corektx)

    api(projects.services.apperror.api)

    testCommonDependencies(libs)
    testImplementation(projects.services.toolbox.test)
}
