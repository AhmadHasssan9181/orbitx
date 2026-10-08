/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.notification

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.notification.NotificationData
import tech.orbitweb.orbitx.libraries.matrix.api.notification.NotificationService

class FakeNotificationService : NotificationService {
    private var getNotificationsResult: Result<Map<EventId, Result<NotificationData>>> = Result.success(emptyMap())

    fun givenGetNotificationsResult(result: Result<Map<EventId, Result<NotificationData>>>) {
        getNotificationsResult = result
    }

    override suspend fun getNotifications(ids: Map<RoomId, List<EventId>>): Result<Map<EventId, Result<NotificationData>>> {
        return getNotificationsResult
    }
}
