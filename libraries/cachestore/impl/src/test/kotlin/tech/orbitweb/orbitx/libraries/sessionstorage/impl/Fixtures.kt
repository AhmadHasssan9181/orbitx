/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.sessionstorage.impl

import tech.orbitweb.orbitx.libraries.cachestore.CacheData
import java.util.Date

internal fun aCacheData(
    key: String = "aKey",
    value: String = "aValue",
    updatedAt: Date = Date(),
) = CacheData(
    key = key,
    value_ = value,
    updatedAt = updatedAt.time,
)
