/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.impl.utils

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.MutableStateFlow
import tech.orbitweb.orbitx.features.call.api.CurrentCall
import tech.orbitweb.orbitx.features.call.api.CurrentCallService

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultCurrentCallService : CurrentCallService {
    override val currentCall = MutableStateFlow<CurrentCall>(CurrentCall.None)

    fun onCallStarted(call: CurrentCall) {
        currentCall.value = call
    }

    fun onCallEnded() {
        currentCall.value = CurrentCall.None
    }
}
