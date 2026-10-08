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
    namespace = "tech.orbitweb.orbitx.services.appnavstate.test"
}

dependencies {
    api(projects.libraries.matrix.api)
    api(projects.services.appnavstate.api)
    implementation(libs.coroutines.core)
    implementation(libs.androidx.lifecycle.runtime)
}
