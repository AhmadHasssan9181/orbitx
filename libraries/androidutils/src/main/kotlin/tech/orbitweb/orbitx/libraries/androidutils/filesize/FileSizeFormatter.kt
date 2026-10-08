/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.androidutils.filesize

interface FileSizeFormatter {
    /**
     * Formats a content size to be in the form of bytes, kilobytes, megabytes, etc.
     */
    fun format(fileSize: Long, useShortFormat: Boolean = true): String
}
