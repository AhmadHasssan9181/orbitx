/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.attachments.preview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import tech.orbitweb.orbitx.libraries.designsystem.utils.CommonDrawables
import tech.orbitweb.orbitx.libraries.mediaviewer.api.local.LocalMedia
import tech.orbitweb.orbitx.libraries.mediaviewer.api.local.LocalMediaRenderer

/**
 * An implementation of [LocalMediaRenderer] that displays a sample background image.
 * To be used for Previews only.
 */
internal class SampleMediaRenderer : LocalMediaRenderer {
    @Composable
    override fun Render(localMedia: LocalMedia) {
        Image(
            painter = painterResource(id = CommonDrawables.sample_background),
            modifier = Modifier.fillMaxSize(),
            contentDescription = null,
        )
    }
}
