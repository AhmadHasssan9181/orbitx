/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.androidutils.json

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import kotlinx.serialization.json.Json

/**
 * Provides a Json instance configured to ignore unknown keys.
 */
fun interface JsonProvider {
    operator fun invoke(): Json
}

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class DefaultJsonProvider : JsonProvider {
    private val json: Json by lazy {
        Json {
            ignoreUnknownKeys = true
            allowComments = true
            allowTrailingComma = true
        }
    }

    override fun invoke() = json
}
