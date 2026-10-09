/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.attachments.preview.imageeditor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.compound.tokens.generated.CompoundIcons
import tech.orbitweb.orbitx.features.messages.impl.R
import tech.orbitweb.orbitx.libraries.designsystem.components.button.BackButton
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreviewDark
import tech.orbitweb.orbitx.libraries.designsystem.text.toPx
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Icon
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.IconButton
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Scaffold
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.TextButton
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.TopAppBar
import tech.orbitweb.orbitx.libraries.designsystem.utils.CommonDrawables
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import kotlin.math.min

private val minHandleTouchRadius = 16.dp
private val maxHandleTouchRadius = 56.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachmentImageEditorView(
    state: AttachmentImageEditorState,
    onCropRectChange: (NormalizedCropRect) -> Unit,
    onRotateClick: () -> Unit,
    onFlipHorizontallyClick: () -> Unit,
    onFlipVerticallyClick: () -> Unit,
    onResetClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
    onFilterChange: (ImageFilter) -> Unit = {},
    onAddDrawStroke: (DrawStroke) -> Unit = {},
    onUndoDrawStroke: () -> Unit = {},
    onClearDrawStrokes: () -> Unit = {},
    onAddSticker: (StickerItem) -> Unit = {},
    onUpdateSticker: (StickerItem) -> Unit = {},
    onRemoveSticker: (String) -> Unit = {},
) {
    val rotateContentDescription = stringResource(R.string.screen_image_edition_a11y_rotate_to_the_left)
    val rotationStateDescription = pluralStringResource(
        R.plurals.screen_image_edition_a11y_rotation_state,
        state.edits.rotationDegrees,
        state.edits.rotationDegrees,
    )
    val flipHorizontalLabel = stringResource(R.string.screen_image_edition_a11y_flip_image_horizontally)
    val flipHorizontalState = if (state.edits.isFlippedHorizontally) {
        stringResource(R.string.screen_image_edition_a11y_flip_image_horizontally_state_flipped)
    } else {
        stringResource(R.string.screen_image_edition_a11y_flip_image_horizontally_state_original)
    }
    val flipVerticalLabel = stringResource(R.string.screen_image_edition_a11y_flip_image_vertically)
    val flipVerticalState = if (state.edits.isFlippedVertically) {
        stringResource(R.string.screen_image_edition_a11y_flip_image_vertically_state_flipped)
    } else {
        stringResource(R.string.screen_image_edition_a11y_flip_image_vertically_state_original)
    }

    var currentToolMode by remember { mutableStateOf(EditorToolMode.Crop) }
    var imageSize by remember(state.localMedia.uri) { mutableStateOf(IntSize.Zero) }
    var selectedAspectRatio by remember { mutableStateOf(CropAspectRatio.Free) }
    var selectedColorHex by remember { mutableLongStateOf(0xFFFF2D55L) }
    var selectedStrokeWidth by remember { mutableFloatStateOf(8f) }
    var selectedStickerId by remember { mutableStateOf<String?>(null) }

    val canUndo = state.edits.drawStrokes.isNotEmpty() || state.edits.stickers.isNotEmpty()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    BackButton(
                        imageVector = CompoundIcons.Close(),
                        onClick = onCancelClick,
                    )
                },
                title = {
                    Text(
                        modifier = Modifier.semantics {
                            heading()
                        },
                        text = stringResource(R.string.screen_image_edition_title),
                    )
                },
                actions = {
                    // Crop Tool
                    IconButton(
                        onClick = { currentToolMode = EditorToolMode.Crop }
                    ) {
                        Icon(
                            imageVector = CompoundIcons.Crop(),
                            contentDescription = "Crop",
                            tint = if (currentToolMode == EditorToolMode.Crop) Color(0xFFA78BFA) else ElementTheme.colors.iconSecondary,
                        )
                    }
                    // Filter Tool
                    IconButton(
                        onClick = { currentToolMode = EditorToolMode.Filter }
                    ) {
                        Icon(
                            imageVector = CompoundIcons.Theme(),
                            contentDescription = "Filters",
                            tint = if (currentToolMode == EditorToolMode.Filter) Color(0xFFA78BFA) else ElementTheme.colors.iconSecondary,
                        )
                    }
                    // Draw Tool
                    IconButton(
                        onClick = { currentToolMode = EditorToolMode.Draw }
                    ) {
                        Icon(
                            imageVector = CompoundIcons.Edit(),
                            contentDescription = "Draw",
                            tint = if (currentToolMode == EditorToolMode.Draw) Color(0xFFA78BFA) else ElementTheme.colors.iconSecondary,
                        )
                    }
                    // Sticker Tool
                    IconButton(
                        onClick = { currentToolMode = EditorToolMode.Sticker }
                    ) {
                        Icon(
                            imageVector = CompoundIcons.Sticker(),
                            contentDescription = "Stickers",
                            tint = if (currentToolMode == EditorToolMode.Sticker) Color(0xFFA78BFA) else ElementTheme.colors.iconSecondary,
                        )
                    }
                    // Undo Button
                    IconButton(
                        onClick = {
                            if (state.edits.drawStrokes.isNotEmpty()) {
                                onUndoDrawStroke()
                            } else if (state.edits.stickers.isNotEmpty()) {
                                val last = state.edits.stickers.lastOrNull()
                                if (last != null) onRemoveSticker(last.id)
                            }
                        },
                        enabled = canUndo,
                    ) {
                        Icon(
                            imageVector = CompoundIcons.Reply(),
                            contentDescription = "Undo",
                            tint = if (canUndo) ElementTheme.colors.iconPrimary else ElementTheme.colors.iconDisabled,
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ElementTheme.colors.bgCanvasDefault)
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                CropEditorCanvas(
                    state = state,
                    currentToolMode = currentToolMode,
                    selectedColorHex = selectedColorHex,
                    selectedStrokeWidth = selectedStrokeWidth,
                    selectedStickerId = selectedStickerId,
                    onSelectSticker = { selectedStickerId = it },
                    onAddDrawStroke = onAddDrawStroke,
                    onUpdateSticker = onUpdateSticker,
                    onRemoveSticker = onRemoveSticker,
                    onCropRectChange = onCropRectChange,
                    onImageSizeChange = { imageSize = it },
                    onDragStart = { selectedAspectRatio = CropAspectRatio.Free },
                )
            }

            when (currentToolMode) {
                EditorToolMode.Crop -> {
                    AspectRatioSelectorRow(
                        selectedAspectRatio = selectedAspectRatio,
                        onAspectRatioSelect = { ratio ->
                            selectedAspectRatio = ratio
                            val newCrop = NormalizedCropRect.forAspectRatio(
                                ratio = ratio.ratio,
                                imageWidth = imageSize.width,
                                imageHeight = imageSize.height,
                                rotationQuarterTurns = state.edits.normalizedRotationQuarterTurns,
                            )
                            onCropRectChange(newCrop)
                        },
                    )
                }
                EditorToolMode.Filter -> {
                    FilterSelectorRow(
                        selectedFilter = state.edits.filter,
                        onFilterSelect = onFilterChange,
                    )
                }
                EditorToolMode.Draw -> {
                    DrawPaletteRow(
                        selectedColorHex = selectedColorHex,
                        selectedStrokeWidth = selectedStrokeWidth,
                        onColorSelect = { selectedColorHex = it },
                        onStrokeWidthSelect = { selectedStrokeWidth = it },
                        onClearAll = onClearDrawStrokes,
                        hasStrokes = state.edits.drawStrokes.isNotEmpty(),
                    )
                }
                EditorToolMode.Sticker -> {
                    StickerPickerRow(
                        onStickerSelect = { emoji ->
                            val newSticker = StickerItem(
                                text = emoji,
                                x = 0.5f,
                                y = 0.5f,
                                scale = 1f,
                            )
                            onAddSticker(newSticker)
                            selectedStickerId = newSticker.id
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .widthIn(max = 360.dp)
                    .navigationBarsPadding()
                    .padding(start = 20.dp, top = 14.dp, end = 20.dp, bottom = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    TextButton(
                        text = stringResource(CommonStrings.action_reset),
                        destructive = true,
                        onClick = {
                            selectedAspectRatio = CropAspectRatio.Free
                            selectedStickerId = null
                            onResetClick()
                        },
                    )
                }
                Row(
                    modifier = Modifier.weight(2f),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    IconButton(
                        onClick = onFlipHorizontallyClick,
                        modifier = Modifier
                            .clearAndSetSemantics {
                                contentDescription = flipHorizontalLabel
                                stateDescription = flipHorizontalState
                            }
                    ) {
                        Icon(
                            imageVector = CompoundIcons.FlipHorizontal(),
                            contentDescription = null,
                        )
                    }
                    IconButton(
                        onClick = onRotateClick,
                        modifier = Modifier
                            .clearAndSetSemantics {
                                contentDescription = rotateContentDescription
                                stateDescription = rotationStateDescription
                            }
                    ) {
                        Icon(
                            imageVector = CompoundIcons.RotateLeft(),
                            contentDescription = null,
                        )
                    }
                    IconButton(
                        onClick = onFlipVerticallyClick,
                        modifier = Modifier
                            .clearAndSetSemantics {
                                contentDescription = flipVerticalLabel
                                stateDescription = flipVerticalState
                            }
                    ) {
                        Icon(
                            imageVector = CompoundIcons.FlipVertical(),
                            contentDescription = null,
                        )
                    }
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    TextButton(
                        text = stringResource(CommonStrings.action_save),
                        onClick = onDoneClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun BoxScope.CropEditorCanvas(
    state: AttachmentImageEditorState,
    currentToolMode: EditorToolMode,
    selectedColorHex: Long,
    selectedStrokeWidth: Float,
    selectedStickerId: String?,
    onSelectSticker: (String) -> Unit,
    onAddDrawStroke: (DrawStroke) -> Unit,
    onUpdateSticker: (StickerItem) -> Unit,
    onRemoveSticker: (String) -> Unit,
    onCropRectChange: (NormalizedCropRect) -> Unit,
    onImageSizeChange: (IntSize) -> Unit = {},
    onDragStart: () -> Unit = {},
) {
    var imageSize by remember(state.localMedia.uri) { mutableStateOf(IntSize.Zero) }
    val rotationQuarterTurns = state.edits.normalizedRotationQuarterTurns
    val flipScaleX = if (state.edits.isFlippedHorizontally) -1f else 1f
    val flipScaleY = if (state.edits.isFlippedVertically) -1f else 1f

    var imageRect by remember { mutableStateOf(Rect.Zero) }
    var inProgressStrokePoints by remember { mutableStateOf<List<DrawPoint>>(emptyList()) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        val displayedSize = remember(maxWidth, maxHeight, imageSize, rotationQuarterTurns) {
            val sourceWidth = imageSize.width.takeIf { it > 0 } ?: 1
            val sourceHeight = imageSize.height.takeIf { it > 0 } ?: 1
            val aspectRatio = if (rotationQuarterTurns % 2 == 0) {
                sourceWidth.toFloat() / sourceHeight.toFloat()
            } else {
                sourceHeight.toFloat() / sourceWidth.toFloat()
            }
            fitSize(
                containerWidth = constraints.maxWidth.toFloat(),
                containerHeight = constraints.maxHeight.toFloat(),
                aspectRatio = aspectRatio,
            )
        }
        val density = LocalDensity.current
        val displayedWidthDp = with(density) { displayedSize.width.toDp() }
        val displayedHeightDp = with(density) { displayedSize.height.toDp() }
        val imageLayoutSize = remember(displayedSize, rotationQuarterTurns) {
            if (rotationQuarterTurns % 2 == 0) {
                displayedSize
            } else {
                Size(
                    width = displayedSize.height,
                    height = displayedSize.width,
                )
            }
        }
        val imageLayoutWidthDp = with(density) { imageLayoutSize.width.toDp() }
        val imageLayoutHeightDp = with(density) { imageLayoutSize.height.toDp() }

        Box(
            modifier = Modifier
                .size(displayedWidthDp, displayedHeightDp)
                .align(Alignment.Center)
                .onPlaced {
                    imageRect = it.boundsInParent()
                }
                .pointerInput(currentToolMode) {
                    if (currentToolMode == EditorToolMode.Sticker) {
                        detectTapGestures {
                            onSelectSticker("")
                        }
                    }
                },
            contentAlignment = Alignment.Center,
        ) {
            val colorFilter = state.edits.filter.toComposeColorFilter()
            if (LocalInspectionMode.current) {
                Image(
                    painter = painterResource(id = CommonDrawables.sample_background),
                    contentDescription = null,
                    colorFilter = colorFilter,
                    modifier = Modifier
                        .requiredSize(imageLayoutWidthDp, imageLayoutHeightDp)
                        .graphicsLayer {
                            scaleX = flipScaleX
                            scaleY = flipScaleY
                        }
                        .graphicsLayer { rotationZ = rotationQuarterTurns * 90f },
                    contentScale = ContentScale.Fit,
                )
            } else {
                AsyncImage(
                    model = state.localMedia.uri,
                    contentDescription = stringResource(CommonStrings.common_image),
                    colorFilter = colorFilter,
                    modifier = Modifier
                        .requiredSize(imageLayoutWidthDp, imageLayoutHeightDp)
                        .graphicsLayer {
                            scaleX = flipScaleX
                            scaleY = flipScaleY
                        }
                        .graphicsLayer { rotationZ = rotationQuarterTurns * 90f },
                    contentScale = ContentScale.Fit,
                    onState = { painterState ->
                        if (painterState is AsyncImagePainter.State.Success) {
                            val size = IntSize(
                                width = painterState.result.image.width,
                                height = painterState.result.image.height,
                            )
                            imageSize = size
                            onImageSizeChange(size)
                        }
                    }
                )
            }

            // Layer 1: Freehand Drawing Strokes
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasW = size.width
                val canvasH = size.height

                for (stroke in state.edits.drawStrokes) {
                    if (stroke.points.size < 2) continue
                    val path = Path()
                    val first = stroke.points.first()
                    path.moveTo(first.x * canvasW, first.y * canvasH)
                    for (i in 1 until stroke.points.size) {
                        val pt = stroke.points[i]
                        path.lineTo(pt.x * canvasW, pt.y * canvasH)
                    }
                    val strokeWidthPx = stroke.strokeWidth / 360f * canvasW
                    drawPath(
                        path = path,
                        color = Color(stroke.colorHex),
                        style = Stroke(
                            width = strokeWidthPx.coerceAtLeast(3f),
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round,
                        )
                    )
                }

                if (inProgressStrokePoints.size >= 2) {
                    val path = Path()
                    val first = inProgressStrokePoints.first()
                    path.moveTo(first.x * canvasW, first.y * canvasH)
                    for (i in 1 until inProgressStrokePoints.size) {
                        val pt = inProgressStrokePoints[i]
                        path.lineTo(pt.x * canvasW, pt.y * canvasH)
                    }
                    val strokeWidthPx = selectedStrokeWidth / 360f * canvasW
                    drawPath(
                        path = path,
                        color = Color(selectedColorHex),
                        style = Stroke(
                            width = strokeWidthPx.coerceAtLeast(3f),
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round,
                        )
                    )
                }
            }

            // Layer 2: Stickers / Emojis
            for (sticker in state.edits.stickers) {
                key(sticker.id) {
                    StickerOverlayView(
                        sticker = sticker,
                        canvasSize = displayedSize,
                        isSelected = selectedStickerId == sticker.id,
                        onSelect = { onSelectSticker(sticker.id) },
                        onUpdate = onUpdateSticker,
                        onRemove = { onRemoveSticker(sticker.id) },
                        modifier = Modifier.align(Alignment.TopStart),
                    )
                }
            }

            // Layer 3: Interactive drawing surface when Draw mode is active
            if (currentToolMode == EditorToolMode.Draw) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(selectedColorHex, selectedStrokeWidth, displayedSize) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    val canvasW = displayedSize.width.takeIf { it > 0f } ?: 1f
                                    val canvasH = displayedSize.height.takeIf { it > 0f } ?: 1f
                                    val normX = (offset.x / canvasW).coerceIn(0f, 1f)
                                    val normY = (offset.y / canvasH).coerceIn(0f, 1f)
                                    inProgressStrokePoints = listOf(DrawPoint(normX, normY))
                                },
                                onDrag = { change, _ ->
                                    change.consume()
                                    val canvasW = displayedSize.width.takeIf { it > 0f } ?: 1f
                                    val canvasH = displayedSize.height.takeIf { it > 0f } ?: 1f
                                    val normX = (change.position.x / canvasW).coerceIn(0f, 1f)
                                    val normY = (change.position.y / canvasH).coerceIn(0f, 1f)
                                    inProgressStrokePoints = inProgressStrokePoints + DrawPoint(normX, normY)
                                },
                                onDragEnd = {
                                    if (inProgressStrokePoints.size >= 2) {
                                        onAddDrawStroke(
                                            DrawStroke(
                                                points = inProgressStrokePoints,
                                                colorHex = selectedColorHex,
                                                strokeWidth = selectedStrokeWidth,
                                            )
                                        )
                                    }
                                    inProgressStrokePoints = emptyList()
                                },
                                onDragCancel = {
                                    inProgressStrokePoints = emptyList()
                                }
                            )
                        }
                )
            }
        }

        // Layer 4: Crop Overlay & Gestures when Crop mode is active
        if (currentToolMode == EditorToolMode.Crop) {
            val minHandleTouchRadiusPx = minHandleTouchRadius.toPx()
            val maxHandleTouchRadiusPx = maxHandleTouchRadius.toPx()
            val touchRadiusPx by rememberUpdatedState(
                (min(
                    state.edits.cropRect.width * imageRect.width,
                    state.edits.cropRect.height * imageRect.height,
                ) / 4f).coerceIn(
                    minHandleTouchRadiusPx,
                    maxHandleTouchRadiusPx,
                )
            )
            var dragTarget by remember { mutableStateOf<CropDragTarget?>(null) }
            val latestCropRect by rememberUpdatedState(state.edits.cropRect)
            val drawGuidelines = dragTarget == CropDragTarget.Move || state.previewDebug

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                onDragStart()
                                dragTarget = detectDragTarget(
                                    touchPoint = offset,
                                    imageOffset = imageRect.topLeft,
                                    cropRect = latestCropRect,
                                    canvasSize = Size(imageRect.width, imageRect.height),
                                    handleTouchRadius = touchRadiusPx,
                                )
                            },
                            onDragCancel = {
                                dragTarget = null
                            },
                            onDragEnd = {
                                dragTarget = null
                            },
                        ) { change, dragAmount ->
                            val activeTarget = dragTarget ?: return@detectDragGestures
                            change.consume()
                            val gestureAreaWidth = imageRect.width.takeIf { it > 0f } ?: size.width.toFloat()
                            val gestureAreaHeight = imageRect.height.takeIf { it > 0f } ?: size.height.toFloat()
                            onCropRectChange(
                                latestCropRect.applyChange(
                                    dragTarget = activeTarget,
                                    deltaX = dragAmount.x / gestureAreaWidth,
                                    deltaY = dragAmount.y / gestureAreaHeight,
                                )
                            )
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                CropOverlay(
                    imageSize = DpSize(displayedWidthDp, displayedHeightDp),
                    cropRect = state.edits.cropRect,
                    drawGuidelines = drawGuidelines,
                    previewDebug = state.previewDebug,
                    touchRadiusPx = touchRadiusPx,
                    dragTarget = dragTarget,
                )
            }
        }
    }
}

