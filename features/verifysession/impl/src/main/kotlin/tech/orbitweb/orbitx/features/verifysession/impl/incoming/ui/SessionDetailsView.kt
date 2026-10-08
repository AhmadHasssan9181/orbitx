/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.verifysession.impl.incoming.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.features.verifysession.impl.R
import tech.orbitweb.orbitx.libraries.designsystem.atomic.atoms.RoundedIconAtom
import tech.orbitweb.orbitx.libraries.designsystem.atomic.atoms.RoundedIconAtomSize
import tech.orbitweb.orbitx.libraries.designsystem.atomic.molecules.TextWithLabelMolecule
import tech.orbitweb.orbitx.libraries.designsystem.icons.CompoundDrawables
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text
import tech.orbitweb.orbitx.libraries.matrix.api.core.DeviceId
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@Composable
fun SessionDetailsView(
    deviceName: String?,
    deviceId: DeviceId,
    signInFormattedTimestamp: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = ElementTheme.colors.borderDisabled,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(24.dp)
            .semantics(mergeDescendants = true) {},
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundedIconAtom(
                modifier = Modifier,
                size = RoundedIconAtomSize.Big,
                resourceId = CompoundDrawables.ic_compound_devices
            )
            Text(
                text = deviceName ?: deviceId.value,
                style = ElementTheme.typography.fontBodyMdMedium,
                color = ElementTheme.colors.textPrimary,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TextWithLabelMolecule(
                label = stringResource(R.string.screen_session_verification_request_details_timestamp),
                text = signInFormattedTimestamp,
                modifier = Modifier.weight(2f),
            )
            TextWithLabelMolecule(
                label = stringResource(CommonStrings.common_device_id),
                text = deviceId.value,
                modifier = Modifier.weight(5f),
                spellText = true,
            )
        }
    }
}

@PreviewsDayNight
@Composable
internal fun SessionDetailsViewPreview() = ElementPreview {
    Column {
        SessionDetailsView(
            deviceName = "ORBIT Chat Android",
            deviceId = DeviceId("ILAKNDNASDLK"),
            signInFormattedTimestamp = "12:34",
        )
        SessionDetailsView(
            deviceName = null,
            deviceId = DeviceId("ILAKNDNASDLK"),
            signInFormattedTimestamp = "12:34",
        )
    }
}
