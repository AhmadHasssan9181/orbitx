/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.atomic.atoms

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Button
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@Composable
fun LoadingButtonAtom(
    modifier: Modifier = Modifier,
) = Button(
    modifier = modifier.fillMaxWidth(),
    enabled = false,
    showProgress = true,
    text = stringResource(CommonStrings.common_loading),
    onClick = {},
)
