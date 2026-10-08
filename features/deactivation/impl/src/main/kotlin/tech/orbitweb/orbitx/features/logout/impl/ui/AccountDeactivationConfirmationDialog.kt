/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.logout.impl.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import tech.orbitweb.orbitx.features.deactivation.impl.R
import tech.orbitweb.orbitx.libraries.designsystem.components.dialogs.ConfirmationDialog
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@Composable
fun AccountDeactivationConfirmationDialog(
    onSubmitClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    ConfirmationDialog(
        title = stringResource(id = R.string.screen_deactivate_account_title),
        content = stringResource(R.string.screen_deactivate_account_confirmation_dialog_content),
        submitText = stringResource(id = CommonStrings.action_delete),
        onSubmitClick = onSubmitClick,
        onDismiss = onDismiss,
        destructiveSubmit = true,
    )
}
