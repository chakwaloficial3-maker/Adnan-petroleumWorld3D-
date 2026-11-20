package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.model.AppLanguage
import com.example.model.BiText
import com.example.model.DiagramType
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BorderSlate
import com.example.ui.theme.CleanWhite
import com.example.ui.theme.CoolSilver
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.GlassSlate
import com.example.ui.theme.HazardCrimson
import com.example.ui.theme.LuxuryGold
import com.example.ui.theme.SafetyEmerald

@Composable
fun TechnicalDiagramCard(
    diagramType: DiagramType,
    language: AppLanguage,
    has3dModel: Boolean,
    modifier: Modifier = Modifier
) {
    val labelText = if (has3dModel) {
        BiText("2D Engineering Schematic + 3D Available", "2D انجینئرنگ ڈایاگرام (3D ماڈل بھی دستیاب)")
    } else {
        BiText("2D Engineering Schematic / Diagram", "2D انجینئرنگ ڈایاگرام (تصویری خاکہ)")
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(GlassSlate)
            .border(1.dp, BorderSlate, RoundedCornerShape(18.dp))
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(if (has3dModel) ElectricBlue else LuxuryGold, RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = labelText.resolve(language),
                style = MaterialTheme.typography.labelMedium,
                color = if (has3dModel) ElectricBlue else LuxuryGold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(DeepNavy)
                .border(1.dp, ElectricBlue.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
        ) {
            Canvas(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                val w = size.width
                val h = size.height
                // Subtle engineering blueprint grid
                val step = 28f
                var gx = 0f
                while (gx <= w) {
                    drawLine(ElectricBlue.copy(alpha = 0.08f), Offset(gx, 0f), Offset(gx, h), 1f)
                    gx += step
                }
                var gy = 0f
                while (gy <= h) {
                    drawLine(ElectricBlue.copy(alpha = 0.08f), Offset(0f, gy), Offset(w, gy), 1f)
                    gy += step
                }

                when (diagramType) {
                    DiagramType.CENTRIFUGAL_PUMP_FLOW -> {
                        // Volute circle & impeller blades
                        val cx = w * 0.45f
                        val cy = h * 0.55f
                        val r = minOf(w, h) * 0.30f
                        drawCircle(ElectricBlue, r, Offset(cx, cy), style = Stroke(4f))
                        drawCircle(LuxuryGold, r * 0.58f, Offset(cx, cy), style = Stroke(3f))
                        // Impeller vanes
                        for (i in 0 until 6) {
                            val ang = i * 60f * (Math.PI / 180f)
                            val x1 = cx + (r * 0.2f * kotlin.math.cos(ang)).toFloat()
                            val y1 = cy + (r * 0.2f * kotlin.math.sin(ang)).toFloat()
                            val x2 = cx + (r * 0.55f * kotlin.math.cos(ang + 0.35)).toFloat()
                            val y2 = cy + (r * 0.55f * kotlin.math.sin(ang + 0.35)).toFloat()
                            drawLine(LuxuryGold, Offset(x1, y1), Offset(x2, y2), 3f, StrokeCap.Round)
                        }
                        // Suction pipe
                        drawLine(SafetyEmerald, Offset(w * 0.08f, cy), Offset(cx - r * 0.2f, cy), 8f)
                        // Discharge pipe up
                        drawLine(ElectricBlue, Offset(cx + r * 0.7f, cy), Offset(cx + r * 0.7f, h * 0.1f), 8f)
                        drawLine(AmberGlow, Offset(w * 0.12f, cy - 16f), Offset(w * 0.25f, cy - 16f), 3f)
                    }
                    DiagramType.THREE_PHASE_SEPARATOR -> {
                        // Horizontal pressure vessel
                        val vx = w * 0.12f
                        val vy = h * 0.24f
                        val vw = w * 0.76f
                        val vh = h * 0.52f
                        drawRoundRect(
                            color = ElectricBlue,
                            topLeft = Offset(vx, vy),
                            size = Size(vw, vh),
                            cornerRadius = CornerRadius(vh * 0.48f, vh * 0.48f),
                            style = Stroke(4f)
                        )
                        // Oil & Water layers
                        drawLine(LuxuryGold, Offset(vx + vw * 0.1f, vy + vh * 0.56f), Offset(vx + vw * 0.65f, vy + vh * 0.56f), 4f)
                        drawLine(SafetyEmerald, Offset(vx + vw * 0.1f, vy + vh * 0.76f), Offset(vx + vw * 0.65f, vy + vh * 0.76f), 4f)
                        // Weir plate
                        drawLine(CleanWhite, Offset(vx + vw * 0.65f, vy + vh * 0.42f), Offset(vx + vw * 0.65f, vy + vh), 5f)
                        // Mist extractor pad
                        drawRect(LuxuryGold.copy(alpha = 0.45f), Offset(vx + vw * 0.72f, vy + 10f), Size(vw * 0.14f, vh * 0.22f))
                        // Inlet & Gas/Oil/Water Nozzles
                        drawLine(CleanWhite, Offset(w * 0.02f, vy + vh * 0.45f), Offset(vx + 12f, vy + vh * 0.45f), 6f)
                        drawLine(ElectricBlue, Offset(vx + vw * 0.79f, vy), Offset(vx + vw * 0.79f, h * 0.05f), 6f)
                        drawLine(LuxuryGold, Offset(vx + vw * 0.78f, vy + vh), Offset(vx + vw * 0.78f, h * 0.92f), 5f)
                        drawLine(SafetyEmerald, Offset(vx + vw * 0.35f, vy + vh), Offset(vx + vw * 0.35f, h * 0.92f), 5f)
                    }
                    DiagramType.DISTILLATION_COLUMN -> {
                        val tx = w * 0.38f
                        val tw = w * 0.24f
                        val ty = h * 0.12f
                        val th = h * 0.76f
                        drawRoundRect(ElectricBlue, Offset(tx, ty), Size(tw, th), CornerRadius(32f, 32f), style = Stroke(4f))
                        for (i in 1..5) {
                            val y = ty + th * (i / 6f)
                            drawLine(LuxuryGold, Offset(tx + 6f, y), Offset(tx + tw - 6f, y), 3f)
                        }
                        // Overhead vapor & side draws
                        drawLine(ElectricBlue, Offset(tx + tw * 0.5f, ty), Offset(tx + tw * 0.5f, h * 0.04f), 4f)
                        drawLine(ElectricBlue, Offset(tx + tw * 0.5f, h * 0.04f), Offset(w * 0.82f, h * 0.04f), 4f)
                        drawLine(SafetyEmerald, Offset(tx + tw, ty + th * 0.3f), Offset(w * 0.86f, ty + th * 0.3f), 4f)
                        drawLine(LuxuryGold, Offset(tx + tw, ty + th * 0.55f), Offset(w * 0.86f, ty + th * 0.55f), 4f)
                        drawLine(HazardCrimson, Offset(w * 0.12f, ty + th * 0.78f), Offset(tx, ty + th * 0.78f), 5f)
                    }
                    DiagramType.ROTARY_RIG_CIRCULATION, DiagramType.MUD_PUMP_TRIPLEX -> {
                        // Derrick mast & mud circulation loop
                        val mastPath = Path().apply {
                            moveTo(w * 0.35f, h * 0.84f)
                            lineTo(w * 0.44f, h * 0.10f)
                            lineTo(w * 0.52f, h * 0.10f)
                            lineTo(w * 0.61f, h * 0.84f)
                            close()
                        }
                        drawPath(mastPath, ElectricBlue, style = Stroke(3.5f))
                        // Drill string down
                        drawLine(LuxuryGold, Offset(w * 0.48f, h * 0.22f), Offset(w * 0.48f, h * 0.94f), 5f)
                        // Mud pit & pump box
                        drawRect(SafetyEmerald, Offset(w * 0.08f, h * 0.62f), Size(w * 0.18f, h * 0.24f), style = Stroke(3f))
                        drawLine(SafetyEmerald, Offset(w * 0.17f, h * 0.62f), Offset(w * 0.17f, h * 0.28f), 4f)
                        drawLine(SafetyEmerald, Offset(w * 0.17f, h * 0.28f), Offset(w * 0.48f, h * 0.28f), 4f)
                        // Return to shaker
                        drawRect(LuxuryGold, Offset(w * 0.74f, h * 0.65f), Size(w * 0.18f, h * 0.18f), style = Stroke(3f))
                        drawLine(AmberGlow, Offset(w * 0.51f, h * 0.74f), Offset(w * 0.74f, h * 0.74f), 4f)
                    }
                    DiagramType.CHRISTMAS_TREE_STACK, DiagramType.BOP_WELL_CONTROL -> {
                        val cx = w * 0.5f
                        drawLine(ElectricBlue, Offset(cx, h * 0.12f), Offset(cx, h * 0.90f), 8f)
                        drawRect(LuxuryGold, Offset(cx - 32f, h * 0.28f), Size(64f, 22f), style = Stroke(3.5f))
                        drawRect(LuxuryGold, Offset(cx - 36f, h * 0.52f), Size(72f, 24f), style = Stroke(3.5f))
                        drawRect(HazardCrimson, Offset(cx - 42f, h * 0.74f), Size(84f, 24f), style = Stroke(3.5f))
                        drawLine(SafetyEmerald, Offset(cx - 75f, h * 0.34f), Offset(cx + 85f, h * 0.34f), 5f)
                    }
                    DiagramType.GEOLOGY_ANTICLINE_TRAP -> {
                        // Anticline dome trap with gas (top), oil (mid), water (bottom)
                        val cap = Path().apply {
                            moveTo(w * 0.08f, h * 0.78f)
                            quadraticTo(w * 0.5f, h * 0.08f, w * 0.92f, h * 0.78f)
                        }
                        drawPath(cap, CoolSilver, style = Stroke(5f))
                        val gasOil = Path().apply {
                            moveTo(w * 0.28f, h * 0.42f)
                            lineTo(w * 0.72f, h * 0.42f)
                        }
                        drawPath(gasOil, HazardCrimson, style = Stroke(3f))
                        drawLine(LuxuryGold, Offset(w * 0.20f, h * 0.58f), Offset(w * 0.80f, h * 0.58f), 4f)
                        drawLine(ElectricBlue, Offset(w * 0.14f, h * 0.72f), Offset(w * 0.86f, h * 0.72f), 4f)
                        // Wellbore piercing crest
                        drawLine(CleanWhite, Offset(w * 0.50f, h * 0.05f), Offset(w * 0.50f, h * 0.65f), 4f)
                    }
                    else -> {
                        // General Process Flow / Control Loop / Heat Exchanger schematic
                        drawRoundRect(ElectricBlue, Offset(w * 0.18f, h * 0.28f), Size(w * 0.64f, h * 0.44f), CornerRadius(18f, 18f), style = Stroke(4f))
                        for (i in 1..4) {
                            val y = h * (0.30f + i * 0.08f)
                            drawLine(LuxuryGold, Offset(w * 0.22f, y), Offset(w * 0.78f, y), 3f)
                        }
                        drawLine(SafetyEmerald, Offset(w * 0.04f, h * 0.5f), Offset(w * 0.18f, h * 0.5f), 5f)
                        drawLine(ElectricBlue, Offset(w * 0.82f, h * 0.5f), Offset(w * 0.96f, h * 0.5f), 5f)
                    }
                }
            }

            // Legend badge bottom-right
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                color = GlassSlate.copy(alpha = 0.88f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = diagramType.name.replace('_', ' '),
                    style = MaterialTheme.typography.labelSmall,
                    color = CoolSilver,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}
