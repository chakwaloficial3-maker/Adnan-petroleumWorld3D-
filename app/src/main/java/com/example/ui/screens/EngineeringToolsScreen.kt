package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.model.AppLanguage
import com.example.model.BiText
import com.example.model.DiagramType
import com.example.ui.components.GlassIndustrialCard
import com.example.ui.components.ScreenTopBar
import com.example.ui.components.TechnicalDiagramCard
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BorderSlate
import com.example.ui.theme.CleanWhite
import com.example.ui.theme.CoolSilver
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.GlassSlate
import com.example.ui.theme.LuxuryGold
import com.example.ui.theme.SafetyEmerald
import com.example.viewmodel.AppUiState
import com.example.viewmodel.MainViewModel
import java.util.Locale

enum class ToolsTab(val label: BiText) {
    CALCULATORS(BiText("Calculators", "کیلکولیٹرز")),
    CONVERTERS(BiText("Unit Converters", "یونٹ کنورٹر")),
    TABLES(BiText("Ref Tables", "انجینئرنگ ٹیبلز")),
    DIAGRAMS(BiText("Diagrams", "ٹیکنیکل ڈایاگرامز"))
}

@Composable
fun EngineeringToolsScreen(
    viewModel: MainViewModel,
    uiState: AppUiState,
    initialTab: ToolsTab = ToolsTab.CALCULATORS
) {
    val lang = uiState.preferences.language
    var activeTab by remember(initialTab) { mutableStateOf(initialTab) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
    ) {
        ScreenTopBar(
            title = BiText("Engineering Tools", "انجینئرنگ ٹولز").resolve(lang),
            subtitle = BiText("Verified Petroleum Formulas & Conversions", "مستند پیٹرولیم فارمولے اور یونٹ کنورژن").resolve(lang),
            language = lang,
            onToggleLanguage = { viewModel.toggleLanguage() }
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(ToolsTab.entries) { tab ->
                val sel = activeTab == tab
                FilterChip(
                    selected = sel,
                    onClick = { activeTab = tab },
                    modifier = Modifier.testTag("tools_tab_${tab.name.lowercase()}"),
                    label = { Text(tab.label.resolve(lang)) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ElectricBlue.copy(alpha = 0.22f),
                        selectedLabelColor = ElectricBlue,
                        containerColor = GlassSlate,
                        labelColor = CoolSilver
                    )
                )
            }
        }

        when (activeTab) {
            ToolsTab.CALCULATORS -> CalculatorsView(lang)
            ToolsTab.CONVERTERS -> ConvertersView(lang)
            ToolsTab.TABLES -> ReferenceTablesView(viewModel, lang)
            ToolsTab.DIAGRAMS -> DiagramsGalleryView(lang)
        }
    }
}

