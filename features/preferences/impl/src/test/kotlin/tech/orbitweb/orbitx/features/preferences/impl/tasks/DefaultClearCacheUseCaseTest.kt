/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.tasks

import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import org.junit.Test
import tech.orbitweb.orbitx.features.invite.test.InMemorySeenInvitesStore
import tech.orbitweb.orbitx.features.preferences.impl.DefaultCacheService
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeJoinedRoom
import tech.orbitweb.orbitx.libraries.push.test.FakePushService
import tech.orbitweb.orbitx.libraries.sessionstorage.test.InMemoryCacheStore
import tech.orbitweb.orbitx.libraries.sessionstorage.test.aCacheData
import tech.orbitweb.orbitx.services.appnavstate.impl.DefaultActiveRoomsHolder
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.value
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import tech.orbitweb.orbitx.tests.testutils.testCoroutineDispatchers

class DefaultClearCacheUseCaseTest : RobolectricTest() {
    @Test
    fun `execute clear cache should do all the expected tasks`() = runTest {
        val activeRoomsHolder = DefaultActiveRoomsHolder().apply { addRoom(FakeJoinedRoom()) }
        val clearCacheLambda = lambdaRecorder<Unit> { }
        val matrixClient = FakeMatrixClient(
            sessionId = A_SESSION_ID,
            clearCacheLambda = clearCacheLambda,
        )
        val defaultCacheService = DefaultCacheService()
        val setIgnoreRegistrationErrorLambda = lambdaRecorder<SessionId, Boolean, Unit> { _, _ -> }
        val resetBatteryOptimizationStateResult = lambdaRecorder<Unit> { }
        val pushService = FakePushService(
            setIgnoreRegistrationErrorLambda = setIgnoreRegistrationErrorLambda,
            resetBatteryOptimizationStateResult = resetBatteryOptimizationStateResult,
        )
        val seenInvitesStore = InMemorySeenInvitesStore(setOf(A_ROOM_ID))
        assertThat(seenInvitesStore.seenRoomIds().first()).isNotEmpty()
        val cacheStore = InMemoryCacheStore(
            initialData = mapOf("key1" to aCacheData())
        )
        val sut = DefaultClearCacheUseCase(
            context = InstrumentationRegistry.getInstrumentation().context,
            matrixClient = matrixClient,
            coroutineDispatchers = testCoroutineDispatchers(),
            defaultCacheService = defaultCacheService,
            okHttpClient = { OkHttpClient.Builder().build() },
            pushService = pushService,
            seenInvitesStore = seenInvitesStore,
            activeRoomsHolder = activeRoomsHolder,
            cacheStore = cacheStore,
        )
        defaultCacheService.clearedCacheEventFlow.test {
            sut.invoke()
            assertThat(cacheStore.dataMap).isEmpty()
            clearCacheLambda.assertions().isCalledOnce()
            setIgnoreRegistrationErrorLambda.assertions().isCalledOnce()
                .with(value(matrixClient.sessionId), value(false))
            resetBatteryOptimizationStateResult.assertions().isCalledOnce()
            assertThat(awaitItem()).isEqualTo(matrixClient.sessionId)
            assertThat(seenInvitesStore.seenRoomIds().first()).isEmpty()
            assertThat(activeRoomsHolder.getActiveRoom(A_SESSION_ID)).isNull()
        }
    }
}
