/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import org.matrix.rustcomponents.sdk.sdkGitSha
import tech.orbitweb.orbitx.libraries.matrix.api.SdkMetadata

@ContributesBinding(AppScope::class)
class RustSdkMetadata : SdkMetadata {
    override val sdkGitSha: String
        get() = sdkGitSha()
}
