/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.impl.notifications

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId

@Parcelize
data class CallNotificationData(
    val sessionId: SessionId,
    val roomId: RoomId,
    val eventId: EventId,
    val senderId: UserId,
    val roomName: String?,
    val senderName: String?,
    val avatarUrl: String?,
    val notificationChannelId: String,
    val timestamp: Long,
    val textContent: String?,
    // Expiration timestamp in millis since epoch
    val expirationTimestamp: Long,
    val audioOnly: Boolean,
) : Parcelable
