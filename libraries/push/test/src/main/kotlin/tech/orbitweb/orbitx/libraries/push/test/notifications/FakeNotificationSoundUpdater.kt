/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.test.notifications

import tech.orbitweb.orbitx.libraries.preferences.api.store.NotificationSound
import tech.orbitweb.orbitx.libraries.push.api.notifications.NotificationSoundUpdater
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeNotificationSoundUpdater(
    private val recreateNoisyChannelLambda: (sound: NotificationSound, version: Int) -> Unit = { _, _ -> lambdaError() },
    private val recreateRingingCallChannelLambda: (sound: NotificationSound, version: Int) -> Unit = { _, _ -> lambdaError() },
    private val readNoisyChannelSoundLambda: () -> NotificationSound? = { null },
    private val readRingingCallChannelSoundLambda: () -> NotificationSound? = { null },
) : NotificationSoundUpdater {
    override fun recreateNoisyChannel(sound: NotificationSound, version: Int) {
        recreateNoisyChannelLambda(sound, version)
    }

    override fun recreateRingingCallChannel(sound: NotificationSound, version: Int) {
        recreateRingingCallChannelLambda(sound, version)
    }

    override suspend fun readNoisyChannelSound(): NotificationSound? = readNoisyChannelSoundLambda()

    override suspend fun readRingingCallChannelSound(): NotificationSound? = readRingingCallChannelSoundLambda()
}
