/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.utils

import tech.orbitweb.orbitx.features.call.impl.utils.CallWidgetProvider
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.test.widget.FakeMatrixWidgetDriver

class FakeCallWidgetProvider(
    private val widgetDriver: FakeMatrixWidgetDriver = FakeMatrixWidgetDriver(),
    private val url: String = "https://call.element.io",
) : CallWidgetProvider {
    var getWidgetCalled = false
        private set

    override suspend fun getWidget(
        sessionId: SessionId,
        roomId: RoomId,
        isAudioCall: Boolean,
        clientId: String,
        languageTag: String?,
        theme: String?
    ): Result<CallWidgetProvider.GetWidgetResult> {
        getWidgetCalled = true
        return Result.success(
            CallWidgetProvider.GetWidgetResult(
                driver = widgetDriver,
                url = url,
            )
        )
    }
}
