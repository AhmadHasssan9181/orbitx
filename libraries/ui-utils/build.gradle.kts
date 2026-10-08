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
    namespace = "tech.orbitweb.orbitx.libraries.ui.utils"
}

dependencies {
    implementation(projects.libraries.androidutils)
    implementation(projects.services.toolbox.impl)
    implementation(libs.androidx.compose.ui.text)

    testCommonDependencies(libs)
}
