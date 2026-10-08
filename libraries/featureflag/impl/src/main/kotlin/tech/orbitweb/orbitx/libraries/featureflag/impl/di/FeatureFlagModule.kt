/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.featureflag.impl.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ElementsIntoSet
import dev.zacsweers.metro.Provides
import tech.orbitweb.orbitx.libraries.featureflag.impl.FeatureFlagProvider
import tech.orbitweb.orbitx.libraries.featureflag.impl.PreferencesFeatureFlagProvider

@BindingContainer
@ContributesTo(AppScope::class)
object FeatureFlagModule {
    @JvmStatic
    @Provides
    @ElementsIntoSet
    fun providesFeatureFlagProvider(
        mutableFeatureFlagProvider: PreferencesFeatureFlagProvider,
    ): Set<FeatureFlagProvider> {
        return buildSet {
            add(mutableFeatureFlagProvider)
        }
    }
}
