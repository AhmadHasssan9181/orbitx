/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.ui

import androidx.compose.runtime.Composable
import tech.orbitweb.orbitx.features.call.impl.ui.LanguageTagProvider

class FakeLanguageTagProvider(private val languageTag: String?) : LanguageTagProvider {
    @Composable
    override fun provideLanguageTag() = languageTag
}
