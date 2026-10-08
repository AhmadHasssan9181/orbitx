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
}

setupDependencyInjection()

android {
    namespace = "tech.orbitweb.orbitx.libraries.dateformatter.impl"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    kotlin {
        compilerOptions {
            optIn = listOf(
                "kotlin.time.ExperimentalTime"
            )
        }
    }
}

dependencies {
    implementation(projects.libraries.core)
    implementation(projects.libraries.designsystem)
    implementation(projects.libraries.di)
    implementation(projects.libraries.uiStrings)
    implementation(projects.services.toolbox.api)

    api(projects.libraries.dateformatter.api)
    api(libs.datetime)

    testCommonDependencies(libs, true)
    testImplementation(projects.libraries.dateformatter.test)
    testImplementation(projects.services.toolbox.test)
}
