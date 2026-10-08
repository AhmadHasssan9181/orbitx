/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.atomic.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text

/**
 * Display a label and a text in a column.
 * @param label the label to display
 * @param text the text to display
 * @param modifier the modifier to apply to this layout
 * @param spellText if true, the text will be spelled out in the content description for accessibility.
 * Useful for deviceId for instance, that the screen reader will read as a list of letters instead of trying to read a
 * word of random characters.
 */
@Composable
fun TextWithLabelMolecule(
    label: String,
    text: String,
    modifier: Modifier = Modifier,
    spellText: Boolean = false,
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = ElementTheme.typography.fontBodySmRegular,
            color = ElementTheme.colors.textSecondary,
        )
        Text(
            modifier = Modifier.semantics {
                if (spellText) {
                    contentDescription = text.toList().joinToString()
                }
            },
            text = text,
            style = ElementTheme.typography.fontBodyMdRegular,
            color = ElementTheme.colors.textPrimary,
        )
    }
}
