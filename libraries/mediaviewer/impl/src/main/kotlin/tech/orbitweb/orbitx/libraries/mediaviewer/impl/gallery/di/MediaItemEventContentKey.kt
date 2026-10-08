/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.gallery.di

import dev.zacsweers.metro.MapKey
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.MediaItem
import kotlin.reflect.KClass

/**
 * Annotation to add a factory of type [MediaItemPresenterFactory] to a
 * DI map multi binding keyed with a subclass of [MediaItem.Event].
 */
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class MediaItemEventContentKey(val value: KClass<out MediaItem.Event>)
