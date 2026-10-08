/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.androidutils.clipboard

/**
 * Wrapper class for handling clipboard operations so it can be used in JVM environments.
 */
interface ClipboardHelper {
    fun copyPlainText(text: String)
}
