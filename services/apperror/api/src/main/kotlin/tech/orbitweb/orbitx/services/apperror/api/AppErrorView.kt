/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.apperror.api

import androidx.compose.runtime.Composable
import tech.orbitweb.orbitx.libraries.designsystem.components.dialogs.ErrorDialog
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight

@Composable
fun AppErrorView(
    state: AppErrorState,
) {
    if (state is AppErrorState.Error) {
        AppErrorViewContent(
            title = state.title,
            body = state.body,
            onDismiss = state.dismiss,
        )
    }
}

@Composable
private fun AppErrorViewContent(
    title: String,
    body: String,
    onDismiss: () -> Unit = { },
) {
    ErrorDialog(
        title = title,
        content = body,
        onSubmit = onDismiss,
    )
}

@PreviewsDayNight
@Composable
internal fun AppErrorViewPreview() = ElementPreview {
    AppErrorView(
        state = aAppErrorState()
    )
}
