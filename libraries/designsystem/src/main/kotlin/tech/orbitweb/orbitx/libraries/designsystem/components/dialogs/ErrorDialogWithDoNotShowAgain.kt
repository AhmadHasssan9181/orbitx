/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Checkbox
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.SimpleAlertDialogContent
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDialogWithDoNotShowAgain(
    content: String,
    onDismiss: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    title: String = ErrorDialogDefaults.title,
    submitText: String = ErrorDialogDefaults.submitText,
    cancelText: String? = null,
    onCancel: () -> Unit = {},
) {
    var doNotShowAgain by remember { mutableStateOf(false) }
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = { onDismiss(doNotShowAgain) }
    ) {
        SimpleAlertDialogContent(
            title = title,
            submitText = submitText,
            cancelText = cancelText,
            onSubmitClick = { onDismiss(doNotShowAgain) },
            onCancelClick = onCancel,
        ) {
            Column {
                Text(
                    text = content,
                    style = ElementTheme.typography.fontBodyMdRegular,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = doNotShowAgain, onCheckedChange = { doNotShowAgain = it })
                    Text(
                        text = stringResource(id = CommonStrings.common_do_not_show_this_again),
                        style = ElementTheme.typography.fontBodyMdRegular,
                    )
                }
            }
        }
    }
}

@PreviewsDayNight
@Composable
internal fun ErrorDialogWithDoNotShowAgainPreview() = ElementPreview {
    ErrorDialogWithDoNotShowAgain(
        content = "Content",
        onDismiss = {},
    )
}
