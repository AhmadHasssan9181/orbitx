/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.analytics.noop

import androidx.compose.runtime.Composable
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import im.vector.app.features.analytics.plan.MobileScreen
import tech.orbitweb.orbitx.services.analytics.api.ScreenTracker

@ContributesBinding(AppScope::class)
class NoopScreenTracker : ScreenTracker {
    @Composable
    override fun TrackScreen(screen: MobileScreen.ScreenName) = Unit
}
