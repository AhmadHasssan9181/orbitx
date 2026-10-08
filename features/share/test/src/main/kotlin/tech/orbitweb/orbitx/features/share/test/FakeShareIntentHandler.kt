/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.share.test

import android.content.Intent
import tech.orbitweb.orbitx.features.share.api.ShareIntentData
import tech.orbitweb.orbitx.features.share.api.ShareIntentHandler

class FakeShareIntentHandler(
    private val onIncomingShareIntent: (Intent) -> ShareIntentData? = { null },
) : ShareIntentHandler {
    override fun handleIncomingShareIntent(
        intent: Intent,
    ): ShareIntentData? {
        return onIncomingShareIntent(intent)
    }
}
