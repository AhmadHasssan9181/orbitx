/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.logout.impl.direct

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.logout.api.direct.DirectLogoutEvents
import tech.orbitweb.orbitx.features.logout.api.direct.DirectLogoutState
import tech.orbitweb.orbitx.features.logout.api.direct.DirectLogoutStateProvider
import tech.orbitweb.orbitx.features.logout.api.direct.DirectLogoutView
import tech.orbitweb.orbitx.features.logout.impl.ui.LogoutActionDialog
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.di.SessionScope

@ContributesBinding(SessionScope::class)
class DefaultDirectLogoutView : DirectLogoutView {
    @Composable
    override fun Render(state: DirectLogoutState) {
        val eventSink = state.eventSink
        LogoutActionDialog(
            state.logoutAction,
            onConfirmClick = {
                eventSink(DirectLogoutEvents.Logout(ignoreSdkError = false))
            },
            onForceLogoutClick = {
                eventSink(DirectLogoutEvents.Logout(ignoreSdkError = true))
            },
            onDismissDialog = {
                eventSink(DirectLogoutEvents.CloseDialogs)
            },
        )
    }
}

@PreviewsDayNight
@Composable
internal fun DefaultDirectLogoutViewPreview(
    @PreviewParameter(DirectLogoutStateProvider::class) state: DirectLogoutState,
) = ElementPreview {
    DefaultDirectLogoutView().Render(state = state)
}
