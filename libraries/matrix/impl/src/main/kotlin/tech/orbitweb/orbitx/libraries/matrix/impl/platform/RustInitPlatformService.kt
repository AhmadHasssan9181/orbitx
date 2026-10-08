/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.platform

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import org.matrix.rustcomponents.sdk.initPlatform
import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta
import tech.orbitweb.orbitx.libraries.matrix.api.platform.InitPlatformService
import tech.orbitweb.orbitx.libraries.matrix.api.tracing.TracingConfiguration
import tech.orbitweb.orbitx.libraries.matrix.impl.tracing.map

@ContributesBinding(AppScope::class)
class RustInitPlatformService(
    private val buildMeta: BuildMeta,
) : InitPlatformService {
    override fun init(tracingConfiguration: TracingConfiguration) {
        initPlatform(
            config = tracingConfiguration.map(buildMeta),
            useLightweightTokioRuntime = false
        )
    }
}
