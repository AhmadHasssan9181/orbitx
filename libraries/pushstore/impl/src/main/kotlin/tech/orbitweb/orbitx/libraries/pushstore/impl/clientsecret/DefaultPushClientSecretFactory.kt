/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushstore.impl.clientsecret

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.pushstore.api.clientsecret.PushClientSecretFactory
import java.util.UUID

@ContributesBinding(AppScope::class)
class DefaultPushClientSecretFactory : PushClientSecretFactory {
    override fun create(): String {
        return UUID.randomUUID().toString()
    }
}
