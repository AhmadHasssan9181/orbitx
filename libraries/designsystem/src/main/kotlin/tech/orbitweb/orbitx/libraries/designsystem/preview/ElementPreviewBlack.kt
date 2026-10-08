/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.preview

import androidx.compose.runtime.Composable
import tech.orbitweb.orbitx.compound.theme.Theme

@Composable
fun ElementPreviewBlack(
    showBackground: Boolean = true,
    content: @Composable () -> Unit
) {
    ElementPreview(
        theme = Theme.Black,
        showBackground = showBackground,
        content = content
    )
}
