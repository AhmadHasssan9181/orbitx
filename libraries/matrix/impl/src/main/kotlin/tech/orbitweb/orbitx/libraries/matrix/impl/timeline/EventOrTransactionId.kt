/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.timeline

import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventOrTransactionId
import org.matrix.rustcomponents.sdk.EventOrTransactionId as RustEventOrTransactionId

fun EventOrTransactionId.toRustEventOrTransactionId() = when (this) {
    is EventOrTransactionId.Event -> RustEventOrTransactionId.EventId(id.value)
    is EventOrTransactionId.Transaction -> RustEventOrTransactionId.TransactionId(id.value)
}
