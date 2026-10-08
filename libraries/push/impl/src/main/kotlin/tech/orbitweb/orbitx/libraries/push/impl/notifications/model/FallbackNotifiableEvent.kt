/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications.model

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

/**
 * Used for notifications with events that couldn't be retrieved or decrypted, so we don't know their contents.
 * These are created separately from message notifications, so they can be displayed differently.
 */
data class FallbackNotifiableEvent(
    override val sessionId: SessionId,
    override val roomId: RoomId,
    override val eventId: EventId,
    override val editedEventId: EventId?,
    override val description: String?,
    override val canBeReplaced: Boolean,
    override val isRedacted: Boolean,
    override val isUpdated: Boolean,
    val timestamp: Long,
    val cause: String?,
) : NotifiableEvent
