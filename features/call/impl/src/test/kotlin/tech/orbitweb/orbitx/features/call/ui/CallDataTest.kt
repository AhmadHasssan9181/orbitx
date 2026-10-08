/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.ui

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.features.call.api.CallData
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID

class CallDataTest {
    @Test
    fun `RoomCall stringification does not contain the URL`() {
        assertThat(CallData(A_SESSION_ID, A_ROOM_ID, false).toString())
            .isEqualTo("CallData(sessionId=$A_SESSION_ID, roomId=$A_ROOM_ID, isAudioCall=false)")
    }
}
