/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */
package tech.orbitweb.orbitx.libraries.matrix.impl.timeline

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.ReceiptType
import org.matrix.rustcomponents.sdk.ReceiptType as RustReceiptType

class ReceiptTypeMapperKtTest {
    @Test
    fun toRustReceiptType() {
        assertThat(ReceiptType.READ.toRustReceiptType()).isEqualTo(RustReceiptType.READ)
        assertThat(ReceiptType.READ_PRIVATE.toRustReceiptType()).isEqualTo(RustReceiptType.READ_PRIVATE)
        assertThat(ReceiptType.FULLY_READ.toRustReceiptType()).isEqualTo(RustReceiptType.FULLY_READ)
    }
}
