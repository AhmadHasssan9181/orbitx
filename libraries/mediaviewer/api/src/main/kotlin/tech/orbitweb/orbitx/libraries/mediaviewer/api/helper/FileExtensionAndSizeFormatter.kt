/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.api.helper

fun formatFileExtensionAndSize(extension: String, size: String?): String {
    return buildString {
        append(extension.uppercase())
        if (size != null) {
            append(" • ")
            append(size)
        }
    }
}
