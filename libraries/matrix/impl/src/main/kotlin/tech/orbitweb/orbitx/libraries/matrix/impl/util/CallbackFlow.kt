/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.util

import tech.orbitweb.orbitx.libraries.core.data.tryOrNull
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import org.matrix.rustcomponents.sdk.TaskHandle

internal fun <T> mxCallbackFlow(block: suspend ProducerScope<T>.() -> TaskHandle) =
    callbackFlow {
        val taskHandle: TaskHandle? = tryOrNull {
            block(this)
        }
        awaitClose {
            taskHandle?.cancelAndDestroy()
        }
    }
