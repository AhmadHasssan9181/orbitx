/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications.channels

import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.preferences.api.store.NotificationSound
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeNotificationChannels(
    var channelForIncomingCall: (ring: Boolean) -> String = { _ -> "" },
    var channelIdForMessage: (sessionId: SessionId, noisy: Boolean) -> String = { _, _ -> "" },
    var channelIdForTest: () -> String = { "" },
    // Side-effecting recreates default to lambdaError (matching FakeNotificationSoundUpdater) so a
    // test that doesn't expect a channel rebuild loudly fails instead of silently swallowing the call.
    var recreateNoisyChannelLambda: (sound: NotificationSound, version: Int) -> Unit = { _, _ -> lambdaError() },
    var recreateRingingCallChannelLambda: (sound: NotificationSound, version: Int) -> Unit = { _, _ -> lambdaError() },
    var readNoisyChannelSoundLambda: () -> NotificationSound? = { null },
    var readRingingCallChannelSoundLambda: () -> NotificationSound? = { null },
) : NotificationChannels {
    override fun getChannelForIncomingCall(ring: Boolean): String {
        return channelForIncomingCall(ring)
    }

    override fun getChannelIdForMessage(sessionId: SessionId, noisy: Boolean): String {
        return channelIdForMessage(sessionId, noisy)
    }

    override fun getChannelIdForTest(): String {
        return channelIdForTest()
    }

    override fun recreateNoisyChannel(sound: NotificationSound, version: Int) {
        recreateNoisyChannelLambda(sound, version)
    }

    override fun recreateRingingCallChannel(sound: NotificationSound, version: Int) {
        recreateRingingCallChannelLambda(sound, version)
    }

    override suspend fun readNoisyChannelSound(): NotificationSound? = readNoisyChannelSoundLambda()

    override suspend fun readRingingCallChannelSound(): NotificationSound? = readRingingCallChannelSoundLambda()
}
