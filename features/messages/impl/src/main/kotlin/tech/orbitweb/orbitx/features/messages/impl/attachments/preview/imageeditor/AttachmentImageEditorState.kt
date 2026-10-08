/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.attachments.preview.imageeditor

import androidx.annotation.FloatRange
import androidx.compose.runtime.Immutable
import tech.orbitweb.orbitx.libraries.mediaviewer.api.local.LocalMedia

private const val DEFAULT_CROP_MARGIN = 0f
private const val MIN_CROP_SIZE = 0.1f

@Immutable
data class AttachmentImageEditorState(
    val localMedia: LocalMedia,
    val edits: AttachmentImageEdits,
    // For preview only
    val previewDebug: Boolean,
)

enum class ImageFilter(val displayName: String) {
    None("Original"),
    Vivid("Vivid"),
    Mono("B&W"),
    Warm("Warm"),
    Cool("Cool"),
    Vintage("Vintage"),
}

enum class CropAspectRatio(val displayName: String, val ratio: Float?) {
    Free("Free", null),
    Square("1:1", 1f),
    Portrait("4:5", 4f / 5f),
    Landscape("16:9", 16f / 9f),
    Story("9:16", 9f / 16f),
}

enum class EditorToolMode {
    Crop,
    Filter,
    Draw,
    Sticker,
}

@Immutable
data class DrawPoint(
    val x: Float, // 0f..1f normalized relative to displayed image
    val y: Float, // 0f..1f normalized relative to displayed image
)

@Immutable
data class DrawStroke(
    val points: List<DrawPoint>,
    val colorHex: Long, // 0xAARRGGBB
    val strokeWidth: Float, // relative to reference width
)

@Immutable
data class StickerItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val text: String,
    val x: Float, // 0f..1f normalized center x
    val y: Float, // 0f..1f normalized center y
    val scale: Float = 1f,
    val rotationDegrees: Float = 0f,
)

@Immutable
data class AttachmentImageEdits(
    val cropRect: NormalizedCropRect = NormalizedCropRect.default(),
    val rotationQuarterTurns: Int = 0,
    val isFlippedHorizontally: Boolean = false,
    val isFlippedVertically: Boolean = false,
    val filter: ImageFilter = ImageFilter.None,
    val drawStrokes: List<DrawStroke> = emptyList(),
    val stickers: List<StickerItem> = emptyList(),
) {
    val normalizedRotationQuarterTurns: Int
        get() = rotationQuarterTurns % 4

    val rotationDegrees: Int
        get() = normalizedRotationQuarterTurns * 90

    val hasChanges: Boolean
        get() = cropRect != NormalizedCropRect.default() ||
            normalizedRotationQuarterTurns != 0 ||
            isFlippedHorizontally ||
            isFlippedVertically ||
            filter != ImageFilter.None ||
            drawStrokes.isNotEmpty() ||
            stickers.isNotEmpty()

    fun rotateAntiClockwise(): AttachmentImageEdits {
        return copy(
            rotationQuarterTurns = (normalizedRotationQuarterTurns + 3) % 4,
            cropRect = cropRect.rotateAntiClockwise(),
            drawStrokes = drawStrokes.map { stroke ->
                stroke.copy(
                    points = stroke.points.map { pt ->
                        DrawPoint(x = pt.y, y = 1f - pt.x)
                    }
                )
            },
            stickers = stickers.map { sticker ->
                sticker.copy(
                    x = sticker.y,
                    y = 1f - sticker.x,
                    rotationDegrees = (sticker.rotationDegrees - 90f)
                )
            }
        )
    }

    fun flipHorizontally(): AttachmentImageEdits {
        return copy(
            isFlippedHorizontally = !isFlippedHorizontally,
            cropRect = cropRect.flipHorizontally(),
            drawStrokes = drawStrokes.map { stroke ->
                stroke.copy(
                    points = stroke.points.map { pt ->
                        DrawPoint(x = 1f - pt.x, y = pt.y)
                    }
                )
            },
            stickers = stickers.map { sticker ->
                sticker.copy(
                    x = 1f - sticker.x,
                    rotationDegrees = -sticker.rotationDegrees
                )
            }
        )
    }

    fun flipVertically(): AttachmentImageEdits {
        return copy(
            isFlippedVertically = !isFlippedVertically,
            cropRect = cropRect.flipVertically(),
            drawStrokes = drawStrokes.map { stroke ->
                stroke.copy(
                    points = stroke.points.map { pt ->
                        DrawPoint(x = pt.x, y = 1f - pt.y)
                    }
                )
            },
            stickers = stickers.map { sticker ->
                sticker.copy(
                    y = 1f - sticker.y,
                    rotationDegrees = -sticker.rotationDegrees
                )
            }
        )
    }
}

