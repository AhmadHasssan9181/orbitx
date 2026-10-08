/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.pinned.list

import android.content.Context
import android.view.HapticFeedbackConstants
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.features.messages.impl.actionlist.ActionListPresenter
import tech.orbitweb.orbitx.features.messages.impl.timeline.di.LocalTimelineItemPresenterFactories
import tech.orbitweb.orbitx.features.messages.impl.timeline.di.TimelineItemPresenterFactories
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem
import tech.orbitweb.orbitx.libraries.androidutils.system.copyToClipboard
import tech.orbitweb.orbitx.libraries.androidutils.system.openUrlInExternalApp
import tech.orbitweb.orbitx.libraries.architecture.callback
import tech.orbitweb.orbitx.libraries.di.RoomScope
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.permalink.PermalinkData
import tech.orbitweb.orbitx.libraries.matrix.api.permalink.PermalinkParser
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.TimelineItemDebugInfo
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.libraries.ui.utils.a11y.hasExternalKeyboard
import tech.orbitweb.orbitx.libraries.ui.utils.a11y.isTalkbackActive

@ContributesNode(RoomScope::class)
@AssistedInject
class PinnedMessagesListNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: PinnedMessagesListPresenter.Factory,
    actionListPresenterFactory: ActionListPresenter.Factory,
    private val timelineItemPresenterFactories: TimelineItemPresenterFactories,
    private val permalinkParser: PermalinkParser,
) : Node(buildContext, plugins = plugins), PinnedMessagesListNavigator {
    interface Callback : Plugin {
        fun handleEventClick(event: TimelineItem.Event, canUseOverlay: Boolean)
        fun handleGalleryItemClick(event: TimelineItem.Event, galleryItemIndex: Int, canUseOverlay: Boolean)
        fun navigateToRoomMemberDetails(userId: UserId)
        fun viewInTimeline(eventId: EventId)
        fun handlePermalinkClick(data: PermalinkData.RoomLink)
        fun navigateToEventDebugInfo(eventId: EventId?, debugInfo: TimelineItemDebugInfo)
        fun handleForwardEventClick(eventId: EventId)
        fun navigateToThread(threadRootId: ThreadId)
    }

    private val callback: Callback = callback()
    private val presenter = presenterFactory.create(
        navigator = this,
        actionListPresenter = actionListPresenterFactory.create(
            postProcessor = PinnedMessagesListTimelineActionPostProcessor(),
            timelineMode = Timeline.Mode.PinnedEvents,
        )
    )

    private fun onLinkClick(context: Context, url: String) {
        when (val permalink = permalinkParser.parse(url)) {
            is PermalinkData.UserLink -> {
                // Open the room member profile, it will fallback to
                // the user profile if the user is not in the room
                callback.navigateToRoomMemberDetails(permalink.userId)
            }
            is PermalinkData.RoomLink -> {
                callback.handlePermalinkClick(permalink)
            }
            is PermalinkData.FallbackLink,
            is PermalinkData.RoomEmailInviteLink -> {
                context.openUrlInExternalApp(url)
            }
        }
    }

    override fun viewInTimeline(eventId: EventId) {
        callback.viewInTimeline(eventId)
    }

    override fun navigateToEventDebugInfo(eventId: EventId?, debugInfo: TimelineItemDebugInfo) {
        callback.navigateToEventDebugInfo(eventId, debugInfo)
    }

    override fun forwardEvent(eventId: EventId) {
        callback.handleForwardEventClick(eventId)
    }

    override fun navigateToThread(threadRootId: ThreadId) {
        callback.navigateToThread(threadRootId)
    }

    @Composable
    override fun View(modifier: Modifier) {
        val canUseOverlay = !isTalkbackActive() && !hasExternalKeyboard()
        CompositionLocalProvider(
            LocalTimelineItemPresenterFactories provides timelineItemPresenterFactories,
        ) {
            val context = LocalContext.current
            val toastMessage = stringResource(CommonStrings.common_copied_to_clipboard)
            val view = LocalView.current
            val state = presenter.present()
            PinnedMessagesListView(
                state = state,
                onBackClick = ::navigateUp,
                onEventClick = {
                    callback.handleEventClick(it, canUseOverlay)
                },
                onGalleryItemClick = { event, index ->
                    callback.handleGalleryItemClick(event, index, canUseOverlay)
                },
                onUserDataClick = { callback.navigateToRoomMemberDetails(it.userId) },
                onLinkClick = { link -> onLinkClick(context, link.url) },
                onLinkLongClick = {
                    view.performHapticFeedback(
                        HapticFeedbackConstants.LONG_PRESS
                    )
                    context.copyToClipboard(
                        text = it.url,
                        toastMessage = toastMessage,
                    )
                },
                modifier = modifier
            )
        }
    }
}
