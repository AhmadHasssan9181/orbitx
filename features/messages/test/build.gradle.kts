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
    namespace = "tech.orbitweb.orbitx.features.messages.test"
}

dependencies {
    api(projects.features.messages.impl)
    implementation(projects.libraries.architecture)
    implementation(projects.libraries.matrix.test)
    implementation(projects.libraries.audio.test)
    implementation(projects.libraries.mediaplayer.test)
    implementation(projects.libraries.mediaupload.test)
    implementation(projects.libraries.mediaviewer.api)
    implementation(projects.libraries.permissions.test)
    implementation(projects.libraries.preferences.api)
    implementation(projects.libraries.voicerecorder.test)
    implementation(projects.services.analytics.test)
    implementation(projects.tests.testutils)
    implementation(projects.libraries.mediaupload.api)
}
