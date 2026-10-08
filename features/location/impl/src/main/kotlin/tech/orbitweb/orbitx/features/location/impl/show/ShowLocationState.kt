/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.impl.show

import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.features.location.api.Location
import tech.orbitweb.orbitx.features.location.impl.common.ui.LocationConstraintsDialogState
import tech.orbitweb.orbitx.features.location.impl.common.ui.LocationMarkerData
import tech.orbitweb.orbitx.features.location.impl.common.userlocation.UserLocationState
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.designsystem.components.PinVariant
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.room.location.AssetType

data class ShowLocationState(
    val customMapStyleUrl: AsyncData<String?>,
    val isLive: Boolean,
    val dialogState: LocationConstraintsDialogState,
    val locationShares: ImmutableList<LocationShareItem>,
    val focusedLocation: LocationShareItem?,
    val isTrackMyLocation: Boolean,
    val userLocationState: UserLocationState,
    val appName: String,
    val hideUserLocationPuck: Boolean,
    val eventSink: (ShowLocationEvent) -> Unit,
) {
    val isSheetDraggable = isLive && locationShares.isNotEmpty()
}

data class LocationShareItem(
    val userId: UserId,
    val displayName: String,
    val avatarData: AvatarData,
    val formattedTimestamp: String,
    val location: Location,
    val isLive: Boolean,
    val assetType: AssetType?,
    val isOwnUser: Boolean
) {
    val canStopSharing = isLive && isOwnUser
}

fun LocationShareItem.toMarkerData(): LocationMarkerData {
    val pinVariant = if (assetType == AssetType.PIN) {
        PinVariant.PinnedLocation
    } else {
        PinVariant.UserLocation(
            avatarData = avatarData,
            isLive = isLive,
        )
    }
    return LocationMarkerData(
        id = userId.value,
        location = location,
        variant = pinVariant,
    )
}
