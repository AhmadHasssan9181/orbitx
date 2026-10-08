/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.user.editprofile

import tech.orbitweb.orbitx.libraries.matrix.ui.media.AvatarAction

sealed interface EditUserProfileEvent {
    data class HandleAvatarAction(val action: AvatarAction) : EditUserProfileEvent
    data class UpdateDisplayName(val name: String) : EditUserProfileEvent
    data object Exit : EditUserProfileEvent
    data object Save : EditUserProfileEvent
    data object CloseDialog : EditUserProfileEvent
}
