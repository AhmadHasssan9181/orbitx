/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.roomlist

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableSet
import tech.orbitweb.orbitx.features.home.impl.model.RoomListRoomSummary
import tech.orbitweb.orbitx.libraries.fullscreenintent.api.FullScreenIntentPermissionsState
import tech.orbitweb.orbitx.libraries.fullscreenintent.api.aFullScreenIntentPermissionsState
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.push.api.battery.BatteryOptimizationState
import tech.orbitweb.orbitx.libraries.push.api.battery.aBatteryOptimizationState

open class RoomListContentStateProvider : PreviewParameterProvider<RoomListContentState> {
    override val values: Sequence<RoomListContentState>
        get() = sequenceOf(
            aRoomsContentState(),
            aRoomsContentState(summaries = persistentListOf()),
            aSkeletonContentState(),
            anEmptyContentState(),
            anEmptyContentState(securityBannerState = SecurityBannerState.SetUpRecovery),
            aRoomsContentState(
                showNewNotificationSoundBanner = true,
            ),
        )
}

internal fun aRoomsContentState(
    securityBannerState: SecurityBannerState = SecurityBannerState.None,
    showNewNotificationSoundBanner: Boolean = false,
    showUnreadCount: Boolean = false,
    summaries: ImmutableList<RoomListRoomSummary> = aRoomListRoomSummaryList(),
    fullScreenIntentPermissionsState: FullScreenIntentPermissionsState = aFullScreenIntentPermissionsState(),
    batteryOptimizationState: BatteryOptimizationState = aBatteryOptimizationState(),
    seenRoomInvites: Set<RoomId> = emptySet(),
) = RoomListContentState.Rooms(
    securityBannerState = securityBannerState,
    showNewNotificationSoundBanner = showNewNotificationSoundBanner,
    showUnreadCount = showUnreadCount,
    fullScreenIntentPermissionsState = fullScreenIntentPermissionsState,
    batteryOptimizationState = batteryOptimizationState,
    summaries = summaries,
    seenRoomInvites = seenRoomInvites.toImmutableSet(),
)

internal fun aSkeletonContentState() = RoomListContentState.Skeleton(16)

internal fun anEmptyContentState(
    securityBannerState: SecurityBannerState = SecurityBannerState.None,
) = RoomListContentState.Empty(
    securityBannerState = securityBannerState,
)
