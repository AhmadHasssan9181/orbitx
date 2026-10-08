/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.compound.theme

import androidx.compose.ui.graphics.Color
import tech.orbitweb.orbitx.compound.annotations.CoreColorToken
import tech.orbitweb.orbitx.compound.tokens.generated.internal.DarkColorTokens
import tech.orbitweb.orbitx.compound.tokens.generated.internal.LightColorTokens

// =================================================================================================
// IMPORTANT!
// We should not be adding any new colors here. This file is only for legacy colors.
// In fact, we should try to remove any references to these colors as we
// iterate through the designs. All new colors should come from Compound's Design Tokens.
// =================================================================================================

val LinkColor = Color(0xFF0086E6)

@OptIn(CoreColorToken::class)
val SnackBarLabelColorLight = LightColorTokens.colorGray700
@OptIn(CoreColorToken::class)
val SnackBarLabelColorDark = DarkColorTokens.colorGray700
