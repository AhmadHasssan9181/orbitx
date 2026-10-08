/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush

import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.libraries.androidutils.json.JsonProvider
import tech.orbitweb.orbitx.libraries.core.data.tryOrNull
import tech.orbitweb.orbitx.libraries.pushproviders.api.PushData

@Inject
class UnifiedPushParser(
    private val json: JsonProvider,
) {
    fun parse(message: ByteArray, clientSecret: String): PushData? {
        return tryOrNull { json().decodeFromString<PushDataUnifiedPush>(String(message)) }?.toPushData(clientSecret)
    }
}
