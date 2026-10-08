/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.pinned.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import im.vector.app.features.analytics.plan.Interaction
import im.vector.app.features.analytics.plan.PinUnpinAction
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tech.orbitweb.orbitx.features.messages.api.timeline.HtmlConverterProvider
import tech.orbitweb.orbitx.features.messages.impl.UserEventPermissions
import tech.orbitweb.orbitx.features.messages.impl.actionlist.ActionListState
import tech.orbitweb.orbitx.features.messages.impl.actionlist.model.TimelineItemAction
import tech.orbitweb.orbitx.features.messages.impl.link.LinkState
import tech.orbitweb.orbitx.features.messages.impl.pinned.DefaultPinnedEventsTimelineProvider
import tech.orbitweb.orbitx.features.messages.impl.timeline.TimelineRoomInfo
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.TimelineItemsFactory
import tech.orbitweb.orbitx.features.messages.impl.timeline.factories.TimelineItemsFactoryConfig
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem
import tech.orbitweb.orbitx.features.messages.impl.timeline.protection.TimelineProtectionState
import tech.orbitweb.orbitx.features.messages.impl.typing.TypingNotificationState
import tech.orbitweb.orbitx.features.messages.impl.userEventPermissions
import tech.orbitweb.orbitx.features.roomcall.api.aStandByCallState
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.designsystem.utils.snackbar.SnackbarDispatcher
import tech.orbitweb.orbitx.libraries.designsystem.utils.snackbar.SnackbarMessage
import tech.orbitweb.orbitx.libraries.di.annotations.SessionCoroutineScope
import tech.orbitweb.orbitx.libraries.featureflag.api.FeatureFlagService
import tech.orbitweb.orbitx.libraries.featureflag.api.FeatureFlags
import tech.orbitweb.orbitx.libraries.matrix.api.room.JoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.api.room.powerlevels.permissionsAsState
import tech.orbitweb.orbitx.libraries.matrix.api.room.roomMembers
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsService
import tech.orbitweb.orbitx.services.analyticsproviders.api.trackers.captureInteraction
import timber.log.Timber

