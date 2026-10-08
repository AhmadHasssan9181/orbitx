/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.widget

import java.util.UUID

interface CallWidgetSettingsProvider {
    suspend fun provide(
        baseUrl: String,
        widgetId: String = UUID.randomUUID().toString(),
        encrypted: Boolean,
        direct: Boolean,
        isAudioCall: Boolean,
        hasActiveCall: Boolean,
    ): MatrixWidgetSettings
}
