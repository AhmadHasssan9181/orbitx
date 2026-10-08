/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.root

import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.features.logout.api.direct.DirectLogoutState
import tech.orbitweb.orbitx.libraries.designsystem.utils.snackbar.SnackbarMessage
import tech.orbitweb.orbitx.libraries.matrix.api.core.DeviceId
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

data class PreferencesRootState(
    val myUser: MatrixUser,
    val version: String,
    val deviceId: DeviceId?,
    val isMultiAccountEnabled: Boolean,
    val otherSessions: ImmutableList<MatrixUser>,
    val showSecureBackup: Boolean,
    val showSecureBackupBadge: Boolean,
    val accountManagementUrl: String?,
    val canReportBug: Boolean,
    val showLinkNewDevice: Boolean,
    val showAnalyticsSettings: Boolean,
    val showDeveloperSettings: Boolean,
    val canDeactivateAccount: Boolean,
    val nbOfBlockedUsers: Int,
    val showLabsItem: Boolean,
    val directLogoutState: DirectLogoutState,
    val snackbarMessage: SnackbarMessage?,
    val eventSink: (PreferencesRootEvent) -> Unit,
) {
    val showBlockedUsersItem = nbOfBlockedUsers > 0
}
