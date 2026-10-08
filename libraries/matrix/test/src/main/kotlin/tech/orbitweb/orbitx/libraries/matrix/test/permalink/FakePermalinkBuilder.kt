/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.permalink

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.permalink.PermalinkBuilder
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakePermalinkBuilder(
    private val permalinkForUserLambda: (UserId) -> Result<String> = { lambdaError() },
    private val permalinkForRoomAliasLambda: (RoomAlias) -> Result<String> = { lambdaError() },
) : PermalinkBuilder {
    override fun permalinkForUser(userId: UserId): Result<String> {
        return permalinkForUserLambda(userId)
    }

    override fun permalinkForRoomAlias(roomAlias: RoomAlias): Result<String> {
        return permalinkForRoomAliasLambda(roomAlias)
    }
}
