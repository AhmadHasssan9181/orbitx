/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.featureflag.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.libraries.designsystem.components.preferences.PreferenceCheckbox
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.featureflag.ui.model.FeatureUiModel
import tech.orbitweb.orbitx.libraries.featureflag.ui.model.aFeatureUiModelList

@Composable
fun FeatureListView(
    features: ImmutableList<FeatureUiModel>,
    onCheckedChange: (FeatureUiModel, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        features.forEach { feature ->
            fun onCheckedChange(isChecked: Boolean) {
                onCheckedChange(feature, isChecked)
            }

            FeaturePreferenceView(feature = feature, onCheckedChange = ::onCheckedChange)
        }
    }
}

@Composable
private fun FeaturePreferenceView(
    feature: FeatureUiModel,
    onCheckedChange: (Boolean) -> Unit,
) {
    PreferenceCheckbox(
        title = feature.title,
        supportingText = feature.description,
        isChecked = feature.isEnabled,
        onCheckedChange = onCheckedChange
    )
}

@PreviewsDayNight
@Composable
internal fun FeatureListViewPreview() = ElementPreview {
    FeatureListView(
        features = aFeatureUiModelList(),
        onCheckedChange = { _, _ -> }
    )
}
