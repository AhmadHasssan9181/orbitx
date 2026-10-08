/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.impl.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import org.maplibre.compose.camera.CameraState
import org.maplibre.compose.location.Location
import org.maplibre.compose.location.LocationPuck
import org.maplibre.compose.location.LocationPuckColors
import org.maplibre.compose.location.LocationPuckSizes

@Composable
fun UserLocationPuck(
    cameraState: CameraState,
    location: Location?,
) {
    LocationPuck(
        idPrefix = "user-location",
        location = location,
        cameraState = cameraState,
        accuracyThreshold = Float.POSITIVE_INFINITY,
        showBearingAccuracy = false,
        showBearing = false,
        sizes = LocationPuckSizes(
            dotRadius = 8.dp,
            dotStrokeWidth = 2.dp,
        ),
        colors = LocationPuckColors(
            dotFillColorCurrentLocation = ElementTheme.colors.iconAccentPrimary,
            dotFillColorOldLocation = ElementTheme.colors.iconAccentTertiary,
            dotStrokeColor = ElementTheme.colors.bgCanvasDefault,
        )
    )
}
