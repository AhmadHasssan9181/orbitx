/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.tests.konsist.failures

import androidx.compose.runtime.Composable

// Make test `Sealed interface used in Composable MUST be Immutable or Stable` fails

sealed interface SealedInterface

@Composable
fun FailingComposableWithNonImmutableSealedInterface(
    sealedInterface: SealedInterface
) {
}
