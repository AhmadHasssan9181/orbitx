/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.cachecleaner.api

interface CacheCleaner {
    /**
     * Clear the cache subdirs holding temporarily decrypted content (such as media and voice messages).
     *
     * Will fail silently in case of errors while deleting the files.
     */
    fun clearCache()
}
