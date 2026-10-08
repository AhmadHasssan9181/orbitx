/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.impl.utils

import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.features.call.impl.data.WidgetMessage
import tech.orbitweb.orbitx.libraries.androidutils.json.JsonProvider
import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions

@Inject
class WidgetMessageSerializer(
    private val json: JsonProvider,
) {
    fun deserialize(message: String): Result<WidgetMessage> {
        return runCatchingExceptions { json().decodeFromString(WidgetMessage.serializer(), message) }
    }

    fun serialize(message: WidgetMessage): String {
        return json().encodeToString(WidgetMessage.serializer(), message)
    }
}