@Composable
private fun CalculatorsView(lang: AppLanguage) {
    // 1. Hydrostatic Pressure
    var mw by remember { mutableStateOf("12.0") }
    var tvd by remember { mutableStateOf("10000") }
    val hydroPsi = (mw.toDoubleOrNull() ?: 0.0) * 0.052 * (tvd.toDoubleOrNull() ?: 0.0)
    val hydroBar = hydroPsi * 0.0689476

    // 2. API Gravity
    var sg by remember { mutableStateOf("0.85") }
    val sgVal = (sg.toDoubleOrNull() ?: 0.0).coerceAtLeast(0.01)
    val apiVal = (141.5 / sgVal) - 131.5
    val crudeClass = when {
        apiVal > 31.1 -> BiText("Light Crude", "ہلکا خام تیل (Light)").resolve(lang)
        apiVal >= 22.3 -> BiText("Medium Crude", "درمیانہ خام تیل (Medium)").resolve(lang)
        apiVal >= 10.0 -> BiText("Heavy Crude", "بھاری خام تیل (Heavy)").resolve(lang)
        else -> BiText("Extra Heavy / Bitumen", "انتہائی بھاری / بٹومین").resolve(lang)
    }

    // 3. Annular Velocity
    var pumpFlow by remember { mutableStateOf("450") }
    var holeDiam by remember { mutableStateOf("8.5") }
    var pipeOd by remember { mutableStateOf("5.0") }
    val dh2 = (holeDiam.toDoubleOrNull() ?: 0.0).let { it * it }
    val dp2 = (pipeOd.toDoubleOrNull() ?: 0.0).let { it * it }
    val avFpm = if (dh2 > dp2) 24.5 * (pumpFlow.toDoubleOrNull() ?: 0.0) / (dh2 - dp2) else 0.0

    // 4. Pump Hydraulic Horsepower
    var pumpPress by remember { mutableStateOf("3200") }
    var pumpGpm by remember { mutableStateOf("450") }
    val hhp = (pumpPress.toDoubleOrNull() ?: 0.0) * (pumpGpm.toDoubleOrNull() ?: 0.0) / 1714.0

    // 5. Real Gas Law (P2 = P1 * V1 * T2 * Z2 / (V2 * T1 * Z1))
    var p1 by remember { mutableStateOf("2500") }
    var t1F by remember { mutableStateOf("180") }
    var p2 by remember { mutableStateOf("1000") }
    var t2F by remember { mutableStateOf("60") }
    val t1R = (t1F.toDoubleOrNull() ?: 60.0) + 459.67
    val t2R = (t2F.toDoubleOrNull() ?: 60.0) + 459.67
    val expansionFactor = if ((p2.toDoubleOrNull() ?: 0.0) > 0 && t1R > 0) {
        ((p1.toDoubleOrNull() ?: 0.0) / (p2.toDoubleOrNull() ?: 1.0)) * (t2R / t1R)
    } else 0.0

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            CalculatorCard(
                title = BiText("1. Hydrostatic Pressure (Well Control)", "1. ہائیڈروسٹیٹک پریشر (ویل کنٹرول)").resolve(lang),
                formula = "P (psi) = 0.052 × MW (ppg) × TVD (ft)",
                resultText = String.format(Locale.US, "%.1f psi  |  %.2f bar", hydroPsi, hydroBar)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CalcField(mw, { mw = it }, "MW (ppg)", Modifier.weight(1f), "calc_mw")
                    CalcField(tvd, { tvd = it }, "TVD (ft)", Modifier.weight(1f), "calc_tvd")
                }
            }
        }

        item {
            CalculatorCard(
                title = BiText("2. API Gravity & Crude Classification", "2. API گریویٹی اور خام تیل کی قسم").resolve(lang),
                formula = "°API = (141.5 / Specific Gravity) − 131.5",
                resultText = String.format(Locale.US, "%.2f °API  (%s)", apiVal, crudeClass)
            ) {
                CalcField(sg, { sg = it }, "Specific Gravity (SG @ 60°F)", Modifier.fillMaxWidth(), "calc_sg")
            }
        }

        item {
            CalculatorCard(
                title = BiText("3. Annular Velocity (Drilling Hydraulics)", "3. اینولر ویلاسٹی (ڈرلنگ ہائیڈرولکس)").resolve(lang),
                formula = "AV (ft/min) = 24.5 × Q (gpm) / (Dh² − Dp²)",
                resultText = String.format(Locale.US, "%.1f ft/min  (%.2f m/s)", avFpm, avFpm * 0.00508)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CalcField(pumpFlow, { pumpFlow = it }, "Q (gpm)", Modifier.weight(1f), "calc_av_q")
                    CalcField(holeDiam, { holeDiam = it }, "Dh (in)", Modifier.weight(1f), "calc_av_dh")
                    CalcField(pipeOd, { pipeOd = it }, "Dp (in)", Modifier.weight(1f), "calc_av_dp")
                }
            }
        }

        item {
            CalculatorCard(
                title = BiText("4. Pump Hydraulic Horsepower (HHP)", "4. پمپ ہائیڈرولک ہارس پاور").resolve(lang),
                formula = "HHP = Pressure (psi) × Flow (gpm) / 1714",
                resultText = String.format(Locale.US, "%.1f HHP  (%.1f kW)", hhp, hhp * 0.7457)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CalcField(pumpPress, { pumpPress = it }, "Pressure (psi)", Modifier.weight(1f), "calc_hhp_p")
                    CalcField(pumpGpm, { pumpGpm = it }, "Flow (gpm)", Modifier.weight(1f), "calc_hhp_q")
                }
            }
        }

        item {
            CalculatorCard(
                title = BiText("5. Gas Volume Expansion Ratio (Z=1)", "5. گیس پھیلاؤ کا تناسب").resolve(lang),
                formula = "V₂ / V₁ = (P₁ / P₂) × (T₂°R / T₁°R)",
                resultText = String.format(Locale.US, "Expansion Ratio: %.2f ×", expansionFactor)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CalcField(p1, { p1 = it }, "P1 (psia)", Modifier.weight(1f), "calc_g_p1")
                    CalcField(t1F, { t1F = it }, "T1 (°F)", Modifier.weight(1f), "calc_g_t1")
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CalcField(p2, { p2 = it }, "P2 (psia)", Modifier.weight(1f), "calc_g_p2")
                    CalcField(t2F, { t2F = it }, "T2 (°F)", Modifier.weight(1f), "calc_g_t2")
                }
            }
        }
    }
}

