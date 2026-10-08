/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaplayer.test

import tech.orbitweb.orbitx.libraries.audio.api.AudioFocus
import tech.orbitweb.orbitx.libraries.audio.api.AudioFocusRequester
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeAudioFocus(
    private val requestAudioFocusResult: (AudioFocusRequester, () -> Unit) -> Unit = { _, _ -> lambdaError() },
    private val releaseAudioFocusResult: () -> Unit = { lambdaError() },
) : AudioFocus {
    override fun requestAudioFocus(
        requester: AudioFocusRequester,
        onFocusLost: () -> Unit,
    ) {
        requestAudioFocusResult(requester, onFocusLost)
    }

    override fun releaseAudioFocus() {
        releaseAudioFocusResult()
    }
}
