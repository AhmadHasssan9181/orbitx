/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.room.location

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.room.location.AssetType
import tech.orbitweb.orbitx.libraries.matrix.api.room.location.LastLocation
import tech.orbitweb.orbitx.libraries.matrix.api.room.location.LiveLocationShare
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID

fun aLiveLocationShare(
    beaconId: EventId = AN_EVENT_ID,
    userId: UserId = A_USER_ID,
    geoUri: String = "geo:48.8584,2.2945",
    timestamp: Long = 0L,
    startTimestamp: Long = 0L,
    endTimestamp: Long = Long.MAX_VALUE,
    assetType: AssetType = AssetType.SENDER,
): LiveLocationShare {
    return LiveLocationShare(
        beaconId = beaconId,
        userId = userId,
        lastLocation = LastLocation(
            geoUri = geoUri,
            timestamp = timestamp,
            assetType = assetType,
        ),
        startTimestamp = startTimestamp,
        endTimestamp = endTimestamp,
    )
}
