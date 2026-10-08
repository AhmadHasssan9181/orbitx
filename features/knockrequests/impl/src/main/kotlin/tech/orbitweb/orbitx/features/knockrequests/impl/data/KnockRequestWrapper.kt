/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.knockrequests.impl.data

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.room.knock.KnockRequest

class KnockRequestWrapper(
    private val inner: KnockRequest,
    dateFormatter: (Long?) -> String? = { null }
) : KnockRequestPresentable {
    override val eventId: EventId = inner.eventId
    override val userId: UserId = inner.userId
    override val displayName: String? = inner.displayName
    override val avatarUrl: String? = inner.avatarUrl
    override val reason: String? = inner.reason?.trim()
    override val formattedDate: String? = dateFormatter(inner.timestamp)

    val isSeen: Boolean = inner.isSeen

    suspend fun accept(): Result<Unit> = inner.accept()

    suspend fun decline(reason: String?): Result<Unit> = inner.decline(reason)

    suspend fun declineAndBan(reason: String?): Result<Unit> = inner.declineAndBan(reason)

    suspend fun markAsSeen(): Result<Unit> = inner.markAsSeen()
}
