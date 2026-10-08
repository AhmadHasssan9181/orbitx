/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.error

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ChangeServerErrorProvider : PreviewParameterProvider<ChangeServerError> {
    override val values: Sequence<ChangeServerError>
        get() = sequenceOf(
            ChangeServerError.InvalidServer,
            ChangeServerError.Error(
                messageStr = "An error description",
            ),
            ChangeServerError.NeedElementPro(
                unauthorisedAccountProviderTitle = "orbitweb.tech",
                applicationId = "tech.orbitweb.orbitx.enterprise",
            ),
            ChangeServerError.UnauthorizedAccountProvider(
                unauthorisedAccountProviderTitle = "orbitweb.tech",
                authorisedAccountProviderTitles = listOf("provider.org", "provider.io"),
            ),
            ChangeServerError.SlidingSyncAlert,
            ChangeServerError.UnsupportedServer,
        )
}
