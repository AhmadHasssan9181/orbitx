/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.media

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import tech.orbitweb.orbitx.libraries.designsystem.icons.CompoundDrawables
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@Immutable
sealed class AvatarAction(
    @StringRes val titleResId: Int,
    @DrawableRes val iconResourceId: Int,
    val destructive: Boolean = false,
) {
    data object TakePhoto : AvatarAction(
        titleResId = CommonStrings.action_take_photo,
        iconResourceId = CompoundDrawables.ic_compound_take_photo,
    )

    data object ChoosePhoto : AvatarAction(
        titleResId = CommonStrings.action_choose_photo,
        iconResourceId = CompoundDrawables.ic_compound_image,
    )

    data object Remove : AvatarAction(
        titleResId = CommonStrings.action_remove,
        iconResourceId = CompoundDrawables.ic_compound_delete,
        destructive = true
    )
}
