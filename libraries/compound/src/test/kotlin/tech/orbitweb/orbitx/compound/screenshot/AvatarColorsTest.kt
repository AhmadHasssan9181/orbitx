/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.compound.screenshot

import com.github.takahirom.roborazzi.captureRoboImage
import tech.orbitweb.orbitx.compound.screenshot.utils.screenshotFile
import tech.orbitweb.orbitx.compound.theme.AvatarColorsPreviewDark
import tech.orbitweb.orbitx.compound.theme.AvatarColorsPreviewLight
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@GraphicsMode(GraphicsMode.Mode.NATIVE)
class AvatarColorsTest : RobolectricTest() {
    @Test
    @Config(sdk = [35], qualifiers = "xxhdpi")
    fun screenshots() {
        captureRoboImage(file = screenshotFile("Avatar Colors - Light.png")) {
            AvatarColorsPreviewLight()
        }
        captureRoboImage(file = screenshotFile("Avatar Colors - Dark.png")) {
            AvatarColorsPreviewDark()
        }
    }
}
