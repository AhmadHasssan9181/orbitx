/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.signedout.impl

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.sessionstorage.api.LoginType
import tech.orbitweb.orbitx.libraries.sessionstorage.api.SessionData

open class SignedOutStateProvider : PreviewParameterProvider<SignedOutState> {
    override val values: Sequence<SignedOutState>
        get() = sequenceOf(
            aSignedOutState(),
            // Add other states here
        )
}

private fun aSignedOutState() = SignedOutState(
    appName = "AppName",
    signedOutSession = aSessionData(),
    eventSink = {},
)

private fun aSessionData(
    sessionId: String = "@alice:server.org",
    isTokenValid: Boolean = false,
): SessionData {
    return SessionData(
        userId = sessionId,
        deviceId = "aDeviceId",
        accessToken = "anAccessToken",
        refreshToken = "aRefreshToken",
        homeserverUrl = "aHomeserverUrl",
        oAuthData = null,
        loginTimestamp = null,
        isTokenValid = isTokenValid,
        loginType = LoginType.UNKNOWN,
        passphrase = null,
        sessionPath = "/a/path/to/a/session",
        cachePath = "/a/path/to/a/cache",
        position = 0,
        lastUsageIndex = 0,
        userDisplayName = null,
        userAvatarUrl = null,
    )
}
