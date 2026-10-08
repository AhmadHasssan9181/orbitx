/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.components

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_ALICE
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_BOB
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_CAROL
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_DAVID
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_EVE
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_JOHN_DOE
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_JUSTIN
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_MALLORY
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_SUSIE
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_VICTOR
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_WALTER
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

open class MatrixUserProvider : PreviewParameterProvider<MatrixUser> {
    override val values: Sequence<MatrixUser>
        get() = sequenceOf(
            aMatrixUser(),
            aMatrixUser(displayName = null),
        )
}

open class MatrixUserWithAvatarProvider : PreviewParameterProvider<MatrixUser?> {
    override val values: Sequence<MatrixUser?>
        get() = sequenceOf(
            aMatrixUser(displayName = USER_NAME_JOHN_DOE),
            aMatrixUser(displayName = USER_NAME_JOHN_DOE, avatarUrl = "anUrl"),
        )
}

fun aMatrixUser(
    id: String? = null,
    displayName: String? = USER_NAME_ALICE,
    avatarUrl: String? = null,
) = MatrixUser(
    userId = UserId(id ?: "@${displayName?.lowercase()?.replace(" ", "_") ?: "id"}:server.org"),
    displayName = displayName,
    avatarUrl = avatarUrl,
)

fun aMatrixUserList() = listOf(
    aMatrixUser(displayName = USER_NAME_ALICE),
    aMatrixUser(displayName = USER_NAME_BOB),
    aMatrixUser(displayName = USER_NAME_CAROL),
    aMatrixUser(displayName = USER_NAME_DAVID),
    aMatrixUser(displayName = USER_NAME_EVE),
    aMatrixUser(displayName = USER_NAME_JUSTIN),
    aMatrixUser(displayName = USER_NAME_MALLORY),
    aMatrixUser(displayName = USER_NAME_SUSIE),
    aMatrixUser(displayName = USER_NAME_VICTOR),
    aMatrixUser(displayName = USER_NAME_WALTER),
)
