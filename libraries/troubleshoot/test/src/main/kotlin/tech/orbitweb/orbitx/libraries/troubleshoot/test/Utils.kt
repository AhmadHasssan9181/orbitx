/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:Suppress("UnusedImports")

package tech.orbitweb.orbitx.libraries.troubleshoot.test

import app.cash.turbine.TurbineTestContext
import app.cash.turbine.test
import tech.orbitweb.orbitx.libraries.troubleshoot.api.test.NotificationTroubleshootTest
import tech.orbitweb.orbitx.libraries.troubleshoot.api.test.NotificationTroubleshootTestState
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope

context(testScope: TestScope)
suspend fun NotificationTroubleshootTest.runAndTestState(
    validate: suspend TurbineTestContext<NotificationTroubleshootTestState>.() -> Unit,
) {
    testScope.backgroundScope.launch {
        run(this)
    }
    state.test(validate = validate)
}
