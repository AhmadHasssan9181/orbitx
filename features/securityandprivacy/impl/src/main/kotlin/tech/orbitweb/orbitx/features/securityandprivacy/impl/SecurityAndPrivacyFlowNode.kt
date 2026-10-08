/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.securityandprivacy.impl

import android.os.Parcelable
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import com.bumble.appyx.navmodel.backstack.BackStack
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.features.securityandprivacy.api.SecurityAndPrivacyEntryPoint
import tech.orbitweb.orbitx.features.securityandprivacy.api.securityAndPrivacyPermissions
import tech.orbitweb.orbitx.features.securityandprivacy.impl.editroomaddress.EditRoomAddressNode
import tech.orbitweb.orbitx.features.securityandprivacy.impl.manageauthorizedspaces.ManageAuthorizedSpacesNode
import tech.orbitweb.orbitx.features.securityandprivacy.impl.root.SecurityAndPrivacyNode
import tech.orbitweb.orbitx.libraries.architecture.BackstackView
import tech.orbitweb.orbitx.libraries.architecture.BaseFlowNode
import tech.orbitweb.orbitx.libraries.architecture.callback
import tech.orbitweb.orbitx.libraries.architecture.createNode
import tech.orbitweb.orbitx.libraries.di.RoomScope
import tech.orbitweb.orbitx.libraries.matrix.api.room.JoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.api.room.powerlevels.use

@ContributesNode(RoomScope::class)
@AssistedInject
class SecurityAndPrivacyFlowNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    private val room: JoinedRoom,
) : BaseFlowNode<SecurityAndPrivacyFlowNode.NavTarget>(
    backstack = BackStack(
        initialElement = NavTarget.SecurityAndPrivacy,
        savedStateMap = buildContext.savedStateMap,
    ),
    buildContext = buildContext,
    plugins = plugins,
) {
    sealed interface NavTarget : Parcelable {
        @Parcelize
        data object SecurityAndPrivacy : NavTarget

        @Parcelize
        data object EditRoomAddress : NavTarget

        @Parcelize
        data object ManageAuthorizedSpaces : NavTarget
    }

    private val callback: SecurityAndPrivacyEntryPoint.Callback = callback()

    @VisibleForTesting
    val navigator = BackstackSecurityAndPrivacyNavigator(callback, backstack)

    override fun onBuilt() {
        super.onBuilt()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                room.roomInfoFlow
                    .map { roomInfo ->
                        room.roomPermissions().use(false) { perms ->
                            perms.securityAndPrivacyPermissions().hasAny(roomInfo.isSpace, roomInfo.joinRule)
                        }
                    }
                    .filter { canEdit -> !canEdit }
                    .first()
                // If the user can no longer edit security and privacy, exit the flow
                callback.onDone()
            }
        }
    }

    override fun resolve(navTarget: NavTarget, buildContext: BuildContext): Node {
        return when (navTarget) {
            NavTarget.SecurityAndPrivacy -> {
                createNode<SecurityAndPrivacyNode>(buildContext, plugins = listOf(navigator))
            }
            NavTarget.EditRoomAddress -> {
                createNode<EditRoomAddressNode>(buildContext, plugins = listOf(navigator))
            }
            NavTarget.ManageAuthorizedSpaces -> {
                createNode<ManageAuthorizedSpacesNode>(buildContext, plugins = listOf(navigator))
            }
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        BackstackView(modifier)
    }
}
