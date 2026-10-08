/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.voicemessages.composer

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import tech.orbitweb.orbitx.libraries.designsystem.components.dialogs.ErrorDialog
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@Composable
internal fun VoiceMessageSendingFailedDialog(
    onDismiss: () -> Unit,
) {
    ErrorDialog(
        title = stringResource(CommonStrings.common_error),
        content = stringResource(CommonStrings.error_failed_uploading_voice_message),
        onSubmit = onDismiss,
        submitText = stringResource(CommonStrings.action_ok),
    )
}
