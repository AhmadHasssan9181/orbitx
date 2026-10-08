/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.timeline.item.event

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.TransactionId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventOrTransactionId
import org.matrix.rustcomponents.sdk.EventOrTransactionId as RustEventOrTransactionId

fun RustEventOrTransactionId.map(): EventOrTransactionId = when (this) {
    is RustEventOrTransactionId.EventId -> EventOrTransactionId.Event(EventId(eventId))
    is RustEventOrTransactionId.TransactionId -> EventOrTransactionId.Transaction(TransactionId(transactionId))
}
