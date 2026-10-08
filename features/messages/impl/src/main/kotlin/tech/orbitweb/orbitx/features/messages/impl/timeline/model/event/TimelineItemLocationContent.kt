/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.model.event

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberUpdatedState
import tech.orbitweb.orbitx.features.location.api.Location
import tech.orbitweb.orbitx.libraries.designsystem.components.PinVariant
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarSize
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.room.location.AssetType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ProfileDetails
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.getAvatarUrl
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.getDisplayName
import kotlinx.coroutines.delay

data class TimelineItemLocationContent(
    val senderId: UserId,
    val senderProfile: ProfileDetails,
    val description: String? = null,
    val assetType: AssetType? = null,
    val mode: Mode,
) : TimelineItemEventContent {
    val location = when (mode) {
        is Mode.Live -> mode.lastKnownLocation
        is Mode.Static -> mode.location
    }

    /**
     * The pin variant to display on the map.
     * Returns a default variant when location is null (map will show loading placeholder anyway).
     */
    val pinVariant: PinVariant = when (mode) {
        is Mode.Live -> {
            if (mode.isActive) {
                PinVariant.UserLocation(avatarData = senderAvatar(), isLive = true)
            } else {
                PinVariant.StaleLocation
            }
        }
        is Mode.Static -> {
            when (assetType) {
                AssetType.PIN -> PinVariant.PinnedLocation
                AssetType.SENDER,
                AssetType.UNKNOWN,
                null -> PinVariant.UserLocation(avatarData = senderAvatar(), isLive = false)
            }
        }
    }

    private fun senderAvatar() = AvatarData(
        senderId.value,
        name = senderProfile.getDisplayName(),
        url = senderProfile.getAvatarUrl(),
        size = AvatarSize.LocationPin
    )

    sealed interface Mode {
        data class Static(
            val location: Location,
        ) : Mode

        data class Live(
            val lastKnownLocation: Location?,
            val isActive: Boolean,
            val endsAt: String,
            val endTimestamp: Long,
            val isOwnUser: Boolean,
        ) : Mode {
            val isLoading = lastKnownLocation == null && isActive
            val canStopSharing = isActive && isOwnUser
        }
    }

    override val type: String = "TimelineItemLocationContent"
}

/**
 * Overrides the isActive value if needed, to make sure endTimestamp is used in absence of stop event.
 */
@Composable
internal fun TimelineItemLocationContent.ensureActiveLiveLocation(
    currentTimeMillis: () -> Long = System::currentTimeMillis,
): TimelineItemLocationContent {
    return when (mode) {
        is TimelineItemLocationContent.Mode.Live -> {
            val isActive = rememberIsLiveLocationActive(mode, currentTimeMillis)
            copy(mode = mode.copy(isActive = isActive))
        }
        is TimelineItemLocationContent.Mode.Static -> this
    }
}

@Composable
private fun rememberIsLiveLocationActive(
    mode: TimelineItemLocationContent.Mode.Live,
    currentTimeMillis: () -> Long,
): Boolean {
    val updatedCurrentTimeMillis by rememberUpdatedState(currentTimeMillis)
    fun TimelineItemLocationContent.Mode.Live.isActive(): Boolean {
        return isActive && endTimestamp > updatedCurrentTimeMillis()
    }
    return produceState(
        initialValue = mode.isActive(),
        key1 = mode.endTimestamp,
        key2 = mode.isActive,
    ) {
        if (mode.isActive) {
            val remainingMillis = mode.endTimestamp - updatedCurrentTimeMillis()
            delay(remainingMillis)
        }
        value = false
    }.value
}
