/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl

import kotlinx.coroutines.flow.MutableSharedFlow
import tech.orbitweb.orbitx.libraries.androidutils.system.DateTimeObserver

class FakeDateTimeObserver : DateTimeObserver {
    override val changes = MutableSharedFlow<DateTimeObserver.Event>(extraBufferCapacity = 1)

    fun given(event: DateTimeObserver.Event) {
        changes.tryEmit(event)
    }
}
