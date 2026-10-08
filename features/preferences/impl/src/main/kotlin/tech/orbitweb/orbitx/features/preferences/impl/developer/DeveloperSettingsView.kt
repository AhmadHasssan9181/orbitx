/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.developer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import io.mhssn.colorpicker.ColorPickerDialog
import io.mhssn.colorpicker.ColorPickerType
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.features.preferences.impl.R
import tech.orbitweb.orbitx.features.preferences.impl.developer.appsettings.AppDeveloperSettingsView
import tech.orbitweb.orbitx.libraries.designsystem.components.ProgressDialog
import tech.orbitweb.orbitx.libraries.designsystem.components.dialogs.ConfirmationDialog
import tech.orbitweb.orbitx.libraries.designsystem.components.list.ListItemContent
import tech.orbitweb.orbitx.libraries.designsystem.components.preferences.PreferenceCategory
import tech.orbitweb.orbitx.libraries.designsystem.components.preferences.PreferencePage
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.CircularProgressIndicator
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.ListItem
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DeveloperSettingsView(
    state: DeveloperSettingsState,
    onOpenShowkase: () -> Unit,
    onPushHistoryClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.showLoader) {
        ProgressDialog()
    }
    if (state.markAllRoomsAsReadAction.isConfirming()) {
        ConfirmationDialog(
            title = "Are you sure you want to mark all the rooms as read?",
            content = "",
            submitText = stringResource(CommonStrings.action_yes),
            onSubmitClick = { state.eventSink(DeveloperSettingsEvents.MarkAllRoomsAsRead(needsConfirmation = false)) },
            onDismiss = { state.eventSink(DeveloperSettingsEvents.DismissMarkAllRoomsAsReadConfirmation) },
        )
    }
    BackHandler(
        enabled = !state.showLoader,
        onBack = onBackClick,
    )
    PreferencePage(
        modifier = modifier,
        onBackClick = {
            if (!state.showLoader) {
                onBackClick()
            }
        },
        title = stringResource(id = CommonStrings.common_developer_options)
    ) {
        // Note: this is OK to hardcode strings in this debug screen.
        AppDeveloperSettingsView(
            state = state.appDeveloperSettingsState,
            onOpenShowkase = onOpenShowkase,
        )
        NotificationCategory(onPushHistoryClick)
        MarkAllRoomsAsReadCategory(state)

        if (state.isEnterpriseBuild) {
            PreferenceCategory(title = "Theme") {
                ListItem(
                    headlineContent = {
                        Text("Change brand color")
                    },
                    onClick = {
                        state.eventSink(DeveloperSettingsEvents.SetShowColorPicker(true))
                    }
                )
                ListItem(
                    headlineContent = {
                        Text("Reset brand color")
                    },
                    onClick = {
                        state.eventSink(DeveloperSettingsEvents.ChangeBrandColor(null))
                    }
                )
            }
        }
        val cache = state.cacheSize
        PreferenceCategory(title = "Cache") {
            ListItem(
                headlineContent = { Text("Database sizes") },
                supportingContent = {
                    if (state.databaseSizes.isLoading()) {
                        Text("Computing...")
                    } else {
                        val dbSizes = state.databaseSizes.dataOrNull()
                        if (dbSizes != null && dbSizes.isNotEmpty()) {
                            Column {
                                for ((dbName, size) in dbSizes) {
                                    Text("$dbName: $size")
                                }
                            }
                        } else {
                            Text("Unknown")
                        }
                    }
                }
            )
            ListItem(
                headlineContent = {
                    Text("Vacuum stores")
                },
                onClick = {
                    state.eventSink(DeveloperSettingsEvents.VacuumStores)
                }
            )
            ListItem(
                headlineContent = {
                    Text("Clear cache")
                },
                trailingContent = if (state.cacheSize.isLoading() || state.clearCacheAction.isLoading()) {
                    ListItemContent.Custom {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .progressSemantics()
                                .size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }
                } else {
                    ListItemContent.Text(cache.dataOrNull().orEmpty())
                },
                onClick = {
                    if (state.clearCacheAction.isLoading().not()) {
                        state.eventSink(DeveloperSettingsEvents.ClearCache)
                    }
                }
            )
        }
    }
    ColorPickerDialog(
        show = state.showColorPicker,
        type = ColorPickerType.Classic(
            showAlphaBar = false,
        ),
        onDismissRequest = {
            state.eventSink(DeveloperSettingsEvents.SetShowColorPicker(false))
        },
        onPickedColor = {
            state.eventSink(DeveloperSettingsEvents.ChangeBrandColor(it))
        },
    )
}

@Composable
private fun MarkAllRoomsAsReadCategory(state: DeveloperSettingsState) {
    PreferenceCategory(title = "Room list") {
        ListItem(
            headlineContent = {
                Text("Mark all rooms as read")
            },
            supportingContent = {
                Text(
                    text = """
                        This will send a private read receipt and a read marker in every room you are part of. 
                        It's a long running operation that might get rate limited.
                        It will run in the background but the app must be alive for it to finish.
                        """.trimIndent(),
                    style = ElementTheme.typography.fontBodySmRegular,
                    color = ElementTheme.colors.textSecondary,
                )
            },
            enabled = !state.showLoader,
            onClick = {
                state.eventSink(DeveloperSettingsEvents.MarkAllRoomsAsRead(needsConfirmation = true))
            },
        )
    }
}

@Composable
private fun NotificationCategory(onPushHistoryClick: () -> Unit) {
    PreferenceCategory(title = stringResource(id = R.string.screen_notification_settings_title)) {
        ListItem(
            headlineContent = {
                Text(stringResource(R.string.troubleshoot_notifications_entry_point_push_history_title))
            },
            onClick = onPushHistoryClick,
        )
    }
}

@PreviewsDayNight
@Composable
internal fun DeveloperSettingsViewPreview(
    @PreviewParameter(DeveloperSettingsStateProvider::class) state: DeveloperSettingsState
) = ElementPreview {
    DeveloperSettingsView(
        state = state,
        onOpenShowkase = {},
        onPushHistoryClick = {},
        onBackClick = {},
    )
}
