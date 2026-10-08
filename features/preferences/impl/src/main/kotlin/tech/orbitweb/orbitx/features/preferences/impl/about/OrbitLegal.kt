/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.about

import androidx.annotation.StringRes
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import tech.orbitweb.orbitx.features.preferences.impl.BuildConfig
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

private const val COPYRIGHT_URL = BuildConfig.URL_COPYRIGHT
private const val USE_POLICY_URL = BuildConfig.URL_ACCEPTABLE_USE
private const val PRIVACY_URL = BuildConfig.URL_PRIVACY

sealed class OrbitLegal(
    @StringRes val titleRes: Int,
    val url: String,
) {
    data object Copyright : OrbitLegal(CommonStrings.common_copyright, COPYRIGHT_URL)
    data object AcceptableUsePolicy : OrbitLegal(CommonStrings.common_acceptable_use_policy, USE_POLICY_URL)
    data object PrivacyPolicy : OrbitLegal(CommonStrings.common_privacy_policy, PRIVACY_URL)
}

typealias ElementLegal = OrbitLegal

fun getAllLegals(): ImmutableList<OrbitLegal> {
    return persistentListOf(
        OrbitLegal.Copyright,
        OrbitLegal.AcceptableUsePolicy,
        OrbitLegal.PrivacyPolicy,
    )
}