@Immutable
data class NormalizedCropRect(
    @FloatRange(from = 0.0, to = 1.0) val left: Float,
    @FloatRange(from = 0.0, to = 1.0) val top: Float,
    @FloatRange(from = 0.0, to = 1.0) val right: Float,
    @FloatRange(from = 0.0, to = 1.0) val bottom: Float,
) {
    init {
        require(left in 0f..1f)
        require(top in 0f..1f)
        require(right in 0f..1f)
        require(bottom in 0f..1f)
        require(left < right)
        require(top < bottom)
    }

    val width: Float
        get() = right - left

    val height: Float
        get() = bottom - top

    fun applyChange(
        dragTarget: CropDragTarget,
        deltaX: Float,
        deltaY: Float,
    ): NormalizedCropRect = when (dragTarget) {
        is CropDragTarget.Move -> translate(deltaX, deltaY)
        is CropDragTarget.Corner -> dragWithCorner(dragTarget, deltaX, deltaY)
        is CropDragTarget.Edge -> dragWithEdge(dragTarget, deltaX, deltaY)
    }

    private fun translate(deltaX: Float, deltaY: Float): NormalizedCropRect {
        val clampedLeft = (left + deltaX).coerceIn(0f, 1f - width)
        val clampedTop = (top + deltaY).coerceIn(0f, 1f - height)
        return copy(
            left = clampedLeft,
            top = clampedTop,
            right = clampedLeft + width,
            bottom = clampedTop + height,
        )
    }

    private fun dragWithCorner(
        dragTarget: CropDragTarget.Corner,
        deltaX: Float,
        deltaY: Float,
    ) = when (dragTarget) {
        CropDragTarget.Corner.TopLeft -> copy(
            left = (left + deltaX).coerceIn(0f, right - MIN_CROP_SIZE),
            top = (top + deltaY).coerceIn(0f, bottom - MIN_CROP_SIZE),
        )
        CropDragTarget.Corner.TopRight -> copy(
            right = (right + deltaX).coerceIn(left + MIN_CROP_SIZE, 1f),
            top = (top + deltaY).coerceIn(0f, bottom - MIN_CROP_SIZE),
        )
        CropDragTarget.Corner.BottomRight -> copy(
            right = (right + deltaX).coerceIn(left + MIN_CROP_SIZE, 1f),
            bottom = (bottom + deltaY).coerceIn(top + MIN_CROP_SIZE, 1f),
        )
        CropDragTarget.Corner.BottomLeft -> copy(
            left = (left + deltaX).coerceIn(0f, right - MIN_CROP_SIZE),
            bottom = (bottom + deltaY).coerceIn(top + MIN_CROP_SIZE, 1f),
        )
    }

    private fun dragWithEdge(
        dragTarget: CropDragTarget.Edge,
        deltaX: Float,
        deltaY: Float,
    ) = when (dragTarget) {
        CropDragTarget.Edge.Top -> copy(
            top = (top + deltaY).coerceIn(0f, bottom - MIN_CROP_SIZE),
        )
        CropDragTarget.Edge.Right -> copy(
            right = (right + deltaX).coerceIn(left + MIN_CROP_SIZE, 1f),
        )
        CropDragTarget.Edge.Bottom -> copy(
            bottom = (bottom + deltaY).coerceIn(top + MIN_CROP_SIZE, 1f),
        )
        CropDragTarget.Edge.Left -> copy(
            left = (left + deltaX).coerceIn(0f, right - MIN_CROP_SIZE),
        )
    }

    fun rotateAntiClockwise() = copy(
        left = top,
        top = 1f - right,
        right = bottom,
        bottom = 1f - left,
    )

    fun flipHorizontally() = copy(
        left = 1f - right,
        right = 1f - left,
    )

    fun flipVertically() = copy(
        top = 1f - bottom,
        bottom = 1f - top,
    )

    companion object {
        fun default() = NormalizedCropRect(
            left = DEFAULT_CROP_MARGIN,
            top = DEFAULT_CROP_MARGIN,
            right = 1f - DEFAULT_CROP_MARGIN,
            bottom = 1f - DEFAULT_CROP_MARGIN,
        )

        fun forAspectRatio(
            ratio: Float?,
            imageWidth: Int,
            imageHeight: Int,
            rotationQuarterTurns: Int = 0,
        ): NormalizedCropRect {
            if (ratio == null || imageWidth <= 0 || imageHeight <= 0) {
                return default()
            }
            val effectiveWidth = if (rotationQuarterTurns % 2 == 0) imageWidth else imageHeight
            val effectiveHeight = if (rotationQuarterTurns % 2 == 0) imageHeight else imageWidth
            val normRatio = ratio * (effectiveHeight.toFloat() / effectiveWidth.toFloat())
            val w: Float
            val h: Float
            if (normRatio <= 1f) {
                w = normRatio.coerceIn(MIN_CROP_SIZE, 1f)
                h = 1f
            } else {
                w = 1f
                h = (1f / normRatio).coerceIn(MIN_CROP_SIZE, 1f)
            }
            val left = ((1f - w) / 2f).coerceIn(0f, 1f - w)
            val top = ((1f - h) / 2f).coerceIn(0f, 1f - h)
            return NormalizedCropRect(
                left = left,
                top = top,
                right = (left + w).coerceIn(left + MIN_CROP_SIZE, 1f),
                bottom = (top + h).coerceIn(top + MIN_CROP_SIZE, 1f),
            )
        }
    }
}

sealed interface CropDragTarget {
    data object Move : CropDragTarget

    sealed interface Corner : CropDragTarget {
        data object TopLeft : Corner
        data object TopRight : Corner
        data object BottomRight : Corner
        data object BottomLeft : Corner
    }

    sealed interface Edge : CropDragTarget {
        data object Top : Edge
        data object Right : Edge
        data object Bottom : Edge
        data object Left : Edge
    }
}
