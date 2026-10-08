/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.TransactionId
import tech.orbitweb.orbitx.libraries.matrix.api.room.SendQueueUpdate
import tech.orbitweb.orbitx.libraries.matrix.impl.media.map
import org.matrix.rustcomponents.sdk.RoomSendQueueUpdate

fun RoomSendQueueUpdate.map(): SendQueueUpdate = when (this) {
    is RoomSendQueueUpdate.NewLocalEvent -> SendQueueUpdate.NewLocalEvent(TransactionId(transactionId))
    is RoomSendQueueUpdate.CancelledLocalEvent -> SendQueueUpdate.CancelledLocalEvent(TransactionId(transactionId))
    is RoomSendQueueUpdate.MediaUpload -> SendQueueUpdate.MediaUpload(
        relatedTo = TransactionId(relatedTo),
        file = file?.map(),
        index = index.toLong(),
        progress = progress.current.toFloat() / progress.total.toFloat(),
    )
    is RoomSendQueueUpdate.ReplacedLocalEvent -> SendQueueUpdate.ReplacedLocalEvent(TransactionId(transactionId))
    is RoomSendQueueUpdate.RetryEvent -> SendQueueUpdate.RetrySendingEvent(TransactionId(transactionId))
    is RoomSendQueueUpdate.SendError -> SendQueueUpdate.SendError(TransactionId(transactionId))
    is RoomSendQueueUpdate.SentEvent -> SendQueueUpdate.SentEvent(TransactionId(transactionId), EventId(eventId))
}
