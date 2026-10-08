/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room

import org.matrix.rustcomponents.sdk.RtcCallIntent
import org.matrix.rustcomponents.sdk.RtcCallIntentConsensus
import tech.orbitweb.orbitx.libraries.matrix.api.notification.CallIntent
import tech.orbitweb.orbitx.libraries.matrix.api.room.CallIntentConsensus

fun RtcCallIntentConsensus.map(): CallIntentConsensus = when (this) {
    is RtcCallIntentConsensus.Full -> CallIntentConsensus.Full(v1.map())
    is RtcCallIntentConsensus.Partial -> CallIntentConsensus.Partial(
        callIntent = intent.map(),
        agreeingCount = agreeingCount.toInt(),
        totalCount = totalCount.toInt()
    )
    RtcCallIntentConsensus.None -> CallIntentConsensus.None
}

fun RtcCallIntent.map(): CallIntent = when (this) {
    RtcCallIntent.VIDEO -> CallIntent.VIDEO
    RtcCallIntent.AUDIO -> CallIntent.AUDIO
}
