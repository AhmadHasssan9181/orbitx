/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.atomic.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.libraries.designsystem.atomic.molecules.NumberedListMolecule

@Composable
fun NumberedListOrganism(
    items: ImmutableList<AnnotatedString>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        itemsIndexed(items) { index, item ->
            NumberedListMolecule(index = index + 1, text = item)
        }
    }
}
