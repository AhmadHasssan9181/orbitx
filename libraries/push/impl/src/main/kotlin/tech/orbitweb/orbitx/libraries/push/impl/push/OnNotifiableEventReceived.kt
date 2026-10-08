/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.push

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.di.annotations.AppCoroutineScope
import tech.orbitweb.orbitx.libraries.push.impl.notifications.DefaultNotificationDrawerManager
import tech.orbitweb.orbitx.libraries.push.impl.notifications.model.NotifiableEvent
import tech.orbitweb.orbitx.libraries.push.impl.notifications.model.NotifiableRingingCallEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface OnNotifiableEventReceived {
    fun onNotifiableEventsReceived(notifiableEvents: List<NotifiableEvent>)
}

@ContributesBinding(AppScope::class)
class DefaultOnNotifiableEventReceived(
    private val defaultNotificationDrawerManager: DefaultNotificationDrawerManager,
    @AppCoroutineScope
    private val coroutineScope: CoroutineScope,
) : OnNotifiableEventReceived {
    override fun onNotifiableEventsReceived(notifiableEvents: List<NotifiableEvent>) {
        coroutineScope.launch {
            defaultNotificationDrawerManager.onNotifiableEventsReceived(notifiableEvents.filter { it !is NotifiableRingingCallEvent })
        }
    }
}
