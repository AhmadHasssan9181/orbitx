/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications.shortcut

import androidx.core.content.pm.ShortcutInfoCompat
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

internal fun createShortcutId(sessionId: SessionId, roomId: RoomId) = "$sessionId-$roomId"

internal fun Iterable<ShortcutInfoCompat>.filterBySession(sessionId: SessionId): Iterable<ShortcutInfoCompat> {
    val prefix = "$sessionId-"
    return filter { it.id.startsWith(prefix) }
}
