/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.test.notifications.sound

import tech.orbitweb.orbitx.libraries.push.api.notifications.sound.NotificationSoundCopier
import tech.orbitweb.orbitx.libraries.push.api.notifications.sound.NotificationSoundCopier.CopyResult
import tech.orbitweb.orbitx.libraries.push.api.notifications.sound.NotificationSoundCopier.SoundSlot
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeNotificationSoundCopier(
    private val copyLambda: (String, SoundSlot) -> CopyResult = { _, _ -> lambdaError() },
    private val deleteStoredSoundForLambda: (SoundSlot) -> Unit = {},
) : NotificationSoundCopier {
    override suspend fun copyToAppFiles(sourceUriString: String, slot: SoundSlot): CopyResult = copyLambda(sourceUriString, slot)

    override suspend fun deleteStoredSoundFor(slot: SoundSlot) {
        deleteStoredSoundForLambda(slot)
    }
}
