/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.tests.testutils

import kotlinx.coroutines.delay

suspend fun waitForPredicate(
    delayBetweenAttemptsMillis: Long = 1,
    maxNumberOfAttempts: Int = 20,
    predicate: () -> Boolean,
) {
    for (i in 0..maxNumberOfAttempts) {
        if (predicate()) return
        if (i < maxNumberOfAttempts) delay(delayBetweenAttemptsMillis)
    }
    throw AssertionError("Predicate was not true after $maxNumberOfAttempts attempts")
}
