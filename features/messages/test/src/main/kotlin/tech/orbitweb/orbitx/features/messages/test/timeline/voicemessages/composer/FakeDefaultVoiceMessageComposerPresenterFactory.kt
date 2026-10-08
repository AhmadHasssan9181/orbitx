/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.test.timeline.voicemessages.composer

import kotlinx.coroutines.CoroutineScope
import tech.orbitweb.orbitx.features.messages.impl.voicemessages.composer.DefaultVoiceMessageComposerPresenter
import tech.orbitweb.orbitx.features.messages.impl.voicemessages.composer.VoiceMessageComposerPlayer
import tech.orbitweb.orbitx.features.messages.test.FakeMessageComposerContext
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.libraries.mediaplayer.test.FakeAudioFocus
import tech.orbitweb.orbitx.libraries.mediaplayer.test.FakeMediaPlayer
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaSender
import tech.orbitweb.orbitx.libraries.mediaupload.test.FakeMediaSender
import tech.orbitweb.orbitx.libraries.permissions.test.FakePermissionsPresenterFactory
import tech.orbitweb.orbitx.libraries.voicerecorder.test.FakeVoiceRecorder
import tech.orbitweb.orbitx.services.analytics.test.FakeAnalyticsService

class FakeDefaultVoiceMessageComposerPresenterFactory(
    private val sessionCoroutineScope: CoroutineScope,
    private val mediaSender: MediaSender = FakeMediaSender(),
) : DefaultVoiceMessageComposerPresenter.Factory {
    override fun create(timelineMode: Timeline.Mode): DefaultVoiceMessageComposerPresenter {
        return DefaultVoiceMessageComposerPresenter(
            sessionCoroutineScope = sessionCoroutineScope,
            timelineMode = timelineMode,
            voiceRecorder = FakeVoiceRecorder(),
            analyticsService = FakeAnalyticsService(),
            audioFocus = FakeAudioFocus(
                requestAudioFocusResult = { _, _ -> },
                releaseAudioFocusResult = { },
            ),
            mediaSenderFactory = { mediaSender },
            player = VoiceMessageComposerPlayer(
                mediaPlayer = FakeMediaPlayer(),
                sessionCoroutineScope = sessionCoroutineScope,
            ),
            messageComposerContext = FakeMessageComposerContext(),
            permissionsPresenterFactory = FakePermissionsPresenterFactory(),
        )
    }
}
