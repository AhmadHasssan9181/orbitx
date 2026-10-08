/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.troubleshoot.test

import tech.orbitweb.orbitx.libraries.troubleshoot.api.test.NotificationTroubleshootNavigator
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeNotificationTroubleshootNavigator(
    private val openIgnoredUsersResult: () -> Unit = { lambdaError() },
) : NotificationTroubleshootNavigator {
    override fun navigateToBlockedUsers() = openIgnoredUsersResult()
}
