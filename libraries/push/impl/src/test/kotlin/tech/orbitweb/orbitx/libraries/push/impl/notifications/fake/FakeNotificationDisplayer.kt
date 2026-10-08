/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications.fake

import android.app.Notification
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.push.api.notifications.NotificationIdProvider
import tech.orbitweb.orbitx.libraries.push.impl.notifications.NotificationDisplayer
import tech.orbitweb.orbitx.tests.testutils.lambda.LambdaNoParamRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.LambdaOneParamRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.LambdaThreeParamsRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.LambdaTwoParamsRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.value

class FakeNotificationDisplayer(
    var showNotificationResult: LambdaThreeParamsRecorder<String?, Int, Notification, Boolean> = lambdaRecorder { _, _, _ -> true },
    var cancelNotificationResult: LambdaTwoParamsRecorder<String?, Int, Unit> = lambdaRecorder { _, _ -> },
    var displayDiagnosticNotificationResult: LambdaOneParamRecorder<Notification, Boolean> = lambdaRecorder { _ -> true },
    var dismissDiagnosticNotificationResult: LambdaNoParamRecorder<Unit> = lambdaRecorder { -> },
    var displayUnregistrationNotificationResult: LambdaOneParamRecorder<Notification, Boolean> = lambdaRecorder { _ -> true },
) : NotificationDisplayer {
    override fun showNotification(tag: String?, id: Int, notification: Notification): Boolean {
        return showNotificationResult(tag, id, notification)
    }

    override fun cancelNotification(tag: String?, id: Int) {
        return cancelNotificationResult(tag, id)
    }

    override fun displayDiagnosticNotification(notification: Notification): Boolean {
        return displayDiagnosticNotificationResult(notification)
    }

    override fun dismissDiagnosticNotification() {
        return dismissDiagnosticNotificationResult()
    }

    override fun displayUnregistrationNotification(notification: Notification): Boolean {
        return displayUnregistrationNotificationResult(notification)
    }

    fun verifySummaryCancelled(times: Int = 1) {
        cancelNotificationResult.assertions().isCalledExactly(times).withSequence(
            listOf(value(null), value(NotificationIdProvider.getSummaryNotificationId(A_SESSION_ID)))
        )
    }
}
