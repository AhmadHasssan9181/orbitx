/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.eventformatter.impl

import android.content.Context
import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.libraries.matrix.api.notification.CallIntent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.CallNotifyContent
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID_2
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID_3
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.services.toolbox.impl.strings.AndroidStringProvider
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Before
import org.junit.Test
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Suppress("LargeClass")
class RtcNotificationContentFormatterTest : RobolectricTest() {
    private lateinit var context: Context
    private lateinit var fakeMatrixClient: FakeMatrixClient
    private lateinit var formatter: RtcNotificationContentFormatter

    @Before
    fun setup() {
        context = RuntimeEnvironment.getApplication() as Context
        fakeMatrixClient = FakeMatrixClient()
        val stringProvider = AndroidStringProvider(context.resources)
        formatter = RtcNotificationContentFormatter(
            fakeMatrixClient,
            stringProvider
        )
    }

    @Test
    @Config(qualifiers = "en")
    fun `Should not display declined info in rooms`() {
        val result = formatter.format(
            CallNotifyContent(
                CallIntent.VIDEO,
                declinedBy = listOf(A_USER_ID_2, A_USER_ID_3)
            ),
            false
        )
        val expected = "Call started"
        assertThat(result.toString()).isEqualTo(expected)
    }

    @Test
    @Config(qualifiers = "en")
    fun `Declined by me variant`() {
        val result = formatter.format(
            CallNotifyContent(
                CallIntent.VIDEO,
                declinedBy = listOf(fakeMatrixClient.sessionId)
            ),
            true
        )
        val expected = "You declined a call"
        assertThat(result.toString()).isEqualTo(expected)
    }

    @Test
    @Config(qualifiers = "en")
    fun `Declined by other variant`() {
        val result = formatter.format(
            CallNotifyContent(
                CallIntent.VIDEO,
                declinedBy = listOf(A_USER_ID_2)
            ),
            true
        )
        val expected = "Call declined"
        assertThat(result.toString()).isEqualTo(expected)
    }

    @Test
    @Config(qualifiers = "en")
    fun `Call started in DM`() {
        val result = formatter.format(
            CallNotifyContent(
                CallIntent.AUDIO,
                declinedBy = listOf()
            ),
            true
        )
        val expected = "Call started"
        assertThat(result.toString()).isEqualTo(expected)
    }
}
