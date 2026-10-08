/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.focus

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.window.DialogProperties
import tech.orbitweb.orbitx.features.messages.impl.timeline.FocusRequestState
import tech.orbitweb.orbitx.libraries.designsystem.components.ProgressDialog
import tech.orbitweb.orbitx.libraries.designsystem.components.dialogs.ErrorDialog
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.matrix.api.room.errors.FocusEventException
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@Composable
fun FocusRequestStateView(
    focusRequestState: FocusRequestState,
    onClearFocusRequestState: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (focusRequestState) {
        is FocusRequestState.Failure -> {
            val errorMessage = when (focusRequestState.throwable) {
                is FocusEventException.EventNotFound,
                is FocusEventException.InvalidEventId -> stringResource(id = CommonStrings.error_message_not_found)
                is FocusEventException.Other -> stringResource(id = CommonStrings.error_unknown)
                else -> stringResource(id = CommonStrings.error_unknown)
            }
            ErrorDialog(
                content = errorMessage,
                onSubmit = onClearFocusRequestState,
                modifier = modifier,
            )
        }
        is FocusRequestState.Loading -> {
            ProgressDialog(
                modifier = modifier,
                properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
                onDismissRequest = onClearFocusRequestState,
            )
        }
        else -> Unit
    }
}

@PreviewsDayNight
@Composable
internal fun FocusRequestStateViewPreview(
    @PreviewParameter(FocusRequestStateProvider::class) state: FocusRequestState,
) = ElementPreview {
    FocusRequestStateView(
        focusRequestState = state,
        onClearFocusRequestState = {},
    )
}
