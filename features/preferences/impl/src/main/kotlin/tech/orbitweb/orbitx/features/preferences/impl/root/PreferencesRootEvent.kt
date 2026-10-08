/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.root

import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

sealed interface PreferencesRootEvent {
    data object OnVersionInfoClick : PreferencesRootEvent
    data class SwitchToSession(val sessionId: SessionId) : PreferencesRootEvent
}
