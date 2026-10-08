/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.compound.screenshot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Test
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import tech.orbitweb.orbitx.compound.previews.ColorPreview
import tech.orbitweb.orbitx.compound.screenshot.utils.screenshotFile
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.compound.theme.LinkColor
import tech.orbitweb.orbitx.compound.theme.SnackBarLabelColorDark
import tech.orbitweb.orbitx.compound.theme.SnackBarLabelColorLight
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

@GraphicsMode(GraphicsMode.Mode.NATIVE)
class LegacyColorsTest : RobolectricTest() {
    @Test
    @Config(sdk = [35], qualifiers = "xxhdpi")
    fun screenshots() {
        captureRoboImage(file = screenshotFile("Legacy Colors.png")) {
            ElementTheme {
                Surface {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Legacy Colors")
                        Spacer(modifier = Modifier.height(10.dp))
                        LegacyColorPreview(
                            color = LinkColor,
                            name = "Link"
                        )
                        LegacyColorPreview(
                            color = SnackBarLabelColorLight,
                            name = "SnackBar Label - Light"
                        )
                        LegacyColorPreview(
                            color = SnackBarLabelColorDark,
                            name = "SnackBar Label - Dark"
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun LegacyColorPreview(color: Color, name: String) {
        ColorPreview(
            backgroundColor = Color.White,
            foregroundColor = Color.Black,
            name = name,
            color = color
        )
    }
}
