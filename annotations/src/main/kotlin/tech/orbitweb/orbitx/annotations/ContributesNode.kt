/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.annotations

import kotlin.reflect.KClass

/**
 * Adds Node to the specified component graph.
 * Equivalent to the following declaration:
 *
 * @BindingContainer
 * @ContributesTo(Scope::class)
 * abstract class YourNodeModule {

 *  @Binds
 *  @IntoMap
 *  @NodeKey(YourNode::class)
 *  abstract fun bindYourNodeFactory(factory: YourNode.Factory): AssistedNodeFactory<*>
 *}

 */
@Target(AnnotationTarget.CLASS)
annotation class ContributesNode(
    val scope: KClass<*>,
)
