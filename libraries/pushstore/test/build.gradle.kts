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
    namespace = "tech.orbitweb.orbitx.libraries.pushstore.test"
}

dependencies {
    api(projects.libraries.matrix.api)
    api(libs.coroutines.core)
    implementation(libs.coroutines.test)
    implementation(projects.tests.testutils)
    implementation(projects.libraries.pushstore.api)
}
