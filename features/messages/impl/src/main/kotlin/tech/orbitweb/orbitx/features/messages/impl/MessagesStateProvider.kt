/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.messages.api.timeline.voicemessages.composer.VoiceMessageComposerState
import tech.orbitweb.orbitx.features.messages.api.timeline.voicemessages.composer.aVoiceMessageComposerState
import tech.orbitweb.orbitx.features.messages.api.timeline.voicemessages.composer.aVoiceMessagePreviewState
import tech.orbitweb.orbitx.features.messages.impl.actionlist.ActionListState
import tech.orbitweb.orbitx.features.messages.impl.actionlist.anActionListState
import tech.orbitweb.orbitx.features.messages.impl.crypto.identity.IdentityChangeState
import tech.orbitweb.orbitx.features.messages.impl.crypto.identity.aRoomMemberIdentityStateChange
import tech.orbitweb.orbitx.features.messages.impl.crypto.identity.anIdentityChangeState
import tech.orbitweb.orbitx.features.messages.impl.link.LinkState
import tech.orbitweb.orbitx.features.messages.impl.link.aLinkState
import tech.orbitweb.orbitx.features.messages.impl.messagecomposer.MessageComposerState
import tech.orbitweb.orbitx.features.messages.impl.messagecomposer.aMessageComposerState
import tech.orbitweb.orbitx.features.messages.impl.pinned.banner.PinnedMessagesBannerState
import tech.orbitweb.orbitx.features.messages.impl.pinned.banner.aLoadedPinnedMessagesBannerState
import tech.orbitweb.orbitx.features.messages.impl.timeline.TimelineState
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemList
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineState
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.customreaction.CustomReactionEvent
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.customreaction.CustomReactionState
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.reactionsummary.ReactionSummaryEvent
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.reactionsummary.ReactionSummaryState
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.receipt.bottomsheet.ReadReceiptBottomSheetEvent
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.receipt.bottomsheet.ReadReceiptBottomSheetState
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemTextContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.protection.TimelineProtectionState
import tech.orbitweb.orbitx.features.messages.impl.timeline.protection.aTimelineProtectionState
import tech.orbitweb.orbitx.features.roomcall.api.RoomCallState
import tech.orbitweb.orbitx.features.roomcall.api.aStandByCallState
import tech.orbitweb.orbitx.features.roommembermoderation.api.RoomMemberModerationEvents
import tech.orbitweb.orbitx.features.roommembermoderation.api.RoomMemberModerationPermissions
import tech.orbitweb.orbitx.features.roommembermoderation.api.RoomMemberModerationState
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarSize
import tech.orbitweb.orbitx.libraries.designsystem.preview.ROOM_NAME
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.identity.IdentityState
import tech.orbitweb.orbitx.libraries.matrix.api.room.tombstone.SuccessorRoom
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.libraries.textcomposer.model.MessageComposerMode
import tech.orbitweb.orbitx.libraries.textcomposer.model.aTextEditorStateMarkdown
import tech.orbitweb.orbitx.libraries.textcomposer.model.aTextEditorStateRich
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

open class MessagesStateProvider : PreviewParameterProvider<MessagesState> {
    override val values: Sequence<MessagesState>
        get() = sequenceOf(
            aMessagesState(),
            aMessagesState(composerState = aMessageComposerState(showAttachmentSourcePicker = true)),
            aMessagesState(userEventPermissions = aUserEventPermissions(canSendMessage = false)),
            aMessagesState(showReinvitePrompt = true),
            aMessagesState(composerState = aMessageComposerState(showTextFormatting = true)),
            aMessagesState(
                voiceMessageComposerState = aVoiceMessageComposerState(showPermissionRationaleDialog = true),
            ),
            aMessagesState(
                voiceMessageComposerState = aVoiceMessageComposerState(
                    voiceMessageState = aVoiceMessagePreviewState(),
                    showSendFailureDialog = true
                ),
            ),
            aMessagesState(
                pinnedMessagesBannerState = aLoadedPinnedMessagesBannerState(
                    knownPinnedMessagesCount = 4,
                    currentPinnedMessageIndex = 0,
                ),
            ),
            aMessagesState(isCurrentlySharingLiveLocationInRoom = true),
            aMessagesState(successorRoom = SuccessorRoom(RoomId("!id:domain"), null)),
            aMessagesState(
                timelineState = aTimelineState(
                    timelineMode = Timeline.Mode.Thread(threadRootId = ThreadId("\$a-thread-id")),
                    timelineItems = aTimelineItemList(aTimelineItemTextContent()),
                )
            ),
            aMessagesState(
                composerState = aMessageComposerState(textEditorState = aTextEditorStateMarkdown()),
                identityChangeState = anIdentityChangeState(listOf(aRoomMemberIdentityStateChange()))
            ),
        )
}

