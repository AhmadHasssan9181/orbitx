/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.poll.impl.create

internal sealed class CreatePollException : Exception() {
    data class GetPollFailed(
        override val message: String?,
        override val cause: Throwable?
    ) : CreatePollException()

    data class SavePollFailed(
        override val message: String?,
        override val cause: Throwable?
    ) : CreatePollException()
}
