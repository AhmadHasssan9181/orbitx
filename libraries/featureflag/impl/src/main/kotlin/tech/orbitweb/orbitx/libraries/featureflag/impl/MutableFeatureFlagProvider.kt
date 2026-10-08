/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.featureflag.impl

import tech.orbitweb.orbitx.libraries.featureflag.api.Feature

interface MutableFeatureFlagProvider : FeatureFlagProvider {
    suspend fun setFeatureEnabled(feature: Feature, enabled: Boolean)
}
