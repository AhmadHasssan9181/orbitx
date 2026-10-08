/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.test

import tech.orbitweb.orbitx.features.call.api.CurrentCall
import tech.orbitweb.orbitx.features.call.api.CurrentCallService
import kotlinx.coroutines.flow.MutableStateFlow

class FakeCurrentCallService(
    override val currentCall: MutableStateFlow<CurrentCall> = MutableStateFlow(CurrentCall.None),
) : CurrentCallService
