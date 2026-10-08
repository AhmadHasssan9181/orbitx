/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.notifications

import androidx.core.graphics.drawable.IconCompat
import androidx.test.platform.app.InstrumentationRegistry
import coil3.ImageLoader
import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.features.call.impl.notifications.RingingCallNotificationCreator
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID_2
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClientProvider
import tech.orbitweb.orbitx.libraries.matrix.ui.media.test.FakeImageLoaderHolder
import tech.orbitweb.orbitx.libraries.push.test.notifications.push.FakeNotificationBitmapLoader
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RingingCallNotificationCreatorTest : RobolectricTest() {
    @Test
    fun `createNotification - with no associated MatrixClient does nothing`() = runTest {
        val notificationCreator = createRingingCallNotificationCreator(
            matrixClientProvider = FakeMatrixClientProvider(getClient = { Result.failure(IllegalStateException("No client found")) })
        )

        val result = notificationCreator.createTestNotification()

        assertThat(result).isNull()
    }

    @Test
    fun `createNotification - creates a valid notification`() = runTest {
        val notificationCreator = createRingingCallNotificationCreator(
            matrixClientProvider = FakeMatrixClientProvider(getClient = { Result.success(FakeMatrixClient()) })
        )

        val result = notificationCreator.createTestNotification()

        assertThat(result).isNotNull()
    }

    @Test
    fun `createNotification - tries to load the avatar URL`() = runTest {
        val getUserIconLambda = lambdaRecorder<AvatarData, ImageLoader, IconCompat?> { _, _ -> null }
        val notificationCreator = createRingingCallNotificationCreator(
            matrixClientProvider = FakeMatrixClientProvider(getClient = { Result.success(FakeMatrixClient()) }),
            notificationBitmapLoader = FakeNotificationBitmapLoader(getUserIconResult = getUserIconLambda)
        )

        notificationCreator.createTestNotification()

        getUserIconLambda.assertions().isCalledOnce()
    }

    @Test
    fun `createNotification - use the correct style for video call`() = runTest {
        val notificationCreator = createRingingCallNotificationCreator(
            matrixClientProvider = FakeMatrixClientProvider(getClient = { Result.success(FakeMatrixClient()) }),
        )

        val notification = notificationCreator.createTestNotification()
        assertThat(notification?.category).isEqualTo("call")

        val acceptAction = notification?.actions?.get(1)
        assertThat(acceptAction?.title?.toString()).isEqualTo("Video")
    }

    @Test
    fun `createNotification - use the correct style for audio call`() = runTest {
        val notificationCreator = createRingingCallNotificationCreator(
            matrixClientProvider = FakeMatrixClientProvider(getClient = { Result.success(FakeMatrixClient()) }),
        )

        val notification = notificationCreator.createTestNotification(audioOnly = true)
        assertThat(notification?.category).isEqualTo("call")

        val acceptAction = notification?.actions?.get(1)
        assertThat(acceptAction?.title?.toString()).isEqualTo("Answer")
    }

    private suspend fun RingingCallNotificationCreator.createTestNotification(audioOnly: Boolean = false) = createNotification(
        sessionId = A_SESSION_ID,
        roomId = A_ROOM_ID,
        eventId = AN_EVENT_ID,
        senderId = A_USER_ID_2,
        roomName = "Room",
        senderDisplayName = "Johnnie Murphy",
        roomAvatarUrl = "https://example.com/avatar.jpg",
        notificationChannelId = "channelId",
        timestamp = 0L,
        expirationTimestamp = 20L,
        textContent = "textContent",
        audioOnly = audioOnly
    )

    private fun createRingingCallNotificationCreator(
        matrixClientProvider: FakeMatrixClientProvider = FakeMatrixClientProvider(),
        imageLoaderHolder: FakeImageLoaderHolder = FakeImageLoaderHolder(),
        notificationBitmapLoader: FakeNotificationBitmapLoader = FakeNotificationBitmapLoader(),
    ) = RingingCallNotificationCreator(
        context = InstrumentationRegistry.getInstrumentation().targetContext,
        matrixClientProvider = matrixClientProvider,
        imageLoaderHolder = imageLoaderHolder,
        notificationBitmapLoader = notificationBitmapLoader,
    )
}
