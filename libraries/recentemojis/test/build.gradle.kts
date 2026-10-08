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
    namespace = "tech.orbitweb.orbitx.libraries.recentemojis.test"
}

dependencies {
    api(projects.libraries.matrix.api)
    api(libs.coroutines.core)

    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.coroutines.test)
    implementation(projects.tests.testutils)
    implementation(projects.libraries.recentemojis.api)
    implementation(libs.matrix.emojibase.bindings)
}
