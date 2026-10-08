/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.event

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.element.android.wysiwyg.link.Link
import tech.orbitweb.orbitx.features.messages.impl.timeline.TimelineEvent
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.layout.ContentAvoidingLayoutData
import tech.orbitweb.orbitx.features.messages.impl.timeline.di.LocalTimelineItemPresenterFactories
import tech.orbitweb.orbitx.features.messages.impl.timeline.di.rememberPresenter
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemAttachmentsContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemAudioContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemEncryptedContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemEventContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemFileContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemGalleryContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemImageContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemLegacyCallInviteContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemLocationContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemPollContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemRedactedContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemRtcNotificationContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemStateContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemStickerContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemTextBasedContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemUnknownContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemVideoContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemVoiceContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.ensureActiveLiveLocation
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.voiceplayer.api.VoiceMessageState

@Composable
fun TimelineItemEventContentView(
    content: TimelineItemEventContent,
    hideMediaContent: Boolean,
    onContentClick: (() -> Unit)?,
    onGalleryItemClick: ((Int) -> Unit),
    onLongClick: (() -> Unit)?,
    onShowContentClick: () -> Unit,
    onLinkClick: (Link) -> Unit,
    onLinkLongClick: (Link) -> Unit,
    eventSink: (TimelineEvent.TimelineItemEvent) -> Unit,
    modifier: Modifier = Modifier,
    onContentLayoutChange: (ContentAvoidingLayoutData) -> Unit = {},
    eventId: tech.orbitweb.orbitx.libraries.matrix.api.core.EventId? = null,
    isMine: Boolean = false,
) {
    val presenterFactories = LocalTimelineItemPresenterFactories.current
    when (content) {
        is TimelineItemEncryptedContent -> TimelineItemEncryptedView(
            content = content,
            onContentLayoutChange = onContentLayoutChange,
            modifier = modifier
        )
        is TimelineItemRedactedContent -> TimelineItemRedactedView(
            content = content,
            onContentLayoutChange = onContentLayoutChange,
            modifier = modifier
        )
        is TimelineItemTextBasedContent -> TimelineItemTextView(
            content = content,
            modifier = modifier,
            onLinkClick = onLinkClick,
            onLinkLongClick = onLinkLongClick,
            onContentLayoutChange = onContentLayoutChange,
            eventId = eventId,
            eventSink = eventSink,
            isMine = isMine,
        )
        is TimelineItemUnknownContent -> TimelineItemUnknownView(
            content = content,
            onContentLayoutChange = onContentLayoutChange,
            modifier = modifier
        )
        is TimelineItemLocationContent -> {
            TimelineItemLocationView(
                content = content.ensureActiveLiveLocation(),
                onStopLiveLocationClick = { eventSink(TimelineEvent.StopLiveLocationShare) },
                modifier = modifier
            )
        }
        is TimelineItemImageContent -> TimelineItemImageView(
            content = content,
            hideMediaContent = hideMediaContent,
            onContentClick = onContentClick,
            onLongClick = onLongClick,
            onShowContentClick = onShowContentClick,
            onLinkClick = onLinkClick,
            onLinkLongClick = onLinkLongClick,
            onContentLayoutChange = onContentLayoutChange,
            modifier = modifier,
            eventId = eventId,
            isMine = isMine,
        )
        is TimelineItemGalleryContent -> TimelineItemGalleryView(
            content = content,
            onGalleryItemClick = { index -> onGalleryItemClick(index) },
            onLongClick = onLongClick,
            onLinkClick = onLinkClick,
            onLinkLongClick = onLinkLongClick,
            onContentLayoutChange = onContentLayoutChange,
            modifier = modifier,
        )
        is TimelineItemAttachmentsContent -> TimelineItemAttachmentsListView(
            content = content,
            onGalleryItemClick = { index -> onGalleryItemClick(index) },
            onLinkClick = onLinkClick,
            onLinkLongClick = onLinkLongClick,
            onContentLayoutChange = {},
            modifier = modifier,
        )
        is TimelineItemStickerContent -> TimelineItemStickerView(
            content = content,
            hideMediaContent = hideMediaContent,
            onContentClick = onContentClick,
            onLongClick = onLongClick,
            onShowClick = onShowContentClick,
            modifier = modifier,
        )
        is TimelineItemVideoContent -> TimelineItemVideoView(
            content = content,
            hideMediaContent = hideMediaContent,
            onContentClick = onContentClick,
            onLongClick = onLongClick,
            onShowContentClick = onShowContentClick,
            onLinkClick = onLinkClick,
            onLinkLongClick = onLinkLongClick,
            onContentLayoutChange = onContentLayoutChange,
            modifier = modifier,
            eventId = eventId,
            isMine = isMine,
        )
        is TimelineItemFileContent -> TimelineItemFileView(
            content = content,
            onContentLayoutChange = onContentLayoutChange,
            modifier = modifier
        )
        is TimelineItemAudioContent -> TimelineItemAudioView(
            content = content,
            onContentClick = onContentClick,
            onLongClick = onLongClick,
            onContentLayoutChange = onContentLayoutChange,
            modifier = modifier,
            eventId = eventId,
            isMine = isMine,
        )
        is TimelineItemLegacyCallInviteContent -> TimelineItemLegacyCallInviteView(modifier = modifier)
        is TimelineItemStateContent -> TimelineItemStateView(
            content = content,
            modifier = modifier
        )
        is TimelineItemPollContent -> TimelineItemPollView(
            content = content,
            eventSink = eventSink,
            modifier = modifier,
        )
        is TimelineItemVoiceContent -> {
            val presenter: Presenter<VoiceMessageState> = presenterFactories.rememberPresenter(content)
            TimelineItemVoiceView(
                state = presenter.present(),
                content = content,
                onContentLayoutChange = onContentLayoutChange,
                modifier = modifier,
                eventSink = eventSink,
                isMine = isMine,
            )
        }
        is TimelineItemRtcNotificationContent -> error("This shouldn't be rendered as the content of a bubble")
    }
}
