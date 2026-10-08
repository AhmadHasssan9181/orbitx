/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.compound.screenshot

import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Test
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import tech.orbitweb.orbitx.compound.screenshot.utils.screenshotFile
import tech.orbitweb.orbitx.compound.theme.MaterialTextPreview
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

@GraphicsMode(GraphicsMode.Mode.NATIVE)
class MaterialTextTest : RobolectricTest() {
    @Test
    @Config(sdk = [35], qualifiers = "w480dp-h1200dp-xxhdpi")
    fun screenshots() {
        captureRoboImage(file = screenshotFile("MaterialText Colors.png")) {
            MaterialTextPreview()
        }
    }
}
