/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.troubleshoot.impl

data class TroubleshootNotificationsState(
    val testSuiteState: TroubleshootTestSuiteState,
    val eventSink: (TroubleshootNotificationsEvents) -> Unit,
) {
    val hasFailedTests: Boolean = testSuiteState.mainState.isFailure()
}
