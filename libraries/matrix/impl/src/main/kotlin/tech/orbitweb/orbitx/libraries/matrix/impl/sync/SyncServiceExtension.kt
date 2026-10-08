/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.sync

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import org.matrix.rustcomponents.sdk.SyncServiceInterface
import org.matrix.rustcomponents.sdk.SyncServiceState
import org.matrix.rustcomponents.sdk.SyncServiceStateObserver
import tech.orbitweb.orbitx.libraries.matrix.impl.util.mxCallbackFlow

fun SyncServiceInterface.stateFlow(): Flow<SyncServiceState> =
    mxCallbackFlow {
        val listener = object : SyncServiceStateObserver {
            override fun onUpdate(state: SyncServiceState) {
                trySendBlocking(state)
            }
        }
        state(listener)
    }.buffer(Channel.UNLIMITED)
