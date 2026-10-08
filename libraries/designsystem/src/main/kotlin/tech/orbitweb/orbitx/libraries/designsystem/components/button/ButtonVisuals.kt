/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.components.button

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Button
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.IconButton
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.IconSource
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.TextButton

/**
 * A sealed interface that represents the different visual styles that a button can have.
 */
@Immutable
sealed interface ButtonVisuals {
    val action: () -> Unit

    /**
     * Creates a [Button] composable based on the visual state.
     */
    @Composable
    fun Composable()

    data class Text(val text: String, override val action: () -> Unit) : ButtonVisuals {
        @Composable
        override fun Composable() {
            TextButton(text = text, onClick = action)
        }
    }
    data class Icon(val iconSource: IconSource, override val action: () -> Unit) : ButtonVisuals {
        @Composable
        override fun Composable() {
            IconButton(onClick = action) {
                Icon(iconSource.getPainter(), iconSource.contentDescription)
            }
        }
    }
}
