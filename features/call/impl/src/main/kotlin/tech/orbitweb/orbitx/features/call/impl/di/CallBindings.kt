/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.impl.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import tech.orbitweb.orbitx.features.call.impl.receivers.DeclineCallBroadcastReceiver
import tech.orbitweb.orbitx.features.call.impl.ui.ElementCallActivity
import tech.orbitweb.orbitx.features.call.impl.ui.IncomingCallActivity

@ContributesTo(AppScope::class)
interface CallBindings {
    fun inject(callActivity: ElementCallActivity)
    fun inject(callActivity: IncomingCallActivity)
    fun inject(declineCallBroadcastReceiver: DeclineCallBroadcastReceiver)
}
