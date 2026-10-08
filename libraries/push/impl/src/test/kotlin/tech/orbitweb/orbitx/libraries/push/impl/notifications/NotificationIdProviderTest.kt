/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID_2
import tech.orbitweb.orbitx.libraries.push.api.notifications.NotificationIdProvider

class NotificationIdProviderTest {
    @Test
    fun `test notification id provider`() {
        val sut = NotificationIdProvider
        val offsetForASessionId = 305_410
        assertThat(sut.getSummaryNotificationId(A_SESSION_ID)).isEqualTo(offsetForASessionId + 0)
        assertThat(sut.getRoomMessagesNotificationId(A_SESSION_ID)).isEqualTo(offsetForASessionId + 1)
        assertThat(sut.getRoomEventNotificationId(A_SESSION_ID)).isEqualTo(offsetForASessionId + 2)
        assertThat(sut.getRoomInvitationNotificationId(A_SESSION_ID)).isEqualTo(offsetForASessionId + 3)
        // Check that value will be different for another sessionId
        assertThat(sut.getSummaryNotificationId(A_SESSION_ID)).isNotEqualTo(sut.getSummaryNotificationId(A_SESSION_ID_2))
    }
}
