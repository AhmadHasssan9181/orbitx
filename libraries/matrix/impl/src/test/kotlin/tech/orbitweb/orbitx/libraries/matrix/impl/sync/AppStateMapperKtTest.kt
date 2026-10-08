/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.sync

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.matrix.rustcomponents.sdk.SyncServiceState
import tech.orbitweb.orbitx.libraries.matrix.api.sync.SyncState

class AppStateMapperKtTest {
    @Test
    fun toSyncState() {
        assertThat(SyncServiceState.IDLE.toSyncState()).isEqualTo(SyncState.Idle)
        assertThat(SyncServiceState.RUNNING.toSyncState()).isEqualTo(SyncState.Running)
        assertThat(SyncServiceState.TERMINATED.toSyncState()).isEqualTo(SyncState.Terminated)
        assertThat(SyncServiceState.ERROR.toSyncState()).isEqualTo(SyncState.Error)
        assertThat(SyncServiceState.OFFLINE.toSyncState()).isEqualTo(SyncState.Offline)
    }
}
