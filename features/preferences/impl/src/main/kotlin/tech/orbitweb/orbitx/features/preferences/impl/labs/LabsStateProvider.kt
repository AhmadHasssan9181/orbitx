/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.labs

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.libraries.designsystem.icons.CompoundDrawables
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.IconSource
import tech.orbitweb.orbitx.libraries.featureflag.ui.model.FeatureUiModel

internal class LabsStateProvider : PreviewParameterProvider<LabsState> {
    override val values: Sequence<LabsState>
        get() = sequenceOf(
            aLabsState(features = aFeatureList()),
            aLabsState(features = aFeatureList(), isApplyingChanges = true),
        )
}

internal fun aLabsState(
    features: List<FeatureUiModel> = emptyList(),
    isApplyingChanges: Boolean = false,
) = LabsState(
    features = features.toImmutableList(),
    isApplyingChanges = isApplyingChanges,
    eventSink = {},
)

internal fun aFeatureList() = listOf(
    FeatureUiModel(
        key = "feature_1",
        title = "Feature 1",
        description = "This is a description of feature 1.",
        isEnabled = true,
        icon = IconSource.Resource(CompoundDrawables.ic_compound_threads),
    ),
    FeatureUiModel(
        key = "feature_2",
        title = "Feature 2",
        description = "This is a description of feature 2.",
        isEnabled = false,
        icon = IconSource.Resource(CompoundDrawables.ic_compound_video_call),
    )
)
