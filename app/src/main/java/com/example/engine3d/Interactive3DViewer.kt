package com.example.engine3d

import android.opengl.GLSurfaceView
import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.LabelOff
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.OpenWith
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.model.AppLanguage
import com.example.model.BiText
import com.example.model.Model3DType
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.CleanWhite
import com.example.ui.theme.CoolSilver
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.GlassSlate
import com.example.ui.theme.LuxuryGold
import com.example.ui.theme.MidnightBlue
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.roundToInt

@Composable
fun Interactive3DViewer(
    modelType: Model3DType,
    language: AppLanguage,
    initialAutoRotate: Boolean = true,
    initialShowLabels: Boolean = true,
    lowEndMode: Boolean = false,
    isFullScreen: Boolean = false,
    onToggleFullScreen: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var viewMode by remember { mutableStateOf(View3DMode.EXTERNAL) }
    var autoRotate by remember(initialAutoRotate) { mutableStateOf(initialAutoRotate) }
    var showLabels by remember(initialShowLabels) { mutableStateOf(initialShowLabels) }
    var explodeSlider by remember { mutableFloatStateOf(0.85f) }
    var crossSectionSlider by remember { mutableFloatStateOf(0.45f) }
    var projectedLabels by remember { mutableStateOf<List<ProjectedLabel3D>>(emptyList()) }
    var selectedPart by remember { mutableStateOf<Part3D?>(null) }

    val renderer = remember {
        PetroleumGlRenderer(
            initialModel = modelType,
            onLabelsUpdated = { labels -> projectedLabels = labels },
            onPartPicked = { part -> selectedPart = part }
        )
    }

    LaunchedEffect(modelType) {
        renderer.setModel(modelType)
        selectedPart = null
    }
    LaunchedEffect(autoRotate) { renderer.autoRotate = autoRotate }
    LaunchedEffect(viewMode) { renderer.viewMode = viewMode }
    LaunchedEffect(explodeSlider) { renderer.explodeAmount = explodeSlider }
    LaunchedEffect(crossSectionSlider) { renderer.crossSectionAmount = crossSectionSlider }
    LaunchedEffect(lowEndMode) { renderer.lowEndMode = lowEndMode }

    val parts = remember(modelType) { MachinerySceneBuilder.buildScene(modelType) }

    var glViewRef by remember { mutableStateOf<GLSurfaceView?>(null) }
    DisposableEffect(Unit) {
        onDispose { glViewRef?.onPause() }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DeepNavy)
    ) {
        // 3D Viewport Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(if (isFullScreen) Modifier.weight(1f) else Modifier.height(350.dp))
                .clip(RoundedCornerShape(if (isFullScreen) 0.dp else 20.dp))
                .border(
                    width = if (isFullScreen) 0.dp else 1.dp,
                    brush = Brush.linearGradient(listOf(ElectricBlue.copy(alpha = 0.5f), LuxuryGold.copy(alpha = 0.4f))),
                    shape = RoundedCornerShape(if (isFullScreen) 0.dp else 20.dp)
                )
        ) {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("gl_3d_surface"),
                factory = { ctx ->
                    var prevX = 0f
                    var prevY = 0f
                    var downX = 0f
                    var downY = 0f
                    var downTime = 0L
                    var prevPinchDist = 0f

                    GLSurfaceView(ctx).apply {
                        setEGLContextClientVersion(2)
                        setRenderer(renderer)
                        renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
                        setOnTouchListener { v, event ->
                            when (event.actionMasked) {
                                MotionEvent.ACTION_DOWN -> {
                                    prevX = event.x
                                    prevY = event.y
                                    downX = event.x
                                    downY = event.y
                                    downTime = System.currentTimeMillis()
                                    v.parent?.requestDisallowInterceptTouchEvent(true)
                                }
                                MotionEvent.ACTION_POINTER_DOWN -> {
                                    if (event.pointerCount >= 2) {
                                        prevPinchDist = hypot(
                                            (event.getX(0) - event.getX(1)).toDouble(),
                                            (event.getY(0) - event.getY(1)).toDouble()
                                        ).toFloat()
                                    }
                                }
                                MotionEvent.ACTION_MOVE -> {
                                    if (event.pointerCount >= 2) {
                                        val dist = hypot(
                                            (event.getX(0) - event.getX(1)).toDouble(),
                                            (event.getY(0) - event.getY(1)).toDouble()
                                        ).toFloat()
                                        if (prevPinchDist > 1f) {
                                            val delta = (prevPinchDist - dist) * 0.015f
                                            renderer.cameraDistance = (renderer.cameraDistance + delta).coerceIn(2.0f, 8.5f)
                                        }
                                        prevPinchDist = dist
                                    } else {
                                        val dx = event.x - prevX
                                        val dy = event.y - prevY
                                        renderer.yawDeg = (renderer.yawDeg - dx * 0.45f) % 360f
                                        renderer.pitchDeg = (renderer.pitchDeg + dy * 0.35f).coerceIn(-75f, 80f)
                                        prevX = event.x
                                        prevY = event.y
                                    }
                                }
                                MotionEvent.ACTION_UP -> {
                                    v.parent?.requestDisallowInterceptTouchEvent(false)
                                    val elapsed = System.currentTimeMillis() - downTime
                                    val moved = abs(event.x - downX) + abs(event.y - downY)
                                    if (elapsed < 260 && moved < 22f) {
                                        queueEvent { renderer.pickAt(event.x, event.y) }
                                    }
                                }
                                MotionEvent.ACTION_CANCEL -> {
                                    v.parent?.requestDisallowInterceptTouchEvent(false)
                                }
                            }
                            true
                        }
                        glViewRef = this
                    }
                }
            )

            // Projected 2D Component Callouts over 3D Geometry
            if (showLabels) {
                val density = LocalDensity.current
                projectedLabels.forEach { label ->
                    val offsetX = with(density) { label.screenX.roundToInt() - 48 }
                    val offsetY = with(density) { label.screenY.roundToInt() - 16 }
                    Surface(
                        modifier = Modifier
                            .offset { IntOffset(offsetX, offsetY) }
                            .clickable {
                                val found = parts.find { it.componentId == label.componentId }
                                renderer.selectedComponentId = found?.componentId
                                selectedPart = found
                            },
                        color = if (label.isSelected) LuxuryGold else MidnightBlue.copy(alpha = 0.85f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(
                            1.dp,
                            if (label.isSelected) AmberGlow else ElectricBlue.copy(alpha = 0.6f)
                        )
                    ) {
                        Text(
                            text = label.label.resolve(language),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = if (label.isSelected) DeepNavy else CleanWhite,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            // Top-left live 3D badge
            Surface(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp),
                color = MidnightBlue.copy(alpha = 0.82f),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, ElectricBlue.copy(alpha = 0.45f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(ElectricBlue)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = BiText("Interactive OpenGL 3D", "لائیو 3D ماڈل").resolve(language),
                        style = MaterialTheme.typography.labelSmall,
                        color = CleanWhite
                    )
                }
            }

            // Top-right quick camera & fullscreen tools
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Small3DIconButton(
                    icon = if (autoRotate) Icons.Default.Autorenew else Icons.Default.Refresh,
                    tint = if (autoRotate) LuxuryGold else CoolSilver,
                    contentDescription = "Toggle Auto-Rotate",
                    testTag = "btn_3d_autorotate"
                ) { autoRotate = !autoRotate }

                Small3DIconButton(
                    icon = if (showLabels) Icons.Default.Label else Icons.Default.LabelOff,
                    tint = if (showLabels) ElectricBlue else CoolSilver,
                    contentDescription = "Toggle Component Labels",
                    testTag = "btn_3d_labels"
                ) { showLabels = !showLabels }

                Small3DIconButton(
                    icon = Icons.Default.Refresh,
                    tint = CleanWhite,
                    contentDescription = "Reset Camera",
                    testTag = "btn_3d_reset"
                ) {
                    renderer.resetCamera()
                    viewMode = View3DMode.EXTERNAL
                    selectedPart = null
                    renderer.selectedComponentId = null
                }

                if (onToggleFullScreen != null) {
                    Small3DIconButton(
                        icon = if (isFullScreen) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
                        tint = LuxuryGold,
                        contentDescription = "Toggle Full Screen 3D",
                        testTag = "btn_3d_fullscreen"
                    ) { onToggleFullScreen() }
                }
            }

            // Bottom-right Zoom +/- buttons
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Small3DIconButton(
                    icon = Icons.Default.Add,
                    tint = CleanWhite,
                    contentDescription = "Zoom In",
                    testTag = "btn_3d_zoom_in"
                ) { renderer.cameraDistance = (renderer.cameraDistance - 0.5f).coerceIn(2.0f, 8.5f) }
                Small3DIconButton(
                    icon = Icons.Default.Remove,
                    tint = CleanWhite,
                    contentDescription = "Zoom Out",
                    testTag = "btn_3d_zoom_out"
                ) { renderer.cameraDistance = (renderer.cameraDistance + 0.5f).coerceIn(2.0f, 8.5f) }
            }

            // Hint at bottom-left
            Text(
                text = BiText("Drag 360° • Pinch Zoom • Tap Part", "گھمائیں • زوم • پرزے پر ٹیپ کریں").resolve(language),
                style = MaterialTheme.typography.labelSmall,
                color = CoolSilver.copy(alpha = 0.85f),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // View Mode Selector: External / Internal / Exploded / Cross-Section
        LazyRow(
            contentPadding = PaddingValues(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val modes = listOf(
                Triple(View3DMode.EXTERNAL, BiText("External", "بیرونی"), Icons.Default.Visibility),
                Triple(View3DMode.INTERNAL, BiText("Internal", "اندرونی"), Icons.Default.Layers),
                Triple(View3DMode.EXPLODED, BiText("Exploded", "کھلا ماڈل"), Icons.Default.OpenWith),
                Triple(View3DMode.CROSS_SECTION, BiText("Cross-Section", "کراس سیکشن"), Icons.Default.ContentCut)
            )
            items(modes) { (mode, label, icon) ->
                val selected = viewMode == mode
                FilterChip(
                    selected = selected,
                    onClick = { viewMode = mode },
                    modifier = Modifier.testTag("mode_3d_${mode.name.lowercase()}"),
                    leadingIcon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    label = { Text(label.resolve(language), style = MaterialTheme.typography.labelLarge) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ElectricBlue.copy(alpha = 0.22f),
                        selectedLabelColor = ElectricBlue,
                        selectedLeadingIconColor = ElectricBlue,
                        containerColor = GlassSlate,
                        labelColor = CoolSilver
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = selected,
                        selectedBorderColor = ElectricBlue,
                        borderColor = GlassSlate
                    )
                )
            }
        }

        // Dynamic Slider for Exploded or Cross-Section mode
        AnimatedVisibility(visible = viewMode == View3DMode.EXPLODED) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = BiText("Explode:", "فاصلہ:").resolve(language),
                    style = MaterialTheme.typography.labelMedium,
                    color = LuxuryGold
                )
                Slider(
                    value = explodeSlider,
                    onValueChange = { explodeSlider = it },
                    valueRange = 0.1f..1.4f,
                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                    colors = SliderDefaults.colors(thumbColor = LuxuryGold, activeTrackColor = LuxuryGold)
                )
            }
        }

        AnimatedVisibility(visible = viewMode == View3DMode.CROSS_SECTION) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = BiText("Slice Plane:", "کٹاؤ:").resolve(language),
                    style = MaterialTheme.typography.labelMedium,
                    color = ElectricBlue
                )
                Slider(
                    value = crossSectionSlider,
                    onValueChange = { crossSectionSlider = it },
                    valueRange = 0.05f..0.95f,
                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                    colors = SliderDefaults.colors(thumbColor = ElectricBlue, activeTrackColor = ElectricBlue)
                )
            }
        }

        // Component Quick-Pick Strip
        LazyRow(
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(parts) { part ->
                val isSel = selectedPart?.componentId == part.componentId
                Surface(
                    modifier = Modifier
                        .clickable {
                            selectedPart = part
                            renderer.selectedComponentId = part.componentId
                        }
                        .testTag("part_chip_${part.componentId}"),
                    color = if (isSel) LuxuryGold.copy(alpha = 0.22f) else GlassSlate,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, if (isSel) LuxuryGold else ElectricBlue.copy(alpha = 0.25f))
                ) {
                    Text(
                        text = part.label.resolve(language),
                        style = MaterialTheme.typography.labelMedium,
                        color = if (isSel) AmberGlow else CleanWhite,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }

        // Selected Component Inspector Card
        AnimatedVisibility(visible = selectedPart != null) {
            selectedPart?.let { part ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    color = GlassSlate,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, LuxuryGold.copy(alpha = 0.6f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = part.label.resolve(language),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = LuxuryGold
                                )
                                Text(
                                    text = "${BiText("Material", "مٹیریل").resolve(language)}: ${part.material}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ElectricBlue
                                )
                            }
                            IconButton(
                                onClick = {
                                    selectedPart = null
                                    renderer.selectedComponentId = null
                                }
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Close component info", tint = CoolSilver)
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = part.description.resolve(language),
                            style = MaterialTheme.typography.bodyMedium,
                            color = CleanWhite
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Small3DIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    contentDescription: String,
    testTag: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .testTag(testTag),
        color = MidnightBlue.copy(alpha = 0.85f),
        shape = CircleShape,
        border = BorderStroke(1.dp, ElectricBlue.copy(alpha = 0.35f))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = tint,
                modifier = Modifier.size(19.dp)
            )
        }
    }
}
