/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.poll

import tech.orbitweb.orbitx.libraries.matrix.api.poll.PollAnswer
import org.matrix.rustcomponents.sdk.PollAnswer as RustPollAnswer

fun RustPollAnswer.map(): PollAnswer = PollAnswer(
    id = id,
    text = text,
)
