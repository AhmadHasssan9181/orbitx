/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.impl.pip

import tech.orbitweb.orbitx.features.call.impl.utils.PipController

sealed interface PictureInPictureEvent {
    data class SetPipController(val pipController: PipController) : PictureInPictureEvent
    data object EnterPictureInPicture : PictureInPictureEvent
    data class OnPictureInPictureModeChanged(val isInPip: Boolean) : PictureInPictureEvent
}
