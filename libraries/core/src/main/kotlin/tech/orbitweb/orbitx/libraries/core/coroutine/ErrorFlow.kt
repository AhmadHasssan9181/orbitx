/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.core.coroutine

import kotlinx.coroutines.flow.flow

/** Create a Flow emitting a single error event. It should be useful for tests. */
fun <T> errorFlow(throwable: Throwable) = flow<T> { throw throwable }