@Composable
private fun StickerOverlayView(
    sticker: StickerItem,
    canvasSize: Size,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onUpdate: (StickerItem) -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentSticker by rememberUpdatedState(sticker)
    val canvasW = canvasSize.width.takeIf { it > 0f } ?: 1f
    val canvasH = canvasSize.height.takeIf { it > 0f } ?: 1f
    var stickerLayoutSize by remember { mutableStateOf(IntSize.Zero) }

    val density = LocalDensity.current
    val stickerFontSize = remember(canvasW, currentSticker.scale, density) {
        with(density) {
            (canvasW * 0.14f * currentSticker.scale).coerceAtLeast(24f).toSp()
        }
    }

    Box(
        modifier = modifier
            .onSizeChanged { stickerLayoutSize = it }
            .offset {
                IntOffset(
                    x = (currentSticker.x * canvasW - stickerLayoutSize.width / 2f).roundToInt(),
                    y = (currentSticker.y * canvasH - stickerLayoutSize.height / 2f).roundToInt(),
                )
            }
            .graphicsLayer {
                rotationZ = currentSticker.rotationDegrees
            }
            .pointerInput(sticker.id) {
                awaitEachGesture {
                    awaitFirstDown(requireUnconsumed = false)
                    onSelect()
                }
            }
            .pointerInput(sticker.id, canvasW, canvasH) {
                detectTransformGestures(panZoomLock = false) { _, pan, zoom, rotation ->
                    onSelect()
                    val current = currentSticker
                    val newX = ((current.x * canvasW + pan.x) / canvasW).coerceIn(0.01f, 0.99f)
                    val newY = ((current.y * canvasH + pan.y) / canvasH).coerceIn(0.01f, 0.99f)
                    val newScale = (current.scale * zoom).coerceIn(0.5f, 4.0f)
                    val newRotation = (current.rotationDegrees + rotation)
                    val normalizedRotation = (newRotation % 360f + 360f) % 360f
                    onUpdate(
                        current.copy(
                            x = newX,
                            y = newY,
                            scale = newScale,
                            rotationDegrees = normalizedRotation,
                        )
                    )
                }
            }
            .then(
                if (isSelected) {
                    Modifier
                        .border(1.5.dp, Color(0xFFA78BFA), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                } else {
                    Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                }
            )
    ) {
        Text(
            text = sticker.text,
            style = ElementTheme.typography.fontBodyLgRegular.copy(fontSize = stickerFontSize),
        )
        if (isSelected) {
            // Delete button at top right
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 12.dp, y = (-12).dp)
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEF4444))
                    .clickable { onRemove() },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "×",
                    color = Color.White,
                    style = ElementTheme.typography.fontBodySmMedium.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                )
            }
            // Rotate button at bottom left
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = (-12).dp, y = 12.dp)
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF06B6D4))
                    .pointerInput(sticker.id) {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                change.consume()
                                val current = currentSticker
                                val rotDelta = (dragAmount.x - dragAmount.y) * 2f
                                val newRotation = (current.rotationDegrees + rotDelta % 360f + 360f) % 360f
                                onUpdate(current.copy(rotationDegrees = newRotation))
                            }
                        )
                    }
                    .clickable {
                        val current = currentSticker
                        val nextRotation = ((current.rotationDegrees + 45f).toInt() % 360).toFloat()
                        onUpdate(current.copy(rotationDegrees = nextRotation))
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "↻",
                    color = Color.White,
                    style = ElementTheme.typography.fontBodySmMedium.copy(fontSize = 15.sp, fontWeight = FontWeight.Bold),
                )
            }
            // Scale toggle button at bottom right
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 12.dp, y = 12.dp)
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF8B5CF6))
                    .clickable {
                        val nextScale = when {
                            sticker.scale < 1.25f -> 1.5f
                            sticker.scale < 1.75f -> 2.2f
                            sticker.scale < 2.5f -> 3.0f
                            else -> 1.0f
                        }
                        onUpdate(sticker.copy(scale = nextScale))
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "⤢",
                    color = Color.White,
                    style = ElementTheme.typography.fontBodySmMedium.copy(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                )
            }
        }
    }
}

