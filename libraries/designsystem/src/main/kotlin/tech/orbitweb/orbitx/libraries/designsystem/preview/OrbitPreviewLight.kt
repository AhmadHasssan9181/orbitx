/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.preview

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import tech.orbitweb.orbitx.compound.theme.Theme
import tech.orbitweb.orbitx.libraries.designsystem.utils.CommonDrawables

@Composable
fun OrbitPreviewLight(
    showBackground: Boolean = true,
    @DrawableRes
    drawableFallbackForImages: Int = CommonDrawables.sample_background,
    content: @Composable () -> Unit,
) {
    OrbitPreview(
        theme = Theme.Light,
        showBackground = showBackground,
        drawableFallbackForImages = drawableFallbackForImages,
        content = content,
    )
}

@Composable
fun ElementPreviewLight(
    showBackground: Boolean = true,
    @DrawableRes
    drawableFallbackForImages: Int = CommonDrawables.sample_background,
    content: @Composable () -> Unit,
) = OrbitPreviewLight(
    showBackground = showBackground,
    drawableFallbackForImages = drawableFallbackForImages,
    content = content,
)
