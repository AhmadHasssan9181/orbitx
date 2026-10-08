/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.architecture.overlay

import com.bumble.appyx.core.navigation.backpresshandlerstrategies.BaseBackPressHandlerStrategy
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.BackStackElements
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.orbitweb.orbitx.libraries.architecture.overlay.operation.Hide

class HideOverlayBackPressHandler<NavTarget : Any> :
    BaseBackPressHandlerStrategy<NavTarget, BackStack.State>() {
    override val canHandleBackPressFlow: Flow<Boolean> by lazy {
        navModel.elements.map(::areThereElements)
    }

    private fun areThereElements(elements: BackStackElements<NavTarget>) =
        elements.isNotEmpty()

    override fun onBackPressed() {
        navModel.accept(Hide())
    }
}
