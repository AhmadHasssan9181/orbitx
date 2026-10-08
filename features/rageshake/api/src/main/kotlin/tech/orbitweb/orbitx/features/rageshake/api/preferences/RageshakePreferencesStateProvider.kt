/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rageshake.api.preferences

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

open class RageshakePreferencesStateProvider : PreviewParameterProvider<RageshakePreferencesState> {
    override val values: Sequence<RageshakePreferencesState>
        get() = sequenceOf(
            aRageshakePreferencesState(isEnabled = true, isSupported = true, sensitivity = 0.5f),
            aRageshakePreferencesState(isEnabled = true, isSupported = false, sensitivity = 0.5f),
        )
}

fun aRageshakePreferencesState(
    isFeatureEnabled: Boolean = true,
    isEnabled: Boolean = false,
    isSupported: Boolean = true,
    sensitivity: Float = 0.3f,
    eventSink: (RageshakePreferencesEvent) -> Unit = {}
) = RageshakePreferencesState(
    isFeatureEnabled = isFeatureEnabled,
    isEnabled = isEnabled,
    isSupported = isSupported,
    sensitivity = sensitivity,
    eventSink = eventSink,
)
