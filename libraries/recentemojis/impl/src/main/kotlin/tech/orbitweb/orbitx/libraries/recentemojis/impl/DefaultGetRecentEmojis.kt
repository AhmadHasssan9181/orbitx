/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.recentemojis.impl

import dev.zacsweers.metro.ContributesBinding
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.withContext
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.recentemojis.api.EmojibaseProvider
import tech.orbitweb.orbitx.libraries.recentemojis.api.GetRecentEmojis

@ContributesBinding(SessionScope::class)
class DefaultGetRecentEmojis(
    private val client: MatrixClient,
    private val dispatchers: CoroutineDispatchers,
    private val emojibaseProvider: EmojibaseProvider,
) : GetRecentEmojis {
    override suspend operator fun invoke(): Result<ImmutableList<String>> = withContext(dispatchers.io) {
        val allEmojis = emojibaseProvider.emojibaseStore.allEmojis
        client.getRecentEmojis()
            .map { emojis ->
                // Remove any possible duplicates
                emojis.distinct()
                    // Return only those emojis that are valid
                    .filter { recent -> allEmojis.any { recent == it.unicode } }
                    .toImmutableList()
            }
    }
}
