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
    namespace = "tech.orbitweb.orbitx.libraries.voicerecorder.test"
}

dependencies {
    api(projects.libraries.voicerecorder.api)
    implementation(projects.tests.testutils)

    implementation(libs.coroutines.test)
    implementation(libs.test.truth)
    implementation(projects.libraries.core)
}
