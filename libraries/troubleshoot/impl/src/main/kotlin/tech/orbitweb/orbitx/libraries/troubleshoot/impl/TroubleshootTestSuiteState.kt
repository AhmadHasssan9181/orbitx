/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.troubleshoot.impl

import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.troubleshoot.api.test.NotificationTroubleshootTestState
import kotlinx.collections.immutable.ImmutableList

data class TroubleshootTestSuiteState(
    val mainState: AsyncAction<Unit>,
    val tests: ImmutableList<NotificationTroubleshootTestState>,
)
