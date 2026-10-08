/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushstore.test.userpushstore

import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.pushstore.api.UserPushStore
import tech.orbitweb.orbitx.libraries.pushstore.api.UserPushStoreFactory

class FakeUserPushStoreFactory(
    val userPushStore: (SessionId) -> UserPushStore = { FakeUserPushStore() }
) : UserPushStoreFactory {
    override fun getOrCreate(userId: SessionId): UserPushStore {
        return userPushStore(userId)
    }
}
