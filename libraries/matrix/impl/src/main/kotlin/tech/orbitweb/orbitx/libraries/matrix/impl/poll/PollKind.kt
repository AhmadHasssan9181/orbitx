/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.poll

import tech.orbitweb.orbitx.libraries.matrix.api.poll.PollKind
import org.matrix.rustcomponents.sdk.PollKind as RustPollKind

fun RustPollKind.map(): PollKind = when (this) {
    RustPollKind.DISCLOSED -> PollKind.Disclosed
    RustPollKind.UNDISCLOSED -> PollKind.Undisclosed
}

fun PollKind.toInner(): RustPollKind = when (this) {
    PollKind.Disclosed -> RustPollKind.DISCLOSED
    PollKind.Undisclosed -> RustPollKind.UNDISCLOSED
}
