/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.impl.show

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.location.api.Location
import tech.orbitweb.orbitx.features.location.impl.common.ui.LocationConstraintsDialogState
import tech.orbitweb.orbitx.features.location.impl.common.userlocation.UserLocationState
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarSize
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_ALICE
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.room.location.AssetType
import kotlinx.collections.immutable.toImmutableList

class ShowLocationStateProvider : PreviewParameterProvider<ShowLocationState> {
    override val values: Sequence<ShowLocationState>
        get() = sequenceOf(
            aShowLocationState(),
            aShowLocationState(isLive = true),
            aShowLocationState(isLive = true, locationShares = emptyList()),
            aShowLocationState(
                constraintsDialogState = LocationConstraintsDialogState.PermissionDenied,
            ),
            aShowLocationState(
                constraintsDialogState = LocationConstraintsDialogState.PermissionRationale,
            ),
            aShowLocationState(
                constraintsDialogState = LocationConstraintsDialogState.LocationServiceDisabled,
            ),
            aShowLocationState(isTrackMyLocation = true),
            aShowLocationState(customMapStyleUrl = AsyncData.Loading()),
        )
}

private const val APP_NAME = "ApplicationName"

fun aShowLocationState(
    customMapStyleUrl: AsyncData<String?> = AsyncData.Success(null),
    isLive: Boolean = false,
    constraintsDialogState: LocationConstraintsDialogState = LocationConstraintsDialogState.None,
    locationShares: List<LocationShareItem> = listOf(aLocationShareItem(isLive = isLive)),
    focusedLocation: LocationShareItem? = locationShares.firstOrNull(),
    isTrackMyLocation: Boolean = false,
    userLocationState: UserLocationState = UserLocationState(null),
    appName: String = APP_NAME,
    hideUserLocationPuck: Boolean = false,
    eventSink: (ShowLocationEvent) -> Unit = {},
): ShowLocationState {
    return ShowLocationState(
        customMapStyleUrl = customMapStyleUrl,
        dialogState = constraintsDialogState,
        locationShares = locationShares.toImmutableList(),
        focusedLocation = focusedLocation,
        isTrackMyLocation = isTrackMyLocation,
        userLocationState = userLocationState,
        hideUserLocationPuck = hideUserLocationPuck,
        appName = appName,
        isLive = isLive,
        eventSink = eventSink,
    )
}

fun aLocationShareItem(
    userId: UserId = UserId("@alice:matrix.org"),
    displayName: String = USER_NAME_ALICE,
    avatarData: AvatarData = AvatarData(
        id = userId.value,
        name = displayName,
        url = null,
        size = AvatarSize.UserListItem,
    ),
    isLive: Boolean = false,
    assetType: AssetType? = null,
    formattedTimestamp: String = "Shared 1 min ago",
    location: Location = Location(1.23, 2.34, 4f),
    isOwnUser: Boolean = false,
) = LocationShareItem(
    userId = userId,
    displayName = displayName,
    avatarData = avatarData,
    formattedTimestamp = formattedTimestamp,
    location = location,
    isLive = isLive,
    assetType = assetType,
    isOwnUser = isOwnUser,
)
