/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.recentemojis.impl

import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.recentemojis.api.AddRecentEmoji
import kotlinx.coroutines.withContext

@ContributesBinding(SessionScope::class)
class DefaultAddRecentEmoji(
    private val client: MatrixClient,
    private val dispatchers: CoroutineDispatchers,
) : AddRecentEmoji {
    override suspend operator fun invoke(emoji: String): Result<Unit> = withContext(dispatchers.io) {
        client.addRecentEmoji(emoji)
    }
}
