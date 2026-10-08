/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.viewfolder.impl.file

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.compound.tokens.generated.CompoundIcons
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.designsystem.components.async.AsyncFailure
import tech.orbitweb.orbitx.libraries.designsystem.components.async.AsyncLoading
import tech.orbitweb.orbitx.libraries.designsystem.components.button.BackButton
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Icon
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.IconButton
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Scaffold
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.TopAppBar
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewFileView(
    state: ViewFileState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    BackButton(onClick = onBackClick)
                },
                titleStr = state.name,
                actions = {
                    IconButton(
                        onClick = {
                            state.eventSink(ViewFileEvents.Share)
                        },
                    ) {
                        Icon(
                            imageVector = CompoundIcons.ShareAndroid(),
                            contentDescription = stringResource(id = CommonStrings.action_share),
                        )
                    }
                    IconButton(
                        onClick = {
                            state.eventSink(ViewFileEvents.SaveOnDisk)
                        },
                    ) {
                        Icon(
                            imageVector = CompoundIcons.Download(),
                            contentDescription = stringResource(id = CommonStrings.action_save),
                        )
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .consumeWindowInsets(padding)
            ) {
                when (state.lines) {
                    AsyncData.Uninitialized,
                    is AsyncData.Loading -> AsyncLoading()
                    is AsyncData.Success -> FileContent(
                        modifier = Modifier.weight(1f),
                        lines = state.lines.data.toImmutableList(),
                        colorationMode = state.colorationMode,
                    )
                    is AsyncData.Failure -> AsyncFailure(throwable = state.lines.error, onRetry = null)
                }
            }
        }
    )
}

@PreviewsDayNight
@Composable
internal fun ViewFileViewPreview(@PreviewParameter(ViewFileStateProvider::class) state: ViewFileState) = ElementPreview {
    ViewFileView(
        state = state,
        onBackClick = {},
    )
}
