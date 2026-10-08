/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import tech.orbitweb.orbitx.libraries.designsystem.components.dialogs.ErrorDialog
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@Composable
fun RenderingMapsNotSupportedDialog(onSubmit: () -> Unit) {
    ErrorDialog(
        title = stringResource(CommonStrings.vulkan_not_supported_dialog_title_android),
        content = stringResource(CommonStrings.vulkan_not_supported_dialog_content_android),
        onSubmit = onSubmit,
    )
}

@PreviewsDayNight
@Composable
internal fun RenderingMapsNotSupportedDialogPreview() = ElementPreview {
    RenderingMapsNotSupportedDialog(onSubmit = {})
}