@Composable
private fun ConvertersView(lang: AppLanguage) {
    var pressPsi by remember { mutableStateOf("1000") }
    val psi = pressPsi.toDoubleOrNull() ?: 0.0

    var tempC by remember { mutableStateOf("100") }
    val cVal = tempC.toDoubleOrNull() ?: 0.0

    var flowBpd by remember { mutableStateOf("5000") }
    val bpd = flowBpd.toDoubleOrNull() ?: 0.0

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            GlassIndustrialCard {
                Text(BiText("Pressure Converter (from psi)", "پریشر کنورٹر (psi سے)").resolve(lang), style = MaterialTheme.typography.titleMedium, color = ElectricBlue)
                Spacer(modifier = Modifier.height(8.dp))
                CalcField(pressPsi, { pressPsi = it }, "Pressure (psi)", Modifier.fillMaxWidth(), "conv_psi")
                Spacer(modifier = Modifier.height(10.dp))
                Text(String.format(Locale.US, "• %.3f bar\n• %.1f kPa\n• %.3f MPa\n• %.2f atm", psi * 0.0689476, psi * 6.89476, psi * 0.00689476, psi / 14.6959), style = MaterialTheme.typography.labelMedium, color = AmberGlow)
            }
        }
        item {
            GlassIndustrialCard {
                Text(BiText("Temperature Converter (from °C)", "درجہ حرارت کنورٹر (°C سے)").resolve(lang), style = MaterialTheme.typography.titleMedium, color = LuxuryGold)
                Spacer(modifier = Modifier.height(8.dp))
                CalcField(tempC, { tempC = it }, "Temperature (°C)", Modifier.fillMaxWidth(), "conv_temp")
                Spacer(modifier = Modifier.height(10.dp))
                val f = cVal * 9.0 / 5.0 + 32.0
                val k = cVal + 273.15
                val r = f + 459.67
                Text(String.format(Locale.US, "• %.2f °F\n• %.2f K\n• %.2f °R (Rankine)", f, k, r), style = MaterialTheme.typography.labelMedium, color = CleanWhite)
            }
        }
        item {
            GlassIndustrialCard {
                Text(BiText("Production Flow Converter (from BPD)", "بہاؤ کنورٹر (BPD سے)").resolve(lang), style = MaterialTheme.typography.titleMedium, color = SafetyEmerald)
                Spacer(modifier = Modifier.height(8.dp))
                CalcField(flowBpd, { flowBpd = it }, "Barrels Per Day (bbl/d)", Modifier.fillMaxWidth(), "conv_bpd")
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    String.format(Locale.US, "• %.2f m³/day\n• %.2f m³/hr\n• %.2f US gpm\n• %.1f L/min", bpd * 0.158987, bpd * 0.158987 / 24.0, bpd * 42.0 / 1440.0, bpd * 158.987 / 1440.0),
                    style = MaterialTheme.typography.labelMedium,
                    color = ElectricBlue
                )
            }
        }
    }
}

