/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.labs

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import tech.orbitweb.orbitx.compound.tokens.generated.CompoundIcons
import tech.orbitweb.orbitx.features.preferences.impl.R
import tech.orbitweb.orbitx.libraries.designsystem.atomic.molecules.IconTitleSubtitleMolecule
import tech.orbitweb.orbitx.libraries.designsystem.atomic.pages.HeaderFooterPage
import tech.orbitweb.orbitx.libraries.designsystem.components.BigIcon
import tech.orbitweb.orbitx.libraries.designsystem.components.ProgressDialog
import tech.orbitweb.orbitx.libraries.designsystem.components.button.BackButton
import tech.orbitweb.orbitx.libraries.designsystem.components.list.ListItemContent
import tech.orbitweb.orbitx.libraries.designsystem.components.list.SwitchListItem
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.TopAppBar

/**
 * The contents of the Labs screen.
 * Design: https://www.figma.com/design/V0dkfRAW6T3yCQKjahpzkX/ER-46-EX--Threads?node-id=2004-27319&t=yssy1yYYigsGON3s-0
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabsView(
    state: LabsState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isApplyingChanges) {
        ProgressDialog()
    }

    BackHandler(
        enabled = !state.isApplyingChanges,
        onBack = onBack,
    )

    HeaderFooterPage(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding(),
        topBar = {
            TopAppBar(
                titleStr = stringResource(R.string.screen_labs_title),
                navigationIcon = {
                    BackButton(onClick = onBack, enabled = !state.isApplyingChanges)
                }
            )
        },
        header = {
            IconTitleSubtitleMolecule(
                modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp),
                title = stringResource(R.string.screen_labs_header_title),
                subTitle = stringResource(R.string.screen_labs_header_description),
                iconStyle = BigIcon.Style.Default(CompoundIcons.Labs())
            )
        },
        contentPadding = PaddingValues(),
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 20.dp),
            ) {
                items(items = state.features, key = { it.key }) { feature ->
                    SwitchListItem(
                        leadingContent = feature.icon?.let { ListItemContent.Icon(it) },
                        headline = feature.title,
                        supportingText = feature.description,
                        value = feature.isEnabled,
                        onChange = {
                            state.eventSink(LabsEvents.ToggleFeature(feature))
                        }
                    )
                }
            }
        }
    )
}

@PreviewsDayNight
@Composable
internal fun LabsViewPreview(@PreviewParameter(LabsStateProvider::class) state: LabsState) {
    ElementPreview {
        LabsView(state = state, onBack = {})
    }
}