fun aMessagesState(
    roomName: String? = ROOM_NAME,
    roomAvatar: AvatarData = AvatarData("!id:domain", ROOM_NAME, size = AvatarSize.TimelineRoom),
    userEventPermissions: UserEventPermissions = aUserEventPermissions(),
    composerState: MessageComposerState = aMessageComposerState(
        textEditorState = aTextEditorStateRich(initialText = "Hello", initialFocus = true),
        isFullScreen = false,
        mode = MessageComposerMode.Normal,
    ),
    voiceMessageComposerState: VoiceMessageComposerState = aVoiceMessageComposerState(),
    timelineState: TimelineState = aTimelineState(
        timelineItems = aTimelineItemList(aTimelineItemTextContent()),
        // Render a focused event for an event with sender information displayed
        focusedEventIndex = 2,
    ),
    timelineProtectionState: TimelineProtectionState = aTimelineProtectionState(),
    identityChangeState: IdentityChangeState = anIdentityChangeState(),
    linkState: LinkState = aLinkState(),
    readReceiptBottomSheetState: ReadReceiptBottomSheetState = aReadReceiptBottomSheetState(),
    actionListState: ActionListState = anActionListState(),
    customReactionState: CustomReactionState = aCustomReactionState(),
    reactionSummaryState: ReactionSummaryState = aReactionSummaryState(),
    showReinvitePrompt: Boolean = false,
    roomCallState: RoomCallState = aStandByCallState(),
    pinnedMessagesBannerState: PinnedMessagesBannerState = aLoadedPinnedMessagesBannerState(),
    dmUserVerificationState: IdentityState? = null,
    roomMemberModerationState: RoomMemberModerationState = aRoomMemberModerationState(),
    topBarSharedHistoryIcon: SharedHistoryIcon = SharedHistoryIcon.NONE,
    successorRoom: SuccessorRoom? = null,
    threads: MessagesState.Threads = MessagesState.Threads(
        hasThreads = false,
        hasUnreadThreads = false,
    ),
    isCurrentlySharingLiveLocationInRoom: Boolean = false,
    eventSink: (MessagesEvent) -> Unit = {},
) = MessagesState(
    roomId = RoomId("!id:domain"),
    roomName = roomName,
    roomAvatar = roomAvatar,
    heroes = persistentListOf(),
    userEventPermissions = userEventPermissions,
    composerState = composerState,
    voiceMessageComposerState = voiceMessageComposerState,
    timelineProtectionState = timelineProtectionState,
    identityChangeState = identityChangeState,
    linkState = linkState,
    timelineState = timelineState,
    readReceiptBottomSheetState = readReceiptBottomSheetState,
    actionListState = actionListState,
    customReactionState = customReactionState,
    reactionSummaryState = reactionSummaryState,
    snackbarMessage = null,
    inviteProgress = AsyncData.Uninitialized,
    showReinvitePrompt = showReinvitePrompt,
    enableTextFormatting = true,
    roomCallState = roomCallState,
    appName = "Orbit",
    pinnedMessagesBannerState = pinnedMessagesBannerState,
    dmUserVerificationState = dmUserVerificationState,
    roomMemberModerationState = roomMemberModerationState,
    topBarSharedHistoryIcon = topBarSharedHistoryIcon,
    successorRoom = successorRoom,
    threads = threads,
    showLiveLocationShareBanner = isCurrentlySharingLiveLocationInRoom,
    eventSink = eventSink,
)

fun aRoomMemberModerationState(
    permissions: RoomMemberModerationPermissions = RoomMemberModerationPermissions.DEFAULT,
) = object : RoomMemberModerationState {
    override val permissions: RoomMemberModerationPermissions = permissions
    override val eventSink: (RoomMemberModerationEvents) -> Unit = {}
}

fun aUserEventPermissions(
    canRedactOwn: Boolean = false,
    canRedactOther: Boolean = false,
    canSendMessage: Boolean = true,
    canSendReaction: Boolean = true,
    canPinUnpin: Boolean = false,
) = UserEventPermissions(
    canRedactOwn = canRedactOwn,
    canRedactOther = canRedactOther,
    canSendMessage = canSendMessage,
    canSendReaction = canSendReaction,
    canPinUnpin = canPinUnpin,
)

fun aReactionSummaryState(
    target: ReactionSummaryState.Summary? = null,
    eventSink: (ReactionSummaryEvent) -> Unit = {}
) = ReactionSummaryState(
    target = target,
    eventSink = eventSink,
)

fun aCustomReactionState(
    target: CustomReactionState.Target = CustomReactionState.Target.None,
    recentEmojis: ImmutableList<String> = persistentListOf(),
    eventSink: (CustomReactionEvent) -> Unit = {},
) = CustomReactionState(
    target = target,
    recentEmojis = recentEmojis,
    selectedEmoji = persistentSetOf(),
    eventSink = eventSink,
)

fun aReadReceiptBottomSheetState(
    selectedEvent: TimelineItem.Event? = null,
    eventSink: (ReadReceiptBottomSheetEvent) -> Unit = {},
) = ReadReceiptBottomSheetState(
    selectedEvent = selectedEvent,
    eventSink = eventSink,
)
