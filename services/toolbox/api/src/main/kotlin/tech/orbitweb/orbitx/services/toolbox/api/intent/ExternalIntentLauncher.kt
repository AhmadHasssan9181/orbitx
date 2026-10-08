/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.toolbox.api.intent

import android.content.Intent

/**
 * Used to launch external intents from anywhere in the app.
 */
interface ExternalIntentLauncher {
    fun launch(intent: Intent)
}
