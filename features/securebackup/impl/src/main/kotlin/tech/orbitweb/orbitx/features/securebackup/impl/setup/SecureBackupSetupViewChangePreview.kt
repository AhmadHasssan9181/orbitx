/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.securebackup.impl.setup

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import tech.orbitweb.orbitx.features.securebackup.impl.setup.views.RecoveryKeyUserStory
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight

@PreviewsDayNight
@Composable
internal fun SecureBackupSetupViewChangePreview(
    @PreviewParameter(SecureBackupSetupStateProvider::class) state: SecureBackupSetupState
) = ElementPreview {
    SecureBackupSetupView(
        state = state.copy(
            isChangeRecoveryKeyUserStory = true,
            recoveryKeyViewState = state.recoveryKeyViewState.copy(recoveryKeyUserStory = RecoveryKeyUserStory.Change),
        ),
        onSuccess = {},
        onBackClick = {},
    )
}
