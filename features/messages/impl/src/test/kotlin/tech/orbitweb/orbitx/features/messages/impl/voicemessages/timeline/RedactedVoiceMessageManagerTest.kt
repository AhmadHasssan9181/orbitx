/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.voicemessages.timeline

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UniqueId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.MatrixTimelineItem
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.TimelineItemDebugInfo
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventTimelineItem
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ProfileDetails
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.RedactedContent
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID_2
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.core.FakeSendHandle
import tech.orbitweb.orbitx.libraries.mediaplayer.api.MediaPlayer
import tech.orbitweb.orbitx.libraries.mediaplayer.test.FakeMediaPlayer
import tech.orbitweb.orbitx.tests.testutils.testCoroutineDispatchers
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RedactedVoiceMessageManagerTest {
    @Test
    fun `redacted event - no playing related media`() = runTest {
        val mediaPlayer = FakeMediaPlayer().apply {
            setMedia(uri = "someUri", mediaId = AN_EVENT_ID.value, mimeType = MimeTypes.Ogg)
            play()
        }
        val manager = aDefaultRedactedVoiceMessageManager(mediaPlayer = mediaPlayer)

        assertThat(mediaPlayer.state.value.mediaId).isEqualTo(AN_EVENT_ID.value)
        assertThat(mediaPlayer.state.value.isPlaying).isTrue()

        manager.onEachMatrixTimelineItem(aRedactedMatrixTimeline(AN_EVENT_ID_2))

        assertThat(mediaPlayer.state.value.mediaId).isEqualTo(AN_EVENT_ID.value)
        assertThat(mediaPlayer.state.value.isPlaying).isTrue()
    }

    @Test
    fun `redacted event - playing related media is paused`() = runTest {
        val mediaPlayer = FakeMediaPlayer().apply {
            setMedia(uri = "someUri", mediaId = AN_EVENT_ID.value, mimeType = MimeTypes.Ogg)
            play()
        }
        val manager = aDefaultRedactedVoiceMessageManager(mediaPlayer = mediaPlayer)

        assertThat(mediaPlayer.state.value.mediaId).isEqualTo(AN_EVENT_ID.value)
        assertThat(mediaPlayer.state.value.isPlaying).isTrue()

        manager.onEachMatrixTimelineItem(aRedactedMatrixTimeline(AN_EVENT_ID))

        assertThat(mediaPlayer.state.value.mediaId).isEqualTo(AN_EVENT_ID.value)
        assertThat(mediaPlayer.state.value.isPlaying).isFalse()
    }
}

fun TestScope.aDefaultRedactedVoiceMessageManager(
    mediaPlayer: MediaPlayer = FakeMediaPlayer(),
) = DefaultRedactedVoiceMessageManager(
    dispatchers = this.testCoroutineDispatchers(true),
    mediaPlayer = mediaPlayer,
)

fun aRedactedMatrixTimeline(eventId: EventId) = listOf<MatrixTimelineItem>(
    MatrixTimelineItem.Event(
        uniqueId = UniqueId("0"),
        event = EventTimelineItem(
            eventId = eventId,
            transactionId = null,
            isEditable = false,
            canBeRepliedTo = false,
            isOwn = false,
            isRemote = false,
            localSendState = null,
            reactions = persistentListOf(),
            receipts = persistentListOf(),
            sender = A_USER_ID,
            senderProfile = ProfileDetails.Unavailable,
            timestamp = 9442,
            content = RedactedContent,
            origin = null,
            timelineItemDebugInfoProvider = {
                TimelineItemDebugInfo(
                    model = "enim",
                    originalJson = null,
                    latestEditedJson = null,
                )
            },
            messageShieldProvider = { null },
            sendHandleProvider = { FakeSendHandle() },
            forwarder = null,
            forwarderProfile = null,
        ),
    )
)
