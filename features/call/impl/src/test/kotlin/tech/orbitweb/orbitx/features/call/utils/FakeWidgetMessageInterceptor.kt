/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import tech.orbitweb.orbitx.features.call.impl.utils.WidgetMessageInterceptor

class FakeWidgetMessageInterceptor : WidgetMessageInterceptor {
    val sentMessages = mutableListOf<String>()

    override val interceptedMessages = MutableSharedFlow<String>(extraBufferCapacity = 1)

    override fun sendMessage(message: String) {
        sentMessages += message
    }

    fun givenInterceptedMessage(message: String) {
        interceptedMessages.tryEmit(message)
    }
}
