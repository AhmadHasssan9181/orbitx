/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.sessionstorage.impl

import tech.orbitweb.orbitx.libraries.matrix.session.SessionData
import tech.orbitweb.orbitx.libraries.sessionstorage.api.LoginType

internal fun aDbSessionData(
    userId: String = "userId",
) = SessionData(
    userId = userId,
    deviceId = "deviceId",
    accessToken = "accessToken",
    refreshToken = "refreshToken",
    homeserverUrl = "homeserverUrl",
    loginTimestamp = null,
    oidcData = "aOidcData",
    isTokenValid = 1,
    loginType = LoginType.UNKNOWN.name,
    passphrase = null,
    sessionPath = "sessionPath",
    cachePath = "cachePath",
    position = 0,
    lastUsageIndex = 0,
    userDisplayName = null,
    userAvatarUrl = null,
)
