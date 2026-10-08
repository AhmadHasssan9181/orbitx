/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.di

import dev.zacsweers.metro.MapKey
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemEventContent
import kotlin.reflect.KClass

/**
 * Annotation to add a factory of type [TimelineItemPresenterFactory] to a
 * dependency injection map multi binding keyed with a subclass of [TimelineItemEventContent].
 */
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class TimelineItemEventContentKey(val value: KClass<out TimelineItemEventContent>)
