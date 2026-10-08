/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.featureflag.ui.model

import tech.orbitweb.orbitx.libraries.designsystem.theme.components.IconSource

data class FeatureUiModel(
    val key: String,
    val title: String,
    val description: String?,
    val icon: IconSource?,
    val isEnabled: Boolean
)
