/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rageshake.impl.reporter

fun interface BugReporterMultipartBodyListener {
    /**
     * Upload listener.
     *
     * @param totalWritten  total written bytes
     * @param contentLength content length
     */
    fun onWrite(totalWritten: Long, contentLength: Long)
}
