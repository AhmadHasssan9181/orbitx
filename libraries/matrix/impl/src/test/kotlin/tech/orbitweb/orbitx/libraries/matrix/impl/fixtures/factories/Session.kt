/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.factories

import tech.orbitweb.orbitx.libraries.matrix.test.A_DEVICE_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_HOMESERVER_URL
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import org.matrix.rustcomponents.sdk.Session
import org.matrix.rustcomponents.sdk.SlidingSyncVersion

internal fun aRustSession(
    proxy: SlidingSyncVersion = SlidingSyncVersion.NONE,
    accessToken: String = "accessToken",
    refreshToken: String = "refreshToken",
) = Session(
    accessToken = accessToken,
    refreshToken = refreshToken,
    userId = A_USER_ID.value,
    deviceId = A_DEVICE_ID.value,
    homeserverUrl = A_HOMESERVER_URL,
    oauthData = null,
    slidingSyncVersion = proxy,
)
