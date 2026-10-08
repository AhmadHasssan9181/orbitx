/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.classic

import android.graphics.Bitmap
import tech.orbitweb.orbitx.libraries.matrix.api.auth.ElementClassicSession
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID

internal const val ROOM_KEYS_VERSION = "roomKeysVersion as Json data"

fun anElementClassicReady(
    elementClassicSession: ElementClassicSession = anElementClassicSession(),
    displayName: String? = null,
    avatar: Bitmap? = null,
) = ElementClassicConnectionState.ElementClassicReady(
    elementClassicSession = elementClassicSession,
    displayName = displayName,
    avatar = avatar,
)

fun anElementClassicSession(
    userId: UserId = A_USER_ID,
    homeserverUrl: String? = null,
    secrets: String? = null,
    roomKeysVersion: String? = null,
    doesContainBackupKey: Boolean = false,
) = ElementClassicSession(
    userId = userId,
    homeserverUrl = homeserverUrl,
    secrets = secrets,
    roomKeysVersion = roomKeysVersion,
    doesContainBackupKey = doesContainBackupKey,
)
