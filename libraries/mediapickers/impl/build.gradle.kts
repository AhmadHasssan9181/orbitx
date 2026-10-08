import extension.setupDependencyInjection

/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

plugins {
    id("tech.orbitweb.orbitx-compose-library")
}

setupDependencyInjection()

android {
    namespace = "tech.orbitweb.orbitx.libraries.mediapickers.impl"
}

dependencies {
    implementation(projects.libraries.core)
    implementation(projects.libraries.di)
    api(projects.libraries.mediapickers.api)
}
