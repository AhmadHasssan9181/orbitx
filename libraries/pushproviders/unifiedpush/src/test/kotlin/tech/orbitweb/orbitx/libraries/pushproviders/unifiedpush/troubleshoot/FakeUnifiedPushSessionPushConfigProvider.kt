/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush.troubleshoot

import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.pushproviders.api.Config
import tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush.UnifiedPushSessionPushConfigProvider
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeUnifiedPushSessionPushConfigProvider(
    private val config: (SessionId) -> Config? = { lambdaError() },
) : UnifiedPushSessionPushConfigProvider {
    override suspend fun provide(sessionId: SessionId): Config? {
        return config(sessionId)
    }
}
