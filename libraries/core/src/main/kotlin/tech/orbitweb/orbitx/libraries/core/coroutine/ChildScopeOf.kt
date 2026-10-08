/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.core.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.job
import kotlinx.coroutines.plus
import kotlinx.coroutines.test.TestScope

/**
 * Create a child scope of the current scope.
 * The child scope will be cancelled if the parent scope is cancelled.
 * The child scope will be cancelled if an exception is thrown in the parent scope.
 * The parent scope won't be cancelled when an exception is thrown in the child scope.
 *
 * @param dispatcher the dispatcher to use for this scope.
 * @param name the name of the coroutine.
 */
fun CoroutineScope.childScope(
    dispatcher: CoroutineDispatcher,
    name: String,
): CoroutineScope = run {
    if (this is TestScope) {
        // Special case for tests: we can't start a coroutine with a different SupervisorJob
        this
    } else {
        val supervisorJob = SupervisorJob(parent = coroutineContext.job)
        this + dispatcher + supervisorJob + CoroutineName(name)
    }
}