@Composable
private fun DrawPaletteRow(
    selectedColorHex: Long,
    selectedStrokeWidth: Float,
    onColorSelect: (Long) -> Unit,
    onStrokeWidthSelect: (Float) -> Unit,
    onClearAll: () -> Unit,
    hasStrokes: Boolean,
    modifier: Modifier = Modifier,
) {
    val colors = listOf(
        0xFFFFFFFFL, // White
        0xFF1A1A1AL, // Black
        0xFFFF2D55L, // Red
        0xFFFF9500L, // Orange
        0xFFFFCC00L, // Yellow
        0xFF34C759L, // Green
        0xFF32ADE6L, // Cyan
        0xFF007AFFL, // Blue
        0xFFAF52DEL, // Purple
    )
    val strokeWidths = listOf(
        4f to "Fine",
        8f to "Medium",
        14f to "Bold",
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            colors.forEach { colorHex ->
                val isSelected = colorHex == selectedColorHex
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color(colorHex))
                        .border(
                            width = if (isSelected) 3.dp else 1.dp,
                            color = if (isSelected) Color(0xFFC4B5FD) else Color(0x66FFFFFF),
                            shape = CircleShape,
                        )
                        .clickable { onColorSelect(colorHex) }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                strokeWidths.forEach { (width, label) ->
                    val isSelected = width == selectedStrokeWidth
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isSelected) Color(0xFF8B5CF6) else Color(0xFF1F1D2B)
                            )
                            .border(
                                width = 1.dp,
                                color = if (isSelected) Color(0xFFC4B5FD) else Color(0xFF2E2B45),
                                shape = RoundedCornerShape(12.dp),
                            )
                            .clickable { onStrokeWidthSelect(width) }
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = label,
                            style = ElementTheme.typography.fontBodySmMedium,
                            color = if (isSelected) Color.White else ElementTheme.colors.textSecondary,
                        )
                    }
                }
            }

            if (hasStrokes) {
                TextButton(
                    text = "Clear drawing",
                    destructive = true,
                    onClick = onClearAll,
                )
            }
        }
    }
}

