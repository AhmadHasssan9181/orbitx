/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.impl.share

import tech.orbitweb.orbitx.features.location.api.Location
import kotlin.time.Duration

sealed interface ShareLocationEvent {
    data class ShareStaticLocation(
        val location: Location,
        val isPinned: Boolean,
    ) : ShareLocationEvent

    data object InitiateLiveLocationShare : ShareLocationEvent
    data object AcceptLiveLocationDisclaimer : ShareLocationEvent
    data class StartLiveLocationShare(val duration: Duration) : ShareLocationEvent

    data object StartTrackingUserLocation : ShareLocationEvent
    data object StopTrackingUserLocation : ShareLocationEvent
    data object DismissDialog : ShareLocationEvent

    data object RequestPermissions : ShareLocationEvent
    data object OpenAppSettings : ShareLocationEvent
    data object OpenLocationSettings : ShareLocationEvent
}
