/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.widget

import kotlinx.coroutines.flow.Flow

interface MatrixWidgetDriver : AutoCloseable {
    val id: String
    val incomingMessages: Flow<String>

    suspend fun run()
    suspend fun send(message: String)
}
