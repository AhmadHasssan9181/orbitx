/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalCoroutinesApi::class)

package tech.orbitweb.orbitx.tests.testutils

import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher

/**
 * Create a [CoroutineDispatchers] instance for testing.
 *
 * @param useUnconfinedTestDispatcher If true, use [UnconfinedTestDispatcher] for all dispatchers.
 * If false, use [StandardTestDispatcher] for all dispatchers.
 */
fun TestScope.testCoroutineDispatchers(
    useUnconfinedTestDispatcher: Boolean = false,
): CoroutineDispatchers = when (useUnconfinedTestDispatcher) {
    true -> CoroutineDispatchers(
        io = UnconfinedTestDispatcher(testScheduler),
        computation = UnconfinedTestDispatcher(testScheduler),
        main = UnconfinedTestDispatcher(testScheduler),
    )
    false -> CoroutineDispatchers(
        io = StandardTestDispatcher(testScheduler),
        computation = StandardTestDispatcher(testScheduler),
        main = StandardTestDispatcher(testScheduler),
    )
}
