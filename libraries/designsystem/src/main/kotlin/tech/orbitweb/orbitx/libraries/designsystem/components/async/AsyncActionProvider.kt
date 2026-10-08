/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.components.async

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction

open class AsyncActionProvider : PreviewParameterProvider<AsyncAction<Unit>> {
    override val values: Sequence<AsyncAction<Unit>>
        get() = sequenceOf(
            AsyncAction.Uninitialized,
            AsyncAction.ConfirmingNoParams,
            AsyncAction.Loading,
            AsyncAction.Failure(Exception("An error occurred")),
            AsyncAction.Success(Unit),
        )
}
