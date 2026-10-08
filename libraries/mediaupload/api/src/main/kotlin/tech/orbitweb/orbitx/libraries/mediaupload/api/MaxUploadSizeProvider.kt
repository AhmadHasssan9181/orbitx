/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaupload.api

/**
 * Provides the maximum upload size allowed by the Matrix server.
 */
fun interface MaxUploadSizeProvider {
    suspend fun getMaxUploadSize(): Result<Long>
}