@AssistedInject
class PinnedMessagesListPresenter(
    @Assisted private val navigator: PinnedMessagesListNavigator,
    private val room: JoinedRoom,
    timelineItemsFactoryCreator: TimelineItemsFactory.Creator,
    private val timelineProvider: DefaultPinnedEventsTimelineProvider,
    private val timelineProtectionPresenter: Presenter<TimelineProtectionState>,
    private val linkPresenter: Presenter<LinkState>,
    private val snackbarDispatcher: SnackbarDispatcher,
    @Assisted private val actionListPresenter: Presenter<ActionListState>,
    @SessionCoroutineScope
    private val sessionCoroutineScope: CoroutineScope,
    private val analyticsService: AnalyticsService,
    private val featureFlagService: FeatureFlagService,
    private val htmlConverterProvider: HtmlConverterProvider,
) : Presenter<PinnedMessagesListState> {
    @AssistedFactory
    interface Factory {
        fun create(
            navigator: PinnedMessagesListNavigator,
            actionListPresenter: Presenter<ActionListState>,
        ): PinnedMessagesListPresenter
    }

    private val timelineItemsFactory: TimelineItemsFactory = timelineItemsFactoryCreator.create(
        config = TimelineItemsFactoryConfig(
            computeReadReceipts = false,
            computeReactions = false,
        )
    )

    @Composable
    override fun present(): PinnedMessagesListState {
        htmlConverterProvider.Update()
        val roomInfo by room.roomInfoFlow.collectAsState()
        val timelineRoomInfo by remember {
            derivedStateOf {
                TimelineRoomInfo(
                    isDm = roomInfo.isDm,
                    name = roomInfo.name,
                    // We don't need to compute those values
                    userHasPermissionToSendMessage = false,
                    userHasPermissionToSendReaction = false,
                    // We do not care about the call state here.
                    roomCallState = aStandByCallState(),
                    // don't compute this value or the pin icon will be shown
                    pinnedEventIds = persistentListOf(),
                    typingNotificationState = TypingNotificationState(
                        renderTypingNotifications = false,
                        typingMembers = persistentListOf(),
                        reserveSpace = false,
                    ),
                    predecessorRoom = room.predecessorRoom(),
                )
            }
        }
        val timelineProtectionState = timelineProtectionPresenter.present()
        val linkState = linkPresenter.present()
        val userEventPermissions by room.permissionsAsState(UserEventPermissions.DEFAULT) { perms ->
            perms.userEventPermissions()
        }

        val displayThreadSummaries by featureFlagService.isFeatureEnabledFlow(FeatureFlags.Threads).collectAsState(false)

        var pinnedMessageItems by remember {
            mutableStateOf<AsyncData<ImmutableList<TimelineItem>>>(AsyncData.Uninitialized)
        }
        PinnedMessagesListEffect(
            onItemsChange = { newItems ->
                pinnedMessageItems = newItems
            }
        )

        fun handleEvent(event: PinnedMessagesListEvent) {
            when (event) {
                is PinnedMessagesListEvent.HandleAction -> sessionCoroutineScope.handleTimelineAction(event.action, event.event)
                is PinnedMessagesListEvent.OpenThread -> navigator.navigateToThread(event.threadRootId)
            }
        }

        return pinnedMessagesListState(
            timelineRoomInfo = timelineRoomInfo,
            timelineProtectionState = timelineProtectionState,
            linkState = linkState,
            displayThreadSummaries = displayThreadSummaries,
            userEventPermissions = userEventPermissions,
            timelineItems = pinnedMessageItems,
            eventSink = ::handleEvent,
        )
    }

    private fun CoroutineScope.handleTimelineAction(
        action: TimelineItemAction,
        targetEvent: TimelineItem.Event,
    ) = launch {
        when (action) {
            TimelineItemAction.ViewSource -> {
                navigator.navigateToEventDebugInfo(targetEvent.eventId, targetEvent.debugInfo)
            }
            TimelineItemAction.Forward -> {
                targetEvent.eventId?.let { eventId ->
                    navigator.forwardEvent(eventId)
                }
            }
            TimelineItemAction.Unpin -> handleUnpinAction(targetEvent)
            TimelineItemAction.ViewInTimeline -> {
                targetEvent.eventId?.let { eventId ->
                    analyticsService.captureInteraction(Interaction.Name.PinnedMessageListViewTimeline)
                    navigator.viewInTimeline(eventId)
                }
            }
            else -> Unit
        }
    }

    private suspend fun handleUnpinAction(targetEvent: TimelineItem.Event) {
        if (targetEvent.eventId == null) return
        analyticsService.capture(
            PinUnpinAction(
                from = PinUnpinAction.From.MessagePinningList,
                kind = PinUnpinAction.Kind.Unpin,
            )
        )
        timelineProvider.invokeOnTimeline {
            unpinEvent(targetEvent.eventId)
                .onFailure {
                    Timber.e(it, "Failed to unpin event ${targetEvent.eventId}")
                    snackbarDispatcher.post(SnackbarMessage(CommonStrings.common_error))
                }
        }
    }

    @Composable
    private fun PinnedMessagesListEffect(onItemsChange: (AsyncData<ImmutableList<TimelineItem>>) -> Unit) {
        val updatedOnItemsChange by rememberUpdatedState(onItemsChange)

        val timelineState by timelineProvider.timelineStateFlow.collectAsState()

        LaunchedEffect(timelineState) {
            when (val asyncTimeline = timelineState) {
                AsyncData.Uninitialized -> flowOf(AsyncData.Uninitialized)
                is AsyncData.Failure -> flowOf(AsyncData.Failure(asyncTimeline.error))
                is AsyncData.Loading -> flowOf(AsyncData.Loading())
                is AsyncData.Success -> {
                    val timelineItemsFlow = asyncTimeline.data.timelineItems
                    combine(timelineItemsFlow, room.membersStateFlow) { items, membersState ->
                        timelineItemsFactory.replaceWith(
                            timelineItems = items,
                            roomMembers = membersState.roomMembers().orEmpty(),
                            renderReadReceipts = false,
                        )
                    }.launchIn(this)

                    timelineItemsFactory.timelineItems.map { timelineItems ->
                        AsyncData.Success(timelineItems)
                    }
                }
            }
                .onEach { items ->
                    updatedOnItemsChange(items)
                }
                .launchIn(this)
        }
    }

    @Composable
    private fun pinnedMessagesListState(
        timelineRoomInfo: TimelineRoomInfo,
        timelineProtectionState: TimelineProtectionState,
        displayThreadSummaries: Boolean,
        linkState: LinkState,
        userEventPermissions: UserEventPermissions,
        timelineItems: AsyncData<ImmutableList<TimelineItem>>,
        eventSink: (PinnedMessagesListEvent) -> Unit
    ): PinnedMessagesListState {
        return when (timelineItems) {
            AsyncData.Uninitialized, is AsyncData.Loading -> PinnedMessagesListState.Loading
            is AsyncData.Failure -> PinnedMessagesListState.Failed
            is AsyncData.Success -> {
                if (timelineItems.data.isEmpty()) {
                    PinnedMessagesListState.Empty
                } else {
                    val actionListState = actionListPresenter.present()
                    PinnedMessagesListState.Filled(
                        timelineRoomInfo = timelineRoomInfo,
                        timelineProtectionState = timelineProtectionState,
                        displayThreadSummaries = displayThreadSummaries,
                        linkState = linkState,
                        userEventPermissions = userEventPermissions,
                        timelineItems = timelineItems.data,
                        actionListState = actionListState,
                        eventSink = eventSink
                    )
                }
            }
        }
    }
}
