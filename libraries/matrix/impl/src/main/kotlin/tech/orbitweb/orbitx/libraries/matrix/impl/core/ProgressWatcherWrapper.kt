/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.core

import tech.orbitweb.orbitx.libraries.matrix.api.core.ProgressCallback
import org.matrix.rustcomponents.sdk.ProgressWatcher
import org.matrix.rustcomponents.sdk.TransmissionProgress

internal class ProgressWatcherWrapper(private val progressCallback: ProgressCallback) : ProgressWatcher {
    override fun transmissionProgress(progress: TransmissionProgress) {
        progressCallback.onProgress(progress.current.toLong(), progress.total.toLong())
    }
}

internal fun ProgressCallback.toProgressWatcher(): ProgressWatcher {
    return ProgressWatcherWrapper(this)
}
