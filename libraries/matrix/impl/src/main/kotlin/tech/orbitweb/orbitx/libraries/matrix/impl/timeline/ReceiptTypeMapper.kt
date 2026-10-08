/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.timeline

import tech.orbitweb.orbitx.libraries.matrix.api.timeline.ReceiptType
import org.matrix.rustcomponents.sdk.ReceiptType as RustReceiptType

internal fun ReceiptType.toRustReceiptType(): RustReceiptType = when (this) {
    ReceiptType.READ -> RustReceiptType.READ
    ReceiptType.READ_PRIVATE -> RustReceiptType.READ_PRIVATE
    ReceiptType.FULLY_READ -> RustReceiptType.FULLY_READ
}
