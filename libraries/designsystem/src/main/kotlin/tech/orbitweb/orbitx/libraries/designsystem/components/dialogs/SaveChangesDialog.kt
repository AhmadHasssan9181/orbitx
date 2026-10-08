/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@Composable
fun SaveChangesDialog(
    onSaveClick: () -> Unit,
    onDiscardClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = stringResource(CommonStrings.dialog_unsaved_changes_title),
    content: String = stringResource(CommonStrings.dialog_unsaved_changes_description),
    submitText: String = stringResource(CommonStrings.action_save),
    cancelText: String = stringResource(CommonStrings.action_discard),
) = ConfirmationDialog(
    modifier = modifier,
    title = title,
    content = content,
    submitText = submitText,
    cancelText = cancelText,
    onSubmitClick = onSaveClick,
    onCancelClick = onDiscardClick,
    onDismiss = onDismiss,
)

@PreviewsDayNight
@Composable
internal fun SaveChangesDialogPreview() = ElementPreview {
    SaveChangesDialog(
        onSaveClick = {},
        onDiscardClick = {},
        onDismiss = {}
    )
}
