/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomdetails.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import im.vector.app.features.analytics.plan.Interaction
import tech.orbitweb.orbitx.features.knockrequests.api.KnockRequestPermissions
import tech.orbitweb.orbitx.features.knockrequests.api.knockRequestPermissions
import tech.orbitweb.orbitx.features.leaveroom.api.LeaveRoomEvent
import tech.orbitweb.orbitx.features.leaveroom.api.LeaveRoomState
import tech.orbitweb.orbitx.features.roomcall.api.RoomCallState
import tech.orbitweb.orbitx.features.roomdetails.impl.members.details.RoomMemberDetailsPresenter
import tech.orbitweb.orbitx.features.roomdetailsedit.api.RoomDetailsEditPermissions
import tech.orbitweb.orbitx.features.roomdetailsedit.api.roomDetailsEditPermissions
import tech.orbitweb.orbitx.features.securityandprivacy.api.SecurityAndPrivacyPermissions
import tech.orbitweb.orbitx.features.securityandprivacy.api.securityAndPrivacyPermissions
import tech.orbitweb.orbitx.libraries.androidutils.clipboard.ClipboardHelper
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.designsystem.utils.snackbar.LocalSnackbarDispatcher
import tech.orbitweb.orbitx.libraries.designsystem.utils.snackbar.SnackbarMessage
import tech.orbitweb.orbitx.libraries.designsystem.utils.snackbar.collectSnackbarMessageAsState
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.identity.IdentityState
import tech.orbitweb.orbitx.libraries.matrix.api.notificationsettings.NotificationSettingsService
import tech.orbitweb.orbitx.libraries.matrix.api.room.JoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMember
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.JoinRule
import tech.orbitweb.orbitx.libraries.matrix.api.room.powerlevels.canEditRolesAndPermissions
import tech.orbitweb.orbitx.libraries.matrix.api.room.powerlevels.permissionsAsState
import tech.orbitweb.orbitx.libraries.matrix.api.room.roomNotificationSettings
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.ReceiptType
import tech.orbitweb.orbitx.libraries.matrix.ui.room.getDirectRoomMember
import tech.orbitweb.orbitx.libraries.matrix.ui.room.roomMemberIdentityStateChange
import tech.orbitweb.orbitx.libraries.preferences.api.store.AppPreferencesStore
import tech.orbitweb.orbitx.libraries.preferences.api.store.SessionPreferencesStore
import tech.orbitweb.orbitx.libraries.push.api.notifications.NotificationCleaner
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsService
import tech.orbitweb.orbitx.services.analyticsproviders.api.trackers.captureInteraction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AssistedInject
class RoomDetailsPresenter(
    @Assisted private val navigator: RoomDetailsNavigator,
    private val client: MatrixClient,
    private val room: JoinedRoom,
    private val notificationSettingsService: NotificationSettingsService,
    private val roomMembersDetailsPresenterFactory: RoomMemberDetailsPresenter.Factory,
    private val leaveRoomPresenter: Presenter<LeaveRoomState>,
    private val roomCallStatePresenter: Presenter<RoomCallState>,
    private val dispatchers: CoroutineDispatchers,
    private val analyticsService: AnalyticsService,
    private val clipboardHelper: ClipboardHelper,
    private val appPreferencesStore: AppPreferencesStore,
    private val sessionPreferencesStore: SessionPreferencesStore,
    private val notificationCleaner: NotificationCleaner,
) : Presenter<RoomDetailsState> {
    @AssistedFactory
    interface Factory {
        fun create(
            navigator: RoomDetailsNavigator,
        ): RoomDetailsPresenter
    }

    @Composable
    override fun present(): RoomDetailsState {
        val scope = rememberCoroutineScope()
        val leaveRoomState = leaveRoomPresenter.present()
        val roomInfo by room.roomInfoFlow.collectAsState()
        val roomAvatar by remember { derivedStateOf { roomInfo.avatarUrl } }

        val roomName by remember { derivedStateOf { roomInfo.name?.trim().orEmpty() } }
        val roomTopic by remember { derivedStateOf { roomInfo.topic } }
        val isFavorite by remember { derivedStateOf { roomInfo.isFavorite } }
        val joinRule by remember { derivedStateOf { roomInfo.joinRule } }
        val hasNewContent by remember {
            derivedStateOf {
                roomInfo.numUnreadMessages > 0 ||
                    roomInfo.numUnreadMentions > 0 ||
                    roomInfo.numUnreadNotifications > 0 ||
                    roomInfo.isMarkedUnread
            }
        }

        val pinnedMessagesCount by remember { derivedStateOf { roomInfo.pinnedEventIds.size } }

        LaunchedEffect(Unit) {
            room.updateRoomNotificationSettings()
            observeNotificationSettings()
        }

        val isDm = roomInfo.isDm
        val membersState by room.membersStateFlow.collectAsState()
        val permissions by getPermissions()
        val canonicalAlias by remember { derivedStateOf { roomInfo.canonicalAlias } }
        val isEncrypted by remember { derivedStateOf { roomInfo.isEncrypted == true } }
        val dmMember by room.getDirectRoomMember(membersState)
        val roomMemberDetailsPresenter = roomMemberDetailsPresenter(dmMember?.userId)
        val roomType = getRoomType(dmMember)
        val roomCallState = roomCallStatePresenter.present()
        val joinedMemberCount by remember { derivedStateOf { roomInfo.joinedMembersCount } }

        val topicState = remember(permissions.editDetailsPermissions.canEditTopic, roomTopic, roomType) {
            val topic = roomTopic
            when {
                !topic.isNullOrBlank() -> RoomTopicState.ExistingTopic(topic)
                permissions.editDetailsPermissions.canEditTopic && roomType is RoomDetailsType.Room -> RoomTopicState.CanAddTopic
                else -> RoomTopicState.Hidden
            }
        }

        val knockRequestsCount by produceState<Int?>(null) {
            room.knockRequestsFlow.collect { value = it.size }
        }
        val canShowKnockRequests by remember {
            derivedStateOf { permissions.knockRequestsPermissions.hasAny && joinRule == JoinRule.Knock }
        }
        val canShowSecurityAndPrivacy by remember {
            derivedStateOf { !isDm && permissions.securityAndPrivacyPermissions.hasAny(isSpace = false, joinRule = joinRule) }
        }
        val isDeveloperModeEnabled by remember {
            appPreferencesStore.isDeveloperModeEnabledFlow()
        }.collectAsState(initial = false)

        val roomNotificationSettingsState by room.roomNotificationSettingsStateFlow.collectAsState()

        val snackbarDispatcher = LocalSnackbarDispatcher.current
        val snackbarMessage by snackbarDispatcher.collectSnackbarMessageAsState()

        fun handleEvent(event: RoomDetailsEvent) {
            when (event) {
                is RoomDetailsEvent.LeaveRoom -> {
                    leaveRoomState.eventSink(LeaveRoomEvent.LeaveRoom(room.roomId, needsConfirmation = event.needsConfirmation))
                }
                RoomDetailsEvent.MuteNotification -> {
                    scope.launch(dispatchers.io) {
                        notificationSettingsService.muteRoom(room.roomId)
                    }
                }
                RoomDetailsEvent.UnmuteNotification -> {
                    scope.launch(dispatchers.io) {
                        notificationSettingsService.unmuteRoom(room.roomId, isEncrypted, room.isDm())
                    }
                }
                is RoomDetailsEvent.SetFavorite -> scope.setFavorite(event.isFavorite)
                is RoomDetailsEvent.CopyToClipboard -> {
                    clipboardHelper.copyPlainText(event.text)
                    snackbarDispatcher.post(SnackbarMessage(CommonStrings.common_copied_to_clipboard))
                }
                is RoomDetailsEvent.MarkAsRead -> scope.markAsRead()
                is RoomDetailsEvent.MarkAsUnread -> scope.markAsUnread()
            }
        }

        val dmOtherMemberDetailsState = roomMemberDetailsPresenter?.present()

        val hasMemberVerificationViolations by produceState(false) {
            room.roomMemberIdentityStateChange(waitForEncryption = true)
                .onEach { identities -> value = identities.any { it.identityState == IdentityState.VerificationViolation } }
                .launchIn(this)
        }

        val canReportRoom by produceState(false) { value = client.canReportRoom() }

        return RoomDetailsState(
            roomId = room.roomId,
            roomName = roomName,
            roomAlias = canonicalAlias,
            roomAvatarUrl = roomAvatar,
            roomTopic = topicState,
            memberCount = joinedMemberCount,
            isEncrypted = isEncrypted,
            canInvite = permissions.canInvite,
            canEdit = roomType == RoomDetailsType.Room && permissions.editDetailsPermissions.hasAny,
            roomCallState = roomCallState,
            roomType = roomType,
            dmOtherMemberDetailsState = dmOtherMemberDetailsState,
            leaveRoomState = leaveRoomState,
            roomNotificationSettings = roomNotificationSettingsState.roomNotificationSettings(),
            isFavorite = isFavorite,
            displayRolesAndPermissionsSettings = !isDm && permissions.canEditRolesAndPermissions,
            isPublic = joinRule == JoinRule.Public,
            heroes = roomInfo.heroes,
            pinnedMessagesCount = pinnedMessagesCount,
            snackbarMessage = snackbarMessage,
            canShowKnockRequests = canShowKnockRequests,
            knockRequestsCount = knockRequestsCount,
            canShowSecurityAndPrivacy = canShowSecurityAndPrivacy,
            hasMemberVerificationViolations = hasMemberVerificationViolations,
            canReportRoom = !isDm && canReportRoom,
            isTombstoned = roomInfo.successorRoom != null,
            showDebugInfo = isDeveloperModeEnabled,
            roomVersion = roomInfo.roomVersion,
            roomHistoryVisibility = roomInfo.historyVisibility,
            hasNewContent = hasNewContent,
            eventSink = ::handleEvent,
        )
    }

    @Composable
    private fun roomMemberDetailsPresenter(userId: UserId?) = remember(userId) {
        userId?.let { userId ->
            roomMembersDetailsPresenterFactory.create(userId)
        }
    }

    @Composable
    private fun getRoomType(dmMember: RoomMember?): RoomDetailsType = remember(dmMember) {
        if (dmMember != null) {
            RoomDetailsType.Dm(otherMember = dmMember)
        } else {
            RoomDetailsType.Room
        }
    }

    private data class Permissions(
        val canInvite: Boolean = false,
        val editDetailsPermissions: RoomDetailsEditPermissions = RoomDetailsEditPermissions.DEFAULT,
        val knockRequestsPermissions: KnockRequestPermissions = KnockRequestPermissions.DEFAULT,
        val securityAndPrivacyPermissions: SecurityAndPrivacyPermissions = SecurityAndPrivacyPermissions.DEFAULT,
        val canEditRolesAndPermissions: Boolean = false,
    )

    @Composable
    private fun getPermissions(): State<Permissions> {
        return room.permissionsAsState(Permissions()) { perms ->
            Permissions(
                canInvite = perms.canOwnUserInvite(),
                editDetailsPermissions = perms.roomDetailsEditPermissions(),
                knockRequestsPermissions = perms.knockRequestPermissions(),
                canEditRolesAndPermissions = perms.canEditRolesAndPermissions(),
                securityAndPrivacyPermissions = perms.securityAndPrivacyPermissions(),
            )
        }
    }

    private fun CoroutineScope.observeNotificationSettings() {
        notificationSettingsService.notificationSettingsChangeFlow.onEach {
            room.updateRoomNotificationSettings()
        }.launchIn(this)
    }

    private fun CoroutineScope.setFavorite(isFavorite: Boolean) = launch {
        room.setIsFavorite(isFavorite)
            .onSuccess {
                analyticsService.captureInteraction(Interaction.Name.MobileRoomFavouriteToggle)
            }
    }

    private fun CoroutineScope.markAsRead() = launch {
        notificationCleaner.clearMessagesForRoom(client.sessionId, room.roomId)
        room.setUnreadFlag(isUnread = false)
        val receiptType = if (sessionPreferencesStore.isSendPublicReadReceiptsEnabled().first()) {
            ReceiptType.READ
        } else {
            ReceiptType.READ_PRIVATE
        }
        room.markAsRead(receiptType)
            .onSuccess {
                analyticsService.captureInteraction(name = Interaction.Name.MobileRoomListRoomContextMenuUnreadToggle)
            }
    }

    private fun CoroutineScope.markAsUnread() = launch {
        room.setUnreadFlag(isUnread = true)
            .onSuccess {
                analyticsService.captureInteraction(name = Interaction.Name.MobileRoomListRoomContextMenuUnreadToggle)
                navigator.onDone()
            }
    }
}
