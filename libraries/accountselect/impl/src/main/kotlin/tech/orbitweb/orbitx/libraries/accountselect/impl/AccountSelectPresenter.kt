/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.accountselect.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import dev.zacsweers.metro.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import tech.orbitweb.orbitx.libraries.sessionstorage.api.SessionStore

@Inject
class AccountSelectPresenter(
    private val sessionStore: SessionStore,
) : Presenter<AccountSelectState> {
    @Composable
    override fun present(): AccountSelectState {
        val accounts by produceState<ImmutableList<MatrixUser>>(persistentListOf()) {
            // Do not use sessionStore.sessionsFlow() to not make it change when an account is selected.
            value = sessionStore.getAllSessions()
                .map {
                    MatrixUser(
                        userId = UserId(it.userId),
                        displayName = it.userDisplayName,
                        avatarUrl = it.userAvatarUrl,
                    )
                }
                .toImmutableList()
        }

        return AccountSelectState(
            accounts = accounts,
        )
    }
}
