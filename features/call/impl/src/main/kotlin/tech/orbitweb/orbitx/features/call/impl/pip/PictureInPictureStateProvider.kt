/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.impl.pip

fun aPictureInPictureState(
    supportPip: Boolean = false,
    isInPictureInPicture: Boolean = false,
    eventSink: (PictureInPictureEvent) -> Unit = {},
): PictureInPictureState {
    return PictureInPictureState(
        supportPip = supportPip,
        isInPictureInPicture = isInPictureInPicture,
        eventSink = eventSink,
    )
}
