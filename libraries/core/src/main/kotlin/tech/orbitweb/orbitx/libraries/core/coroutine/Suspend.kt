/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.core.coroutine

import kotlinx.coroutines.delay
import kotlin.system.measureTimeMillis

fun suspendWithMinimumDuration(
    minimumDurationMillis: Long = 500,
    block: suspend () -> Unit
) = suspend {
    val duration = measureTimeMillis {
        block()
    }
    delay(minimumDurationMillis - duration)
}
