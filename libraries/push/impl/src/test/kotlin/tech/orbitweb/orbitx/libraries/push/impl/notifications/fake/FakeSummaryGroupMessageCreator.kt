/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications.fake

import android.app.Notification
import tech.orbitweb.orbitx.libraries.push.impl.notifications.OneShotNotification
import tech.orbitweb.orbitx.libraries.push.impl.notifications.RoomNotification
import tech.orbitweb.orbitx.libraries.push.impl.notifications.SummaryGroupMessageCreator
import tech.orbitweb.orbitx.libraries.push.impl.notifications.factories.NotificationAccountParams
import tech.orbitweb.orbitx.libraries.push.impl.notifications.fixtures.A_NOTIFICATION
import tech.orbitweb.orbitx.tests.testutils.lambda.LambdaFourParamsRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder

class FakeSummaryGroupMessageCreator(
    var createSummaryNotificationResult: LambdaFourParamsRecorder<
        NotificationAccountParams, List<RoomNotification>, List<OneShotNotification>, List<OneShotNotification>, Notification> =
        lambdaRecorder { _, _, _, _ -> A_NOTIFICATION }
) : SummaryGroupMessageCreator {
    override fun createSummaryNotification(
        notificationAccountParams: NotificationAccountParams,
        roomNotifications: List<RoomNotification>,
        invitationNotifications: List<OneShotNotification>,
        simpleNotifications: List<OneShotNotification>,
    ): Notification {
        return createSummaryNotificationResult(
            notificationAccountParams,
            roomNotifications,
            invitationNotifications,
            simpleNotifications,
        )
    }
}