private val emojiStickers = listOf(
    "😀",
    "😂",
    "🤣",
    "😍",
    "🥰",
    "😎",
    "🤩",
    "🥳",
    "😜",
    "🤫",
    "🤔",
    "😴",
    "🤯",
    "😇",
    "🤠",
    "🤖",
    "👻",
    "🔥",
    "✨",
    "💯",
    "❤️",
    "💖",
    "💔",
    "🎉",
    "🎊",
    "👍",
    "👎",
    "👏",
    "🙌",
    "🤝",
    "✌️",
    "🤞",
    "💪",
    "🐶",
    "🐱",
    "🐼",
    "🦊",
    "🦁",
    "🦄",
    "🍕",
    "🍔",
    "🍦",
    "🍩",
    "🎂",
    "🚗",
    "🚀",
    "👑",
    "🎯",
    "⭐",
    "💡"
)

@Composable
private fun StickerPickerRow(
    onStickerSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = "Tap sticker to place on image",
            style = ElementTheme.typography.fontBodySmRegular,
            color = ElementTheme.colors.textSecondary,
            modifier = Modifier.padding(start = 4.dp, bottom = 2.dp),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            emojiStickers.forEach { emoji ->
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1F1D2B))
                        .border(1.dp, Color(0xFF2E2B45), RoundedCornerShape(12.dp))
                        .clickable { onStickerSelect(emoji) },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = emoji,
                        style = ElementTheme.typography.fontBodyLgRegular.copy(fontSize = 24.sp),
                    )
                }
            }
        }
    }
}

