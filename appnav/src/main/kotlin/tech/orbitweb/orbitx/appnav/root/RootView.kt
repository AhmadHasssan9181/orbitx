/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.appnav.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import tech.orbitweb.orbitx.features.rageshake.api.crash.CrashDetectionEvent
import tech.orbitweb.orbitx.features.rageshake.api.crash.CrashDetectionView
import tech.orbitweb.orbitx.features.rageshake.api.detection.RageshakeDetectionEvent
import tech.orbitweb.orbitx.features.rageshake.api.detection.RageshakeDetectionView
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text
import tech.orbitweb.orbitx.services.apperror.api.AppErrorView

@Composable
fun RootView(
    state: RootState,
    onOpenBugReport: () -> Unit,
    modifier: Modifier = Modifier,
    children: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        children()

        fun onOpenBugReport() {
            state.crashDetectionState.eventSink(CrashDetectionEvent.ResetAppHasCrashed)
            state.rageshakeDetectionState.eventSink(RageshakeDetectionEvent.Dismiss)
            onOpenBugReport.invoke()
        }

        RageshakeDetectionView(
            state = state.rageshakeDetectionState,
            onOpenBugReport = ::onOpenBugReport,
        )
        CrashDetectionView(
            state = state.crashDetectionState,
            onOpenBugReport = ::onOpenBugReport,
        )
        AppErrorView(
            state = state.errorState,
        )
    }
}

@PreviewsDayNight
@Composable
internal fun RootViewPreview(@PreviewParameter(RootStateProvider::class) rootState: RootState) = ElementPreview {
    RootView(
        state = rootState,
        onOpenBugReport = {},
    ) {
        Text("Children")
    }
}
