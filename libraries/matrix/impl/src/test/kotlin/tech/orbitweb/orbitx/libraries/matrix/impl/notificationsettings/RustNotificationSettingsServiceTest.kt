/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.notificationsettings

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.matrix.rustcomponents.sdk.NotificationSettings
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomNotificationMode
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiClient
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiNotificationSettings
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.tests.testutils.testCoroutineDispatchers

class RustNotificationSettingsServiceTest {
    @Test
    fun test() = runTest {
        val sut = createRustNotificationSettingsService()
        val result = sut.getRoomNotificationSettings(
            roomId = A_ROOM_ID,
            isEncrypted = true,
            isOneToOne = true,
        ).getOrNull()!!
        assertThat(result.mode).isEqualTo(RoomNotificationMode.ALL_MESSAGES)
        assertThat(result.isDefault).isTrue()
    }

    private fun TestScope.createRustNotificationSettingsService(
        notificationSettings: NotificationSettings = FakeFfiNotificationSettings(),
    ) = RustNotificationSettingsService(
        client = FakeFfiClient(
            notificationSettings = notificationSettings,
        ),
        sessionCoroutineScope = this,
        dispatchers = testCoroutineDispatchers(),
    )
}