@Composable
private fun CropOverlay(
    imageSize: DpSize,
    cropRect: NormalizedCropRect,
    drawGuidelines: Boolean,
    previewDebug: Boolean,
    touchRadiusPx: Float,
    dragTarget: CropDragTarget?,
) {
    val borderColor = ElementTheme.colors.iconPrimary
    val guideColor = ElementTheme.colors.iconPrimary

    Canvas(
        modifier = Modifier.size(imageSize.width, imageSize.height)
    ) {
        val cropLeft = cropRect.left * size.width
        val cropTop = cropRect.top * size.height
        val cropRight = cropRect.right * size.width
        val cropBottom = cropRect.bottom * size.height
        val overlayColor = Color.Black.copy(alpha = 0.48f)

        // Top dark overlay
        drawRect(
            color = overlayColor,
            topLeft = Offset(0f, 0f),
            size = Size(size.width, cropTop),
        )
        // Left dark overlay
        drawRect(
            color = overlayColor,
            topLeft = Offset(0f, cropTop),
            size = Size(cropLeft, cropBottom - cropTop),
        )
        // Right dark overlay
        drawRect(
            color = overlayColor,
            topLeft = Offset(cropRight, cropTop),
            size = Size(size.width - cropRight, cropBottom - cropTop),
        )
        // Bottom dark overlay
        drawRect(
            color = overlayColor,
            topLeft = Offset(0f, cropBottom),
            size = Size(size.width, size.height - cropBottom),
        )

        // Border around crop
        drawRect(
            color = borderColor,
            topLeft = Offset(cropLeft, cropTop),
            size = Size(cropRight - cropLeft, cropBottom - cropTop),
            style = Stroke(width = 1.dp.toPx()),
        )

        if (drawGuidelines) {
            val cropWidth = cropRight - cropLeft
            val cropHeight = cropBottom - cropTop
            // Vertical guidelines (rule of thirds)
            for (i in 1..2) {
                val offsetX = cropLeft + cropWidth * (i / 3f)
                drawLine(
                    color = guideColor.copy(alpha = 0.5f),
                    start = Offset(offsetX, cropTop),
                    end = Offset(offsetX, cropBottom),
                    strokeWidth = 1.dp.toPx(),
                )
            }
            // Horizontal guidelines (rule of thirds)
            for (i in 1..2) {
                val offsetY = cropTop + cropHeight * (i / 3f)
                drawLine(
                    color = guideColor.copy(alpha = 0.5f),
                    start = Offset(cropLeft, offsetY),
                    end = Offset(cropRight, offsetY),
                    strokeWidth = 1.dp.toPx(),
                )
            }
        }

        // Corner handles
        val handleLength = 18.dp.toPx()
        val handleOffset = 2.dp.toPx()
        // Top left corner
        drawCornerHandle(
            x = cropLeft - handleOffset,
            y = cropTop - handleOffset,
            handleLength = handleLength,
            color = borderColor,
            position = CropDragTarget.Corner.TopLeft,
        )
        // Top right corner
        drawCornerHandle(
            x = cropRight + handleOffset,
            y = cropTop - handleOffset,
            handleLength = handleLength,
            color = borderColor,
            position = CropDragTarget.Corner.TopRight,
        )
        // Bottom left corner
        drawCornerHandle(
            x = cropLeft - handleOffset,
            y = cropBottom + handleOffset,
            handleLength = handleLength,
            color = borderColor,
            position = CropDragTarget.Corner.BottomLeft,
        )
        // Bottom right corner
        drawCornerHandle(
            x = cropRight + handleOffset,
            y = cropBottom + handleOffset,
            handleLength = handleLength,
            color = borderColor,
            position = CropDragTarget.Corner.BottomRight,
        )

        val handleColor = borderColor
        // Top handle
        drawEdgeHandle(
            center = Offset((cropLeft + cropRight) / 2f, cropTop - handleOffset),
            horizontal = true,
            handleLength = handleLength,
            color = handleColor,
        )
        // Right handle
        drawEdgeHandle(
            center = Offset(cropRight + handleOffset, (cropTop + cropBottom) / 2f),
            horizontal = false,
            handleLength = handleLength,
            color = handleColor,
        )
        // Bottom handle
        drawEdgeHandle(
            center = Offset((cropLeft + cropRight) / 2f, cropBottom + handleOffset),
            horizontal = true,
            handleLength = handleLength,
            color = handleColor,
        )
        // Left handle
        drawEdgeHandle(
            center = Offset(cropLeft - handleOffset, (cropTop + cropBottom) / 2f),
            horizontal = false,
            handleLength = handleLength,
            color = handleColor,
        )

        if (previewDebug) {
            listOf(
                CropDragTarget.Edge.Top,
                CropDragTarget.Edge.Right,
                CropDragTarget.Edge.Bottom,
                CropDragTarget.Edge.Left,
                CropDragTarget.Corner.TopLeft,
                CropDragTarget.Corner.TopRight,
                CropDragTarget.Corner.BottomRight,
                CropDragTarget.Corner.BottomLeft,
                CropDragTarget.Move,
            ).forEach { target ->
                val color = when (target) {
                    is CropDragTarget.Move -> Color.Red
                    is CropDragTarget.Corner -> Color.Blue
                    is CropDragTarget.Edge -> Color.Green
                }.copy(alpha = if (dragTarget == target) 0.9f else 0.5f)
                drawCircle(
                    color = color,
                    radius = touchRadiusPx,
                    center = computeOffset(target, cropRect, Size(size.width, size.height)),
                )
            }
        }
    }
}