@Composable
private fun ReferenceTablesView(viewModel: MainViewModel, lang: AppLanguage) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        items(viewModel.repository.referenceTables) { table ->
            GlassIndustrialCard {
                Text(table.title.resolve(lang), style = MaterialTheme.typography.titleMedium, color = LuxuryGold)
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    table.headers.forEach { h ->
                        Text(h, style = MaterialTheme.typography.labelSmall, color = ElectricBlue, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    }
                }
                HorizontalDivider(color = BorderSlate, modifier = Modifier.padding(vertical = 6.dp))
                table.rows.forEach { row ->
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        row.forEach { cell ->
                            Text(cell, style = MaterialTheme.typography.bodySmall, color = CleanWhite, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DiagramsGalleryView(lang: AppLanguage) {
    val gallery = listOf(
        Triple(DiagramType.ROTARY_RIG_CIRCULATION, BiText("Rotary Rig Mud Circulation Loop", "روٹری رگ مڈ سرکولیشن سسٹم"), true),
        Triple(DiagramType.CENTRIFUGAL_PUMP_FLOW, BiText("Centrifugal Pump Volute & Impeller", "سینٹری فیوگل پمپ وولیوٹ اور امپیلر"), true),
        Triple(DiagramType.THREE_PHASE_SEPARATOR, BiText("3-Phase Separator Internal Weir & Mist Pad", "تھری فیز سیپریٹر اندرونی ساخت"), true),
        Triple(DiagramType.DISTILLATION_COLUMN, BiText("Atmospheric Crude Distillation Tower", "ڈسٹیلیشن ٹاور فلو خاکہ"), true),
        Triple(DiagramType.CHRISTMAS_TREE_STACK, BiText("Christmas Tree Wellhead Valve Stack", "کرسمس ٹری والو اسٹیک"), true),
        Triple(DiagramType.GEOLOGY_ANTICLINE_TRAP, BiText("Anticline Oil & Gas Reservoir Trap", "اینٹی کلائن تیل و گیس ٹریپ"), false)
    )
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        items(gallery) { (type, title, has3d) ->
            Column {
                Text(title.resolve(lang), style = MaterialTheme.typography.titleMedium, color = CleanWhite, modifier = Modifier.padding(bottom = 6.dp))
                TechnicalDiagramCard(diagramType = type, language = lang, has3dModel = has3d)
            }
        }
    }
}

@Composable
private fun CalculatorCard(
    title: String,
    formula: String,
    resultText: String,
    inputs: @Composable () -> Unit
) {
    GlassIndustrialCard {
        Text(title, style = MaterialTheme.typography.titleMedium, color = CleanWhite)
        Spacer(modifier = Modifier.height(4.dp))
        Surface(color = DeepNavy, shape = RoundedCornerShape(8.dp), border = BorderStroke(1.dp, BorderSlate)) {
            Text(formula, style = MaterialTheme.typography.labelSmall, color = ElectricBlue, modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        inputs()
        Spacer(modifier = Modifier.height(10.dp))
        Surface(color = LuxuryGold.copy(alpha = 0.16f), shape = RoundedCornerShape(10.dp), border = BorderStroke(1.dp, LuxuryGold.copy(alpha = 0.5f))) {
            Text(resultText, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = AmberGlow, modifier = Modifier.fillMaxWidth().padding(10.dp))
        }
    }
}

@Composable
private fun CalcField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    testTag: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = modifier.testTag(testTag),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ElectricBlue,
            unfocusedBorderColor = BorderSlate,
            focusedTextColor = CleanWhite,
            unfocusedTextColor = CleanWhite,
            focusedLabelColor = ElectricBlue,
            unfocusedLabelColor = CoolSilver
        )
    )
}
