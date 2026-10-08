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
    namespace = "tech.orbitweb.orbitx.services.analytics.test"
}

dependencies {
    api(projects.services.analytics.api)
    implementation(projects.libraries.core)
    implementation(projects.tests.testutils)
    implementation(libs.coroutines.core)
}
