/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.knockrequests.impl.banner

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.knockrequests.api.banner.KnockRequestsBannerRenderer
import tech.orbitweb.orbitx.libraries.di.RoomScope

@ContributesBinding(RoomScope::class)
class DefaultKnockRequestsBannerRenderer(
    private val presenter: KnockRequestsBannerPresenter,
) : KnockRequestsBannerRenderer {
    @Composable
    override fun View(modifier: Modifier, onViewRequestsClick: () -> Unit) {
        val state = presenter.present()
        KnockRequestsBannerView(
            state = state,
            onViewRequestsClick = onViewRequestsClick,
        )
    }
}
