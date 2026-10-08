/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.accountselect.impl

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import tech.orbitweb.orbitx.libraries.matrix.ui.components.aMatrixUserList

open class AccountSelectStateProvider : PreviewParameterProvider<AccountSelectState> {
    override val values: Sequence<AccountSelectState>
        get() = sequenceOf(
            anAccountSelectState(),
            anAccountSelectState(accounts = aMatrixUserList()),
        )
}

private fun anAccountSelectState(
    accounts: List<MatrixUser> = listOf(),
) = AccountSelectState(
    accounts = accounts.toImmutableList(),
)