private fun fitSize(
    containerWidth: Float,
    containerHeight: Float,
    aspectRatio: Float,
): Size {
    val widthBasedHeight = containerWidth / aspectRatio
    return if (widthBasedHeight <= containerHeight) {
        Size(width = containerWidth, height = widthBasedHeight)
    } else {
        Size(width = containerHeight * aspectRatio, height = containerHeight)
    }
}

private fun detectDragTarget(
    touchPoint: Offset,
    imageOffset: Offset,
    cropRect: NormalizedCropRect,
    canvasSize: Size,
    handleTouchRadius: Float,
): CropDragTarget? {
    val handlesArea = mapOf(
        CropDragTarget.Move to computeOffset(CropDragTarget.Move, cropRect, canvasSize),
        CropDragTarget.Corner.TopLeft to computeOffset(CropDragTarget.Corner.TopLeft, cropRect, canvasSize),
        CropDragTarget.Corner.TopRight to computeOffset(CropDragTarget.Corner.TopRight, cropRect, canvasSize),
        CropDragTarget.Corner.BottomRight to computeOffset(CropDragTarget.Corner.BottomRight, cropRect, canvasSize),
        CropDragTarget.Corner.BottomLeft to computeOffset(CropDragTarget.Corner.BottomLeft, cropRect, canvasSize),
        CropDragTarget.Edge.Top to computeOffset(CropDragTarget.Edge.Top, cropRect, canvasSize),
        CropDragTarget.Edge.Right to computeOffset(CropDragTarget.Edge.Right, cropRect, canvasSize),
        CropDragTarget.Edge.Bottom to computeOffset(CropDragTarget.Edge.Bottom, cropRect, canvasSize),
        CropDragTarget.Edge.Left to computeOffset(CropDragTarget.Edge.Left, cropRect, canvasSize),
    )
    handlesArea.forEach { (target, corner) ->
        if ((corner - touchPoint + imageOffset).getDistance() <= handleTouchRadius) {
            return target
        }
    }
    val cropLeft = imageOffset.x + cropRect.left * canvasSize.width
    val cropTop = imageOffset.y + cropRect.top * canvasSize.height
    val cropRight = imageOffset.x + cropRect.right * canvasSize.width
    val cropBottom = imageOffset.y + cropRect.bottom * canvasSize.height
    return if (touchPoint.x in cropLeft..cropRight && touchPoint.y in cropTop..cropBottom) {
        CropDragTarget.Move
    } else {
        null
    }
}

