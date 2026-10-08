/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications.factories

import androidx.annotation.ColorInt
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import tech.orbitweb.orbitx.libraries.matrix.test.A_COLOR_INT
import tech.orbitweb.orbitx.libraries.matrix.ui.components.aMatrixUser

fun aNotificationAccountParams(
    user: MatrixUser = aMatrixUser(),
    @ColorInt color: Int = A_COLOR_INT,
    showSessionId: Boolean = false,
) = NotificationAccountParams(
    user = user,
    color = color,
    showSessionId = showSessionId,
)
