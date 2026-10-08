/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.user.editprofile

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_JOHN_DOE
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.ui.media.AvatarAction
import tech.orbitweb.orbitx.libraries.permissions.api.PermissionsState
import tech.orbitweb.orbitx.libraries.permissions.api.aPermissionsState
import kotlinx.collections.immutable.toImmutableList

open class EditUserProfileStateProvider : PreviewParameterProvider<EditUserProfileState> {
    override val values: Sequence<EditUserProfileState>
        get() = sequenceOf(
            aEditUserProfileState(),
            aEditUserProfileState(userAvatarUrl = "example://uri"),
            aEditUserProfileState(saveAction = AsyncAction.ConfirmingCancellation),
            aEditUserProfileState(canChangeAvatarUrl = false, canChangeDisplayName = false),
        )
}

fun aEditUserProfileState(
    userId: UserId = UserId("@john.doe:matrix.org"),
    displayName: String = USER_NAME_JOHN_DOE,
    userAvatarUrl: String? = null,
    avatarActions: List<AvatarAction> = emptyList(),
    saveButtonEnabled: Boolean = true,
    saveAction: AsyncAction<Unit> = AsyncAction.Uninitialized,
    cameraPermissionState: PermissionsState = aPermissionsState(showDialog = false),
    canChangeDisplayName: Boolean = true,
    canChangeAvatarUrl: Boolean = true,
    eventSink: (EditUserProfileEvent) -> Unit = {},
) = EditUserProfileState(
    userId = userId,
    displayName = displayName,
    userAvatarUrl = userAvatarUrl,
    avatarActions = avatarActions.toImmutableList(),
    saveButtonEnabled = saveButtonEnabled,
    saveAction = saveAction,
    cameraPermissionState = cameraPermissionState,
    canChangeDisplayName = canChangeDisplayName,
    canChangeAvatarUrl = canChangeAvatarUrl,
    eventSink = eventSink,
)
