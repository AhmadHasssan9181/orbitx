/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.designsystem.atomic.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.compound.tokens.generated.CompoundIcons
import tech.orbitweb.orbitx.libraries.designsystem.colors.gradientCriticalColors
import tech.orbitweb.orbitx.libraries.designsystem.colors.gradientInfoColors
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.Avatar
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarType
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.text.toAnnotatedString
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Button
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.ButtonSize
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Icon
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

/**
 * Ref: https://www.figma.com/design/G1xy0HDZKJf5TCRFmKb5d5/Compound-Android-Components?node-id=2392-6721
 */
@Composable
fun ComposerAlertMolecule(
    avatar: AvatarData?,
    content: AnnotatedString,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
    level: ComposerAlertLevel = ComposerAlertLevel.Info,
    showIcon: Boolean = false,
    submitText: String = stringResource(CommonStrings.action_ok),
) {
    Column(
        modifier.fillMaxWidth()
    ) {
        val lineColor = when (level) {
            ComposerAlertLevel.Info -> ElementTheme.colors.borderInfoSubtle
            ComposerAlertLevel.Critical -> ElementTheme.colors.borderCriticalSubtle
        }

        val textColor = when (level) {
            ComposerAlertLevel.Info -> ElementTheme.colors.textPrimary
            ComposerAlertLevel.Critical -> ElementTheme.colors.textCriticalPrimary
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(lineColor)
        )
        val gradientColors = when (level) {
            ComposerAlertLevel.Info -> gradientInfoColors()
            ComposerAlertLevel.Critical -> gradientCriticalColors()
        }
        Box(
            modifier = Modifier
                .background(Brush.verticalGradient(gradientColors))
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (avatar != null) {
                        Avatar(
                            avatarData = avatar,
                            avatarType = AvatarType.User,
                        )
                    } else if (showIcon) {
                        val icon = when (level) {
                            ComposerAlertLevel.Info -> CompoundIcons.Info()
                            ComposerAlertLevel.Critical -> CompoundIcons.Error()
                        }
                        val iconTint = when (level) {
                            ComposerAlertLevel.Info -> ElementTheme.colors.iconInfoPrimary
                            ComposerAlertLevel.Critical -> ElementTheme.colors.iconCriticalPrimary
                        }
                        Icon(
                            imageVector = icon,
                            tint = iconTint,
                            contentDescription = null,
                        )
                    }
                    Text(
                        text = content,
                        modifier = Modifier.weight(1f),
                        style = ElementTheme.typography.fontBodyMdRegular,
                        color = textColor,
                        textAlign = TextAlign.Start,
                    )
                }
                Button(
                    text = submitText,
                    size = ButtonSize.Medium,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSubmitClick,
                )
            }
        }
    }
}

enum class ComposerAlertLevel {
    Info,
    Critical
}

@PreviewsDayNight
@Composable
internal fun ComposerAlertMoleculePreview(
    @PreviewParameter(ComposerAlertMoleculeParamsProvider::class) params: ComposerAlertMoleculeParams,
) = ElementPreview {
    ComposerAlertMolecule(
        avatar = params.avatar,
        content = "Alice’s verified identity has changed. Learn more".toAnnotatedString(),
        level = params.level,
        showIcon = params.showIcon,
        onSubmitClick = {},
    )
}
