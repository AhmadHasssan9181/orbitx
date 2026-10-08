/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.widget

import tech.orbitweb.orbitx.libraries.matrix.api.widget.CallWidgetSettingsProvider
import tech.orbitweb.orbitx.libraries.matrix.api.widget.MatrixWidgetSettings

class FakeCallWidgetSettingsProvider(
    private val provideFn: (
        String,
        String,
        Boolean,
        Boolean,
        Boolean,
        Boolean
    ) -> MatrixWidgetSettings = { _, _, _, _, _, _ -> MatrixWidgetSettings("id", true, "url") }
) : CallWidgetSettingsProvider {
    val providedBaseUrls = mutableListOf<String>()

    override suspend fun provide(
        baseUrl: String,
        widgetId: String,
        encrypted: Boolean,
        direct: Boolean,
        isAudioCall: Boolean,
        hasActiveCall: Boolean
    ): MatrixWidgetSettings {
        providedBaseUrls += baseUrl
        return provideFn(baseUrl, widgetId, encrypted, direct, isAudioCall, hasActiveCall)
    }
}
