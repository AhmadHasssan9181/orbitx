/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.appnav.loggedin

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.push.api.PusherRegistrationFailure

open class LoggedInStateProvider : PreviewParameterProvider<LoggedInState> {
    override val values: Sequence<LoggedInState>
        get() = sequenceOf(
            aLoggedInState(),
            aLoggedInState(showSyncSpinner = true),
            aLoggedInState(pusherRegistrationState = AsyncData.Failure(PusherRegistrationFailure.NoDistributorsAvailable())),
            aLoggedInState(forceNativeSlidingSyncMigration = true),
        )
}

fun aLoggedInState(
    showSyncSpinner: Boolean = false,
    pusherRegistrationState: AsyncData<Unit> = AsyncData.Uninitialized,
    forceNativeSlidingSyncMigration: Boolean = false,
    appName: String = "ORBIT Chat",
) = LoggedInState(
    showSyncSpinner = showSyncSpinner,
    pusherRegistrationState = pusherRegistrationState,
    ignoreRegistrationError = false,
    forceNativeSlidingSyncMigration = forceNativeSlidingSyncMigration,
    appName = appName,
    eventSink = {},
)
