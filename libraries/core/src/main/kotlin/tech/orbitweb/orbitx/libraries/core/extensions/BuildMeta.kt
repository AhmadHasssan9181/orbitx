/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.core.extensions

import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta
import tech.orbitweb.orbitx.libraries.core.meta.BuildType

fun BuildMeta.isElement(): Boolean {
    return when (buildType) {
        BuildType.RELEASE -> applicationId == "tech.orbitweb.orbitx"
        BuildType.NIGHTLY -> applicationId == "tech.orbitweb.orbitx.nightly"
        BuildType.DEBUG -> applicationId == "tech.orbitweb.orbitx.debug"
    }
}
