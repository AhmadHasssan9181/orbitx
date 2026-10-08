/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.analytics

import im.vector.app.features.analytics.plan.ViewRoom
import tech.orbitweb.orbitx.libraries.matrix.api.room.BaseRoom

fun BaseRoom.toAnalyticsViewRoom(
    trigger: ViewRoom.Trigger? = null,
    selectedSpace: BaseRoom? = null,
    viaKeyboard: Boolean? = null,
): ViewRoom {
    val activeSpace = selectedSpace?.toActiveSpace() ?: ViewRoom.ActiveSpace.Home

    return ViewRoom(
        isDM = info().isDm,
        isSpace = info().isSpace,
        trigger = trigger,
        activeSpace = activeSpace,
        viaKeyboard = viaKeyboard
    )
}

private fun BaseRoom.toActiveSpace(): ViewRoom.ActiveSpace {
    return if (info().isPublic == true) ViewRoom.ActiveSpace.Public else ViewRoom.ActiveSpace.Private
}
