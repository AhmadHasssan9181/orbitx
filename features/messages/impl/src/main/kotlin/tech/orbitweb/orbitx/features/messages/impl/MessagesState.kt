/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl

import tech.orbitweb.orbitx.features.messages.api.timeline.voicemessages.composer.VoiceMessageComposerState
import tech.orbitweb.orbitx.features.messages.impl.actionlist.ActionListState
import tech.orbitweb.orbitx.features.messages.impl.crypto.identity.IdentityChangeState
import tech.orbitweb.orbitx.features.messages.impl.link.LinkState
import tech.orbitweb.orbitx.features.messages.impl.messagecomposer.MessageComposerState
import tech.orbitweb.orbitx.features.messages.impl.pinned.banner.PinnedMessagesBannerState
import tech.orbitweb.orbitx.features.messages.impl.timeline.TimelineState
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.customreaction.CustomReactionState
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.reactionsummary.ReactionSummaryState
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.receipt.bottomsheet.ReadReceiptBottomSheetState
import tech.orbitweb.orbitx.features.messages.impl.timeline.protection.TimelineProtectionState
import tech.orbitweb.orbitx.features.roomcall.api.RoomCallState
import tech.orbitweb.orbitx.features.roommembermoderation.api.RoomMemberModerationState
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import tech.orbitweb.orbitx.libraries.designsystem.utils.snackbar.SnackbarMessage
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.identity.IdentityState
import tech.orbitweb.orbitx.libraries.matrix.api.room.tombstone.SuccessorRoom
import kotlinx.collections.immutable.ImmutableList

data class MessagesState(
    val roomId: RoomId,
    val roomName: String?,
    val roomAvatar: AvatarData,
    val heroes: ImmutableList<AvatarData>,
    val userEventPermissions: UserEventPermissions,
    val composerState: MessageComposerState,
    val voiceMessageComposerState: VoiceMessageComposerState,
    val timelineState: TimelineState,
    val timelineProtectionState: TimelineProtectionState,
    val identityChangeState: IdentityChangeState,
    val linkState: LinkState,
    val actionListState: ActionListState,
    val customReactionState: CustomReactionState,
    val reactionSummaryState: ReactionSummaryState,
    val readReceiptBottomSheetState: ReadReceiptBottomSheetState,
    val snackbarMessage: SnackbarMessage?,
    val inviteProgress: AsyncData<Unit>,
    val showReinvitePrompt: Boolean,
    val enableTextFormatting: Boolean,
    val roomCallState: RoomCallState,
    val appName: String,
    val pinnedMessagesBannerState: PinnedMessagesBannerState,
    val dmUserVerificationState: IdentityState?,
    val roomMemberModerationState: RoomMemberModerationState,
    /** Type of "shared history" icon to show in the top bar. */
    val topBarSharedHistoryIcon: SharedHistoryIcon,
    val successorRoom: SuccessorRoom?,
    val threads: Threads,
    val showLiveLocationShareBanner: Boolean,
    val eventSink: (MessagesEvent) -> Unit
) {
    val isTombstoned = successorRoom != null

    data class Threads(
        val hasThreads: Boolean,
        val hasUnreadThreads: Boolean,
    )
}

/** Type of "shared history" icon to show in the top bar. */
enum class SharedHistoryIcon {
    /** Show no icon at all. */
    NONE,

    /** history_visibility: shared. */
    SHARED,

    /** history_visibility: world_readable. */
    WORLD_READABLE
}
