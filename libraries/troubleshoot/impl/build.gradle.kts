import extension.setupDependencyInjection
import extension.testCommonDependencies

/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */
plugins {
    id("tech.orbitweb.orbitx-compose-library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "tech.orbitweb.orbitx.libraries.troubleshoot.impl"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

setupDependencyInjection()

dependencies {
    implementation(projects.libraries.architecture)
    implementation(projects.libraries.designsystem)
    implementation(projects.libraries.di)
    implementation(projects.libraries.matrix.api)
    implementation(projects.libraries.uiStrings)
    api(projects.libraries.troubleshoot.api)
    api(projects.libraries.push.api)
    implementation(projects.services.analytics.api)

    testCommonDependencies(libs, true)
    testImplementation(projects.services.analytics.test)
    testImplementation(projects.libraries.matrix.test)
    testImplementation(projects.libraries.push.test)
}
