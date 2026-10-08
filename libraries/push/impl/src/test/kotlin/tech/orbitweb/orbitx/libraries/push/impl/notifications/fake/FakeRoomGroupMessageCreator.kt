/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications.fake

import android.app.Notification
import coil3.ImageLoader
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId
import tech.orbitweb.orbitx.libraries.push.impl.notifications.RoomGroupMessageCreator
import tech.orbitweb.orbitx.libraries.push.impl.notifications.factories.NotificationAccountParams
import tech.orbitweb.orbitx.libraries.push.impl.notifications.fixtures.A_NOTIFICATION
import tech.orbitweb.orbitx.libraries.push.impl.notifications.model.NotifiableMessageEvent
import tech.orbitweb.orbitx.tests.testutils.lambda.LambdaSixParamsRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder

// We just can't make the param types fit
@Suppress("MaxLineLength", "ktlint:standard:max-line-length", "ktlint:standard:parameter-wrapping")
class FakeRoomGroupMessageCreator(
    var createRoomMessageResult: LambdaSixParamsRecorder<
        NotificationAccountParams, List<NotifiableMessageEvent>, RoomId, ThreadId?, ImageLoader, Notification?, Notification
        > = lambdaRecorder { _, _, _, _, _, _ -> A_NOTIFICATION }
) : RoomGroupMessageCreator {
    override suspend fun createRoomMessage(
        notificationAccountParams: NotificationAccountParams,
        events: List<NotifiableMessageEvent>,
        roomId: RoomId,
        threadId: ThreadId?,
        imageLoader: ImageLoader,
        existingNotification: Notification?,
    ): Notification {
        return createRoomMessageResult(notificationAccountParams, events, roomId, threadId, imageLoader, existingNotification)
    }
}
