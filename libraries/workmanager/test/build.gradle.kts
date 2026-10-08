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
    namespace = "tech.orbitweb.orbitx.libraries.workmanager.test"
}

dependencies {
    api(projects.libraries.workmanager.api)
    implementation(projects.libraries.matrix.api)
    implementation(projects.tests.testutils)
}
