/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.test

import kotlinx.coroutines.flow.MutableStateFlow
import tech.orbitweb.orbitx.features.call.api.CurrentCall
import tech.orbitweb.orbitx.features.call.api.CurrentCallService

class FakeCurrentCallService(
    override val currentCall: MutableStateFlow<CurrentCall> = MutableStateFlow(CurrentCall.None),
) : CurrentCallService
