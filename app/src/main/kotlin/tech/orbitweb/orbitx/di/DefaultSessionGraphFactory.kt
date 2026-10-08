/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.appnav.di.SessionGraphFactory
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient

@ContributesBinding(AppScope::class)
class DefaultSessionGraphFactory(
    private val appGraph: AppGraph
) : SessionGraphFactory {
    override fun create(client: MatrixClient): Any {
        return appGraph.sessionGraphFactory.create(client)
    }
}