private fun computeOffset(
    target: CropDragTarget,
    cropRect: NormalizedCropRect,
    canvasSize: Size,
) = when (target) {
    CropDragTarget.Move -> Offset((cropRect.left + cropRect.right) * canvasSize.width / 2f, (cropRect.top + cropRect.bottom) * canvasSize.height / 2f)
    CropDragTarget.Corner.TopLeft -> Offset(cropRect.left * canvasSize.width, cropRect.top * canvasSize.height)
    CropDragTarget.Edge.Top -> Offset((cropRect.left + cropRect.right) * canvasSize.width / 2f, cropRect.top * canvasSize.height)
    CropDragTarget.Corner.TopRight -> Offset(cropRect.right * canvasSize.width, cropRect.top * canvasSize.height)
    CropDragTarget.Edge.Right -> Offset(cropRect.right * canvasSize.width, (cropRect.top + cropRect.bottom) * canvasSize.height / 2f)
    CropDragTarget.Corner.BottomRight -> Offset(cropRect.right * canvasSize.width, cropRect.bottom * canvasSize.height)
    CropDragTarget.Edge.Bottom -> Offset((cropRect.left + cropRect.right) * canvasSize.width / 2f, cropRect.bottom * canvasSize.height)
    CropDragTarget.Corner.BottomLeft -> Offset(cropRect.left * canvasSize.width, cropRect.bottom * canvasSize.height)
    CropDragTarget.Edge.Left -> Offset(cropRect.left * canvasSize.width, (cropRect.top + cropRect.bottom) * canvasSize.height / 2f)
}

private fun DrawScope.drawCornerHandle(
    x: Float,
    y: Float,
    handleLength: Float,
    color: Color,
    position: CropDragTarget.Corner,
) {
    val strokeWidth = 4.dp.toPx()
    val correction = strokeWidth / 2
    val horizontalCorrection = if (position.isLeft()) -correction else correction
    val horizontalEndX = if (position.isLeft()) x + handleLength else x - handleLength
    val verticalEndY = if (position.isTop()) y + handleLength else y - handleLength
    val verticalCorrection = if (position.isTop()) -correction else correction
    // Horizontal line
    drawLine(
        color = color,
        start = Offset(x + horizontalCorrection, y),
        end = Offset(horizontalEndX + horizontalCorrection, y),
        strokeWidth = strokeWidth,
    )
    // Vertical line
    drawLine(
        color = color,
        start = Offset(x, y + verticalCorrection),
        end = Offset(x, verticalEndY + verticalCorrection),
        strokeWidth = strokeWidth,
    )
}

