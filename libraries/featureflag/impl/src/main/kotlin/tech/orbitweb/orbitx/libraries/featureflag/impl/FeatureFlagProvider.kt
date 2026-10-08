/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.featureflag.impl

import tech.orbitweb.orbitx.libraries.featureflag.api.Feature
import kotlinx.coroutines.flow.Flow

interface FeatureFlagProvider {
    val priority: Int
    fun isFeatureEnabledFlow(feature: Feature): Flow<Boolean>
    fun hasFeature(feature: Feature): Boolean
}

const val LOW_PRIORITY = 0
const val MEDIUM_PRIORITY = 1
const val HIGH_PRIORITY = 2
