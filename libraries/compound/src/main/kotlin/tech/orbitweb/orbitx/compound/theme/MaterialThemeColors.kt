/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.compound.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import tech.orbitweb.orbitx.compound.previews.ColorsSchemePreview
import tech.orbitweb.orbitx.compound.tokens.generated.SemanticColors
import tech.orbitweb.orbitx.compound.tokens.generated.compoundColorsHcDark
import tech.orbitweb.orbitx.compound.tokens.generated.compoundColorsHcLight

fun SemanticColors.toMaterialColorScheme(): ColorScheme {
    return if (isLight) {
        toMaterialColorSchemeLight()
    } else {
        toMaterialColorSchemeDark()
    }
}

@Preview(heightDp = 1200)
@Composable
internal fun ColorsSchemeLightPreview() = ElementTheme {
    ColorsSchemePreview(
        Color.Black,
        Color.White,
        ElementTheme.materialColors,
    )
}

@Preview(heightDp = 1200)
@Composable
internal fun ColorsSchemeLightHcPreview() = ElementTheme(
    compoundLight = compoundColorsHcLight,
) {
    ColorsSchemePreview(
        Color.Black,
        Color.White,
        ElementTheme.materialColors,
    )
}

@Preview(heightDp = 1200)
@Composable
internal fun ColorsSchemeDarkPreview() = ElementTheme(
    theme = Theme.Dark,
) {
    ColorsSchemePreview(
        Color.White,
        Color.Black,
        ElementTheme.materialColors,
    )
}

@Preview(heightDp = 1200)
@Composable
internal fun ColorsSchemeDarkHcPreview() = ElementTheme(
    theme = Theme.Dark,
    compoundDark = compoundColorsHcDark,
) {
    ColorsSchemePreview(
        Color.White,
        Color.Black,
        ElementTheme.materialColors,
    )
}

@Preview(heightDp = 1200)
@Composable
internal fun ColorsSchemeBlackPreview() = ElementTheme(
    theme = Theme.Black
) {
    ColorsSchemePreview(
        Color.White,
        Color.Black,
        ElementTheme.materialColors,
    )
}
