/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.GraphExtension
import tech.orbitweb.orbitx.libraries.architecture.NodeFactoriesBindings

@GraphExtension(QrCodeLoginScope::class)
interface QrCodeLoginGraph : NodeFactoriesBindings {
    @ContributesTo(AppScope::class)
    @GraphExtension.Factory
    interface Factory {
        fun create(): QrCodeLoginGraph
    }
}
