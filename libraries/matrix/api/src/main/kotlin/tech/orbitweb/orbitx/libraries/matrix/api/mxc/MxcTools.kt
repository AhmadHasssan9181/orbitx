/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.mxc

interface MxcTools {
    /**
     * Sanitizes an mxcUri to be used as a relative file path.
     *
     * @param mxcUri the Matrix Content (mxc://) URI of the file.
     * @return the relative file path as "<server-name>/<media-id>" or null if the mxcUri is invalid.
     */
    fun mxcUri2FilePath(mxcUri: String): String?
}

/**
 * "mxc" scheme, including "://". So "mxc://".
 */
const val MATRIX_CONTENT_URI_SCHEME = "mxc://"

/**
 * Return true if the String starts with "mxc://".
 */
fun String.isMxcUrl() = startsWith(MATRIX_CONTENT_URI_SCHEME)

/**
 * Remove the "mxc://" prefix. No op if the String is not a Mxc URL.
 */
fun String.removeMxcPrefix() = removePrefix(MATRIX_CONTENT_URI_SCHEME)
