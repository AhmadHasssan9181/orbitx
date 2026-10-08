/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.recentemojis.api

import kotlinx.collections.immutable.ImmutableList

/**
 * Returns the list of recently used emojis for reactions.
 */
fun interface GetRecentEmojis {
    suspend operator fun invoke(): Result<ImmutableList<String>>
}
