/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.qrcode.intro

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import tech.orbitweb.orbitx.compound.tokens.generated.CompoundIcons
import tech.orbitweb.orbitx.features.login.impl.R
import tech.orbitweb.orbitx.libraries.designsystem.atomic.organisms.NumberedListOrganism
import tech.orbitweb.orbitx.libraries.designsystem.atomic.pages.FlowStepPage
import tech.orbitweb.orbitx.libraries.designsystem.components.BigIcon
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Button
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Icon
import tech.orbitweb.orbitx.libraries.designsystem.utils.annotatedTextWithBold
import tech.orbitweb.orbitx.libraries.permissions.api.PermissionsView

@Composable
fun QrCodeIntroView(
    state: QrCodeIntroState,
    onBackClick: () -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val latestOnContinue by rememberUpdatedState(onContinue)
    LaunchedEffect(state.canContinue) {
        if (state.canContinue) {
            latestOnContinue()
        }
    }
    FlowStepPage(
        modifier = modifier,
        onBackClick = onBackClick,
        iconStyle = BigIcon.Style.Default(CompoundIcons.Computer()),
        title = stringResource(id = R.string.screen_qr_code_login_initial_state_title, state.desktopAppName),
        subTitle = stringResource(id = R.string.screen_qr_code_login_initial_state_subtitle),
        content = { Content(state = state) },
        buttons = { Buttons(state = state) }
    )

    PermissionsView(
        title = stringResource(R.string.screen_qr_code_login_no_camera_permission_state_title),
        content = stringResource(R.string.screen_qr_code_login_no_camera_permission_state_description, state.appName),
        icon = { Icon(imageVector = CompoundIcons.TakePhotoSolid(), contentDescription = null) },
        state = state.cameraPermissionState,
    )
}

@Composable
private fun Content(state: QrCodeIntroState) {
    NumberedListOrganism(
        modifier = Modifier.padding(top = 50.dp, start = 20.dp, end = 20.dp),
        items = persistentListOf(
            AnnotatedString(stringResource(R.string.screen_qr_code_login_initial_state_item_1, state.desktopAppName)),
            AnnotatedString(stringResource(R.string.screen_qr_code_login_initial_state_item_2)),
            annotatedTextWithBold(
                text = stringResource(
                    id = R.string.screen_qr_code_login_initial_state_item_3,
                    stringResource(R.string.screen_qr_code_login_initial_state_item_3_action),
                ),
                boldText = stringResource(R.string.screen_qr_code_login_initial_state_item_3_action)
            ),
            AnnotatedString(stringResource(R.string.screen_qr_code_login_initial_state_item_4)),
        ),
    )
}

@Composable
private fun ColumnScope.Buttons(
    state: QrCodeIntroState,
) {
    Button(
        text = stringResource(id = R.string.screen_qr_code_login_initial_state_button_title),
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            state.eventSink.invoke(QrCodeIntroEvents.Continue)
        }
    )
}

@PreviewsDayNight
@Composable
internal fun QrCodeIntroViewPreview(@PreviewParameter(QrCodeIntroStateProvider::class) state: QrCodeIntroState) = ElementPreview {
    QrCodeIntroView(
        state = state,
        onBackClick = {},
        onContinue = {},
    )
}
