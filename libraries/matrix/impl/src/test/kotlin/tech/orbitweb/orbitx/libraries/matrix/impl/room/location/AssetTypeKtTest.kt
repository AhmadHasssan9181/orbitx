/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room.location

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.room.location.AssetType

class AssetTypeKtTest {
    @Test
    fun toInner() {
        assertThat(AssetType.SENDER.into()).isEqualTo(org.matrix.rustcomponents.sdk.AssetType.SENDER)
        assertThat(AssetType.PIN.into()).isEqualTo(org.matrix.rustcomponents.sdk.AssetType.PIN)
    }
}
