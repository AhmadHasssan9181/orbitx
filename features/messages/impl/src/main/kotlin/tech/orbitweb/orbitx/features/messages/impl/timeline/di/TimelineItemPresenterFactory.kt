/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.di

import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemEventContent
import tech.orbitweb.orbitx.libraries.architecture.Presenter

/**
 * A factory for a [Presenter] associated with a timeline item.
 *
 * Implementations should be annotated with [dev.zacsweers.metro.AssistedFactory] to be created by the dependency injection library.
 *
 * @param C The timeline item's [TimelineItemEventContent] subtype.
 * @param S The [Presenter]'s state class.
 * @return A [Presenter] that produces a state of type [S] for the given content of type [C].
 */
fun interface TimelineItemPresenterFactory<C : TimelineItemEventContent, S : Any> {
    fun create(content: C): Presenter<S>
}
