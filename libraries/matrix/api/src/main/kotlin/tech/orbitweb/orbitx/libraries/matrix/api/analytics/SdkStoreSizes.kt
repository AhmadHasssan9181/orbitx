/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.analytics

import tech.orbitweb.orbitx.libraries.core.data.ByteSize

/**
 * The sizes of the different stores (DBs) in the SDK.
 */
data class SdkStoreSizes(
    val stateStore: ByteSize?,
    val eventCacheStore: ByteSize?,
    val mediaStore: ByteSize?,
    val cryptoStore: ByteSize?,
)
