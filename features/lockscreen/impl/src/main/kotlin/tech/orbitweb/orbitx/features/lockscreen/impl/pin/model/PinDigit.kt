/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.pin.model

import androidx.compose.runtime.Immutable

@Immutable
sealed interface PinDigit {
    data object Empty : PinDigit
    data class Filled(val value: Char) : PinDigit

    fun toText(): String {
        return when (this) {
            is Empty -> ""
            is Filled -> value.toString()
        }
    }
}
