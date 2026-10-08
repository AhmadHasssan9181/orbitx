/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.featureflag.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.featureflag.api.Feature
import tech.orbitweb.orbitx.libraries.featureflag.api.FeatureFlags

fun interface FeaturesProvider {
    fun provide(): List<Feature>
}

@ContributesBinding(AppScope::class)
class DefaultFeaturesProvider : FeaturesProvider {
    override fun provide(): List<Feature> = FeatureFlags.entries
}
