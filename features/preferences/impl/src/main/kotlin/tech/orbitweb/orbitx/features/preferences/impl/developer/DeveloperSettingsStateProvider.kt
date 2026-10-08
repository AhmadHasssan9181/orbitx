/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.developer

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.persistentMapOf
import tech.orbitweb.orbitx.features.preferences.impl.developer.appsettings.AppDeveloperSettingsState
import tech.orbitweb.orbitx.features.preferences.impl.developer.appsettings.anAppDeveloperSettingsState
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.architecture.AsyncData

open class DeveloperSettingsStateProvider : PreviewParameterProvider<DeveloperSettingsState> {
    override val values: Sequence<DeveloperSettingsState>
        get() = sequenceOf(
            aDeveloperSettingsState(),
            aDeveloperSettingsState(
                clearCacheAction = AsyncAction.Loading
            ),
            aDeveloperSettingsState(
                isEnterpriseBuild = true,
                // Disable the color picker for now, Paparazzi is failing with:
                // java.lang.IllegalArgumentException: Cannot round NaN value.
                //  at kotlin.math.MathKt__MathJVMKt.roundToInt(MathJVM.kt:1210)
                //  at io.mhssn.colorpicker.ext.ColorExtKt.lighten(ColorExt.kt:86)
                //  at io.mhssn.colorpicker.pickers.ClassicColorPickerKt$ClassicColorPicker$1$1.invokeSuspend(ClassicColorPicker.kt:53)
                showColorPicker = false,
            ),
        )
}

fun aDeveloperSettingsState(
    appDeveloperSettingsState: AppDeveloperSettingsState = anAppDeveloperSettingsState(),
    clearCacheAction: AsyncAction<Unit> = AsyncAction.Uninitialized,
    markAllRoomsAsReadAction: AsyncAction<Unit> = AsyncAction.Uninitialized,
    isEnterpriseBuild: Boolean = false,
    showColorPicker: Boolean = false,
    eventSink: (DeveloperSettingsEvents) -> Unit = {},
) = DeveloperSettingsState(
    appDeveloperSettingsState = appDeveloperSettingsState,
    cacheSize = AsyncData.Success("1.2 MB"),
    databaseSizes = AsyncData.Success(persistentMapOf("state_store" to "1.2MB")),
    clearCacheAction = clearCacheAction,
    markAllRoomsAsReadAction = markAllRoomsAsReadAction,
    isEnterpriseBuild = isEnterpriseBuild,
    showColorPicker = showColorPicker,
    eventSink = eventSink,
)
