/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.sync

import tech.orbitweb.orbitx.libraries.matrix.api.sync.SlidingSyncVersion
import org.matrix.rustcomponents.sdk.SlidingSyncVersion as RustSlidingSyncVersion

internal fun RustSlidingSyncVersion.map(): SlidingSyncVersion {
    return when (this) {
        RustSlidingSyncVersion.NONE -> SlidingSyncVersion.None
        RustSlidingSyncVersion.NATIVE -> SlidingSyncVersion.Native
    }
}
