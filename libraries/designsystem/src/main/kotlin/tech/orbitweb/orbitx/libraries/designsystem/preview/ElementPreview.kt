/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.preview

import androidx.annotation.DrawableRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.res.ResourcesCompat
import coil3.annotation.ExperimentalCoilApi
import coil3.asImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.compound.theme.Theme
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Surface
import tech.orbitweb.orbitx.libraries.designsystem.utils.CommonDrawables

@OptIn(ExperimentalCoilApi::class)
@Composable
@Suppress("ModifierMissing")
fun ElementPreview(
    theme: Theme = if (isSystemInDarkTheme()) Theme.Dark else Theme.Light,
    showBackground: Boolean = true,
    @DrawableRes
    drawableFallbackForImages: Int = CommonDrawables.sample_background,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    CompositionLocalProvider(
        LocalAsyncImagePreviewHandler provides AsyncImagePreviewHandler {
            ResourcesCompat.getDrawable(context.resources, drawableFallbackForImages, null)!!.asImage()
        }
    ) {
        ElementTheme(theme = theme) {
            if (showBackground) {
                // If we have a proper contentColor applied we need a Surface instead of a Box
                Surface(content = content)
            } else {
                content()
            }
        }
    }
}
