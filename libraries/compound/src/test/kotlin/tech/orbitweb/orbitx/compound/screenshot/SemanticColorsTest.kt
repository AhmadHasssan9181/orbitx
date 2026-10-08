/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.compound.screenshot

import com.github.takahirom.roborazzi.captureRoboImage
import tech.orbitweb.orbitx.compound.previews.CompoundSemanticColorsDark
import tech.orbitweb.orbitx.compound.previews.CompoundSemanticColorsDarkHc
import tech.orbitweb.orbitx.compound.previews.CompoundSemanticColorsLight
import tech.orbitweb.orbitx.compound.previews.CompoundSemanticColorsLightHc
import tech.orbitweb.orbitx.compound.screenshot.utils.screenshotFile
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@GraphicsMode(GraphicsMode.Mode.NATIVE)
class SemanticColorsTest : RobolectricTest() {
    @Config(sdk = [35], qualifiers = "h2000dp-xhdpi")
    @Test
    fun screenshots() {
        captureRoboImage(file = screenshotFile("Compound Semantic Colors - Light.png")) {
            CompoundSemanticColorsLight()
        }

        captureRoboImage(file = screenshotFile("Compound Semantic Colors - Light HC.png")) {
            CompoundSemanticColorsLightHc()
        }

        captureRoboImage(file = screenshotFile("Compound Semantic Colors - Dark.png")) {
            CompoundSemanticColorsDark()
        }

        captureRoboImage(file = screenshotFile("Compound Semantic Colors - Dark HC.png")) {
            CompoundSemanticColorsDarkHc()
        }
    }
}
