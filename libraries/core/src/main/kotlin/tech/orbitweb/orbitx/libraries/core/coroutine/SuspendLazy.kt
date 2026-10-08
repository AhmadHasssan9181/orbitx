/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.core.coroutine

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun <T> suspendLazy(coroutineContext: CoroutineContext = EmptyCoroutineContext, block: suspend () -> T): Lazy<Deferred<T>> {
    return lazy(LazyThreadSafetyMode.NONE) {
        val deferred = CompletableDeferred<T>()
        CoroutineScope(coroutineContext).launch {
            deferred.complete(block())
        }
        deferred
    }
}
