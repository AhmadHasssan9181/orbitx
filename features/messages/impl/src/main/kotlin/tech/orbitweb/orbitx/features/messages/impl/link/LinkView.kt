/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.link

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.core.extensions.ensureEndsLeftToRight
import tech.orbitweb.orbitx.libraries.core.extensions.filterDirectionOverrides
import tech.orbitweb.orbitx.libraries.designsystem.components.dialogs.ConfirmationDialog
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import io.element.android.wysiwyg.link.Link

@Composable
fun LinkView(
    state: LinkState,
    onLinkValid: (Link) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state.linkClick) {
        AsyncAction.Uninitialized,
        AsyncAction.Loading,
        is AsyncAction.Failure -> Unit
        is AsyncAction.Confirming -> {
            if (state.linkClick is ConfirmingLinkClick) {
                ConfirmationDialog(
                    modifier = modifier,
                    title = stringResource(CommonStrings.dialog_confirm_link_title),
                    content = stringResource(
                        CommonStrings.dialog_confirm_link_message,
                        state.linkClick.link.text.ensureEndsLeftToRight(),
                        state.linkClick.link.url.filterDirectionOverrides(),
                    ),
                    submitText = stringResource(CommonStrings.action_continue),
                    onSubmitClick = {
                        state.eventSink(LinkEvent.Confirm)
                    },
                    onDismiss = {
                        state.eventSink(LinkEvent.Cancel)
                    },
                )
            }
        }
        is AsyncAction.Success -> {
            val latestOnLinkValid by rememberUpdatedState(onLinkValid)
            LaunchedEffect(state.linkClick.data) {
                latestOnLinkValid(state.linkClick.data)
                state.eventSink(LinkEvent.Cancel)
            }
        }
    }
}

@PreviewsDayNight
@Composable
internal fun LinkViewPreview(@PreviewParameter(LinkStateProvider::class) state: LinkState) = ElementPreview {
    LinkView(
        state = state,
        onLinkValid = {},
    )
}
