/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.compound.colors

import tech.orbitweb.orbitx.compound.tokens.generated.SemanticColors
import tech.orbitweb.orbitx.compound.tokens.generated.compoundColorsDark
import tech.orbitweb.orbitx.compound.tokens.generated.compoundColorsLight

data class SemanticColorsLightDark(
    val light: SemanticColors,
    val dark: SemanticColors,
) {
    companion object {
        val default = SemanticColorsLightDark(
            light = compoundColorsLight,
            dark = compoundColorsDark,
        )
    }
}
