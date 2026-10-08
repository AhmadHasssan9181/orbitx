/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room.location

import tech.orbitweb.orbitx.libraries.matrix.api.room.location.AssetType
import org.matrix.rustcomponents.sdk.AssetType as RustAssetType

fun AssetType.into(): RustAssetType = when (this) {
    AssetType.SENDER -> RustAssetType.SENDER
    AssetType.PIN -> RustAssetType.PIN
    AssetType.UNKNOWN -> RustAssetType.UNKNOWN
}

fun RustAssetType.into(): AssetType = when (this) {
    RustAssetType.SENDER -> AssetType.SENDER
    RustAssetType.PIN -> AssetType.PIN
    RustAssetType.UNKNOWN -> AssetType.UNKNOWN
}
