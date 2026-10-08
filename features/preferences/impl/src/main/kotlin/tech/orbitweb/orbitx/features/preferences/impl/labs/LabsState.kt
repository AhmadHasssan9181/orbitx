/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.labs

import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.libraries.featureflag.ui.model.FeatureUiModel

data class LabsState(
    val features: ImmutableList<FeatureUiModel>,
    val isApplyingChanges: Boolean,
    val eventSink: (LabsEvents) -> Unit,
)
