/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import tech.orbitweb.orbitx.compound.theme.ElementTheme

val bgCanvasWithTransparency: Color
    @Composable
    get() = ElementTheme.colors.bgCanvasDefault.copy(alpha = 0.6f)
