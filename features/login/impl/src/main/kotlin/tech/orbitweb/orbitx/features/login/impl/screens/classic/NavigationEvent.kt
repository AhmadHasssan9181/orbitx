/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.classic

import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId

sealed interface NavigationEvent {
    data object Idle : NavigationEvent
    data object NavigateToOnBoarding : NavigationEvent
    data class NavigateToLoginWithClassic(
        val userId: UserId,
    ) : NavigationEvent
}