private fun CropDragTarget.Corner.isLeft() = this == CropDragTarget.Corner.TopLeft || this == CropDragTarget.Corner.BottomLeft
private fun CropDragTarget.Corner.isTop() = this == CropDragTarget.Corner.TopLeft || this == CropDragTarget.Corner.TopRight

private fun DrawScope.drawEdgeHandle(
    center: Offset,
    horizontal: Boolean,
    handleLength: Float,
    color: Color,
) {
    val start = if (horizontal) {
        Offset(center.x - handleLength / 2f, center.y)
    } else {
        Offset(center.x, center.y - handleLength / 2f)
    }
    val end = if (horizontal) {
        Offset(center.x + handleLength / 2f, center.y)
    } else {
        Offset(center.x, center.y + handleLength / 2f)
    }
    drawLine(
        color = color,
        start = start,
        end = end,
        strokeWidth = 4.dp.toPx(),
    )
}

@Preview
@Composable
internal fun AttachmentImageEditorViewPreview(
    @PreviewParameter(AttachmentImageEditorStateProvider::class) state: AttachmentImageEditorState,
) = ElementPreviewDark {
    AttachmentImageEditorView(
        state = state,
        onCropRectChange = {},
        onRotateClick = {},
        onFlipHorizontallyClick = {},
        onFlipVerticallyClick = {},
        onResetClick = {},
        onCancelClick = {},
        onDoneClick = {},
    )
}

@Composable
private fun AspectRatioSelectorRow(
    selectedAspectRatio: CropAspectRatio,
    onAspectRatioSelect: (CropAspectRatio) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CropAspectRatio.entries.forEach { ratio ->
            val isSelected = ratio == selectedAspectRatio
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (isSelected) {
                            Brush.linearGradient(
                                listOf(Color(0xFF8B5CF6), Color(0xFF6366F1))
                            )
                        } else {
                            Brush.linearGradient(
                                listOf(Color(0xFF1F1D2B), Color(0xFF1F1D2B))
                            )
                        }
                    )
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Color(0xFFC4B5FD) else Color(0xFF2E2B45),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { onAspectRatioSelect(ratio) }
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = ratio.displayName,
                    style = ElementTheme.typography.fontBodyMdMedium.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    ),
                    color = if (isSelected) Color.White else ElementTheme.colors.textSecondary,
                )
            }
        }
    }
}

@Composable
private fun FilterSelectorRow(
    selectedFilter: ImageFilter,
    onFilterSelect: (ImageFilter) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ImageFilter.entries.forEach { filter ->
            val isSelected = filter == selectedFilter
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (isSelected) {
                            Brush.linearGradient(
                                listOf(Color(0xFF8B5CF6), Color(0xFF6366F1))
                            )
                        } else {
                            Brush.linearGradient(
                                listOf(Color(0xFF1F1D2B), Color(0xFF1F1D2B))
                            )
                        }
                    )
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Color(0xFFC4B5FD) else Color(0xFF2E2B45),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { onFilterSelect(filter) }
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = filter.displayName,
                    style = ElementTheme.typography.fontBodyMdMedium.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    ),
                    color = if (isSelected) Color.White else ElementTheme.colors.textSecondary,
                )
            }
        }
    }
}

private fun ImageFilter.toComposeColorFilter(): ColorFilter? = when (this) {
    ImageFilter.None -> null
    ImageFilter.Vivid -> ColorFilter.colorMatrix(
        ColorMatrix(
            floatArrayOf(
                1.3f,
                0f,
                0f,
                0f,
                0f,
                0f,
                1.3f,
                0f,
                0f,
                0f,
                0f,
                0f,
                1.3f,
                0f,
                0f,
                0f,
                0f,
                0f,
                1f,
                0f
            )
        )
    )
    ImageFilter.Mono -> ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
    ImageFilter.Warm -> ColorFilter.colorMatrix(
        ColorMatrix(
            floatArrayOf(
                1.15f,
                0f,
                0f,
                0f,
                15f,
                0f,
                1.05f,
                0f,
                0f,
                5f,
                0f,
                0f,
                0.85f,
                0f,
                -10f,
                0f,
                0f,
                0f,
                1f,
                0f
            )
        )
    )
    ImageFilter.Cool -> ColorFilter.colorMatrix(
        ColorMatrix(
            floatArrayOf(
                0.85f,
                0f,
                0f,
                0f,
                -10f,
                0f,
                1.0f,
                0f,
                0f,
                0f,
                0f,
                0f,
                1.2f,
                0f,
                15f,
                0f,
                0f,
                0f,
                1f,
                0f
            )
        )
    )
    ImageFilter.Vintage -> ColorFilter.colorMatrix(
        ColorMatrix(
            floatArrayOf(
                0.9f,
                0.1f,
                0.1f,
                0f,
                25f,
                0.1f,
                0.8f,
                0.1f,
                0f,
                15f,
                0.1f,
                0.1f,
                0.6f,
                0f,
                -10f,
                0f,
                0f,
                0f,
                1f,
                0f
            )
        )
    )
}
