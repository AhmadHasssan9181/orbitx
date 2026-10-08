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
    namespace = "tech.orbitweb.orbitx.libraries.cryptography.impl"
}

setupDependencyInjection()

dependencies {
    implementation(projects.libraries.di)
    implementation(libs.coroutines.core)
    api(projects.libraries.cryptography.api)

    testCommonDependencies(libs)
}
