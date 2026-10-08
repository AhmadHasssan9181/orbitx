/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.protection

fun coerceRatioWhenHidingContent(aspectRatio: Float?, hideContent: Boolean): Float? {
    return if (hideContent) {
        aspectRatio?.coerceIn(
            minimumValue = 0.5f,
            maximumValue = 3f
        )
    } else {
        aspectRatio
    }
}
