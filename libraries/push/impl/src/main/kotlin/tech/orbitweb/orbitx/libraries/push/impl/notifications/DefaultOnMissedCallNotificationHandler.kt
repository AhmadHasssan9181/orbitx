/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClientProvider
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.push.api.notifications.OnMissedCallNotificationHandler

@ContributesBinding(AppScope::class)
class DefaultOnMissedCallNotificationHandler(
    private val matrixClientProvider: MatrixClientProvider,
    private val defaultNotificationDrawerManager: DefaultNotificationDrawerManager,
    private val callNotificationEventResolver: CallNotificationEventResolver,
) : OnMissedCallNotificationHandler {
    override suspend fun addMissedCallNotification(
        sessionId: SessionId,
        roomId: RoomId,
        eventId: EventId,
    ) {
        // Resolve the event and add a notification for it, at this point it should no longer be a ringing one
        val notificationData = matrixClientProvider.getOrRestore(sessionId).getOrNull()
            ?.notificationService
            ?.getNotifications(mapOf(roomId to listOf(eventId)))
            ?.getOrNull()
            ?.get(eventId)
            ?.getOrNull()
            ?: return

        val notifiableEvent = callNotificationEventResolver.resolveEvent(
            sessionId = sessionId,
            notificationData = notificationData,
            // Make sure the notifiable event is not a ringing one
            forceNotify = true,
        ).getOrNull()
        notifiableEvent?.let { defaultNotificationDrawerManager.onNotifiableEventReceived(it) }
    }
}
