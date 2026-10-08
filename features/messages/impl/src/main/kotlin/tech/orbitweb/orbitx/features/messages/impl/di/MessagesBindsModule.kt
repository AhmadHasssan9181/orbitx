/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.di

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.ContributesTo
import tech.orbitweb.orbitx.features.messages.impl.crypto.identity.IdentityChangeState
import tech.orbitweb.orbitx.features.messages.impl.crypto.identity.IdentityChangeStatePresenter
import tech.orbitweb.orbitx.features.messages.impl.crypto.sendfailure.resolve.ResolveVerifiedUserSendFailurePresenter
import tech.orbitweb.orbitx.features.messages.impl.crypto.sendfailure.resolve.ResolveVerifiedUserSendFailureState
import tech.orbitweb.orbitx.features.messages.impl.link.LinkPresenter
import tech.orbitweb.orbitx.features.messages.impl.link.LinkState
import tech.orbitweb.orbitx.features.messages.impl.pinned.banner.PinnedMessagesBannerPresenter
import tech.orbitweb.orbitx.features.messages.impl.pinned.banner.PinnedMessagesBannerState
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.customreaction.CustomReactionPresenter
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.customreaction.CustomReactionState
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.reactionsummary.ReactionSummaryPresenter
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.reactionsummary.ReactionSummaryState
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.receipt.bottomsheet.ReadReceiptBottomSheetPresenter
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.receipt.bottomsheet.ReadReceiptBottomSheetState
import tech.orbitweb.orbitx.features.messages.impl.timeline.protection.TimelineProtectionPresenter
import tech.orbitweb.orbitx.features.messages.impl.timeline.protection.TimelineProtectionState
import tech.orbitweb.orbitx.features.messages.impl.typing.TypingNotificationPresenter
import tech.orbitweb.orbitx.features.messages.impl.typing.TypingNotificationState
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.di.RoomScope

@ContributesTo(RoomScope::class)
@BindingContainer
interface MessagesBindsModule {
    @Binds
    fun bindPinnedMessagesBannerPresenter(presenter: PinnedMessagesBannerPresenter): Presenter<PinnedMessagesBannerState>

    @Binds
    fun bindResolveVerifiedUserSendFailurePresenter(presenter: ResolveVerifiedUserSendFailurePresenter): Presenter<ResolveVerifiedUserSendFailureState>

    @Binds
    fun bindTypingNotificationPresenter(presenter: TypingNotificationPresenter): Presenter<TypingNotificationState>

    @Binds
    fun bindTimelineProtectionPresenter(presenter: TimelineProtectionPresenter): Presenter<TimelineProtectionState>

    @Binds
    fun bindLinkPresenter(presenter: LinkPresenter): Presenter<LinkState>

    @Binds
    fun bindCustomReactionPresenter(presenter: CustomReactionPresenter): Presenter<CustomReactionState>

    @Binds
    fun bindReactionSummaryPresenter(presenter: ReactionSummaryPresenter): Presenter<ReactionSummaryState>

    @Binds
    fun bindReadReceiptBottomSheetPresenter(presenter: ReadReceiptBottomSheetPresenter): Presenter<ReadReceiptBottomSheetState>

    @Binds
    fun bindIdentityChangeStatePresenter(presenter: IdentityChangeStatePresenter): Presenter<IdentityChangeState>
}
