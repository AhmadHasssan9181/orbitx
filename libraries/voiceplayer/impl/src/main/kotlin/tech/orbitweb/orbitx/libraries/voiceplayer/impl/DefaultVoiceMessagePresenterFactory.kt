/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.voiceplayer.impl

import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.CoroutineScope
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.di.RoomScope
import tech.orbitweb.orbitx.libraries.di.annotations.SessionCoroutineScope
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaSource
import tech.orbitweb.orbitx.libraries.voiceplayer.api.VoiceMessagePresenterFactory
import tech.orbitweb.orbitx.libraries.voiceplayer.api.VoiceMessageState
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsService
import kotlin.time.Duration

@ContributesBinding(RoomScope::class)
class DefaultVoiceMessagePresenterFactory(
    private val analyticsService: AnalyticsService,
    @SessionCoroutineScope
    private val sessionCoroutineScope: CoroutineScope,
    private val voiceMessagePlayerFactory: VoiceMessagePlayer.Factory,
    private val voicePlayerStore: VoicePlayerStore,
) : VoiceMessagePresenterFactory {
    override fun createVoiceMessagePresenter(
        eventId: EventId?,
        mediaSource: MediaSource,
        mimeType: String?,
        filename: String?,
        duration: Duration,
    ): Presenter<VoiceMessageState> {
        val player = voiceMessagePlayerFactory.create(
            eventId = eventId,
            mediaSource = mediaSource,
            mimeType = mimeType,
            filename = filename,
        )

        return VoiceMessagePresenter(
            analyticsService = analyticsService,
            sessionCoroutineScope = sessionCoroutineScope,
            voicePlayerStore = voicePlayerStore,
            player = player,
            eventId = eventId,
            duration = duration,
        )
    }
}
