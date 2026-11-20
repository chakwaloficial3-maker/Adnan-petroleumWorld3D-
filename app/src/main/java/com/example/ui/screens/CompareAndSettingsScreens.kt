package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.AppLanguage
import com.example.model.BiText
import com.example.ui.components.GlassIndustrialCard
import com.example.ui.components.ScreenTopBar
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

@Composable
fun CompareEquipmentScreen(
    initialEquipmentId: String?,
    viewModel: MainViewModel,
    uiState: AppUiState,
    onBack: () -> Unit
) {
    val lang = uiState.preferences.language
    val allEq = viewModel.repository.allEquipment
    var firstId by remember(initialEquipmentId) {
        mutableStateOf(initialEquipmentId ?: allEq.first().id)
    }
    var secondId by remember {
        mutableStateOf(allEq.getOrNull(1)?.id ?: allEq.first().id)
    }
    var selectingSlot by remember { mutableStateOf(1) }

    val eqA = viewModel.getEquipment(firstId) ?: allEq.first()
    val eqB = viewModel.getEquipment(secondId) ?: allEq.last()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
    ) {
        ScreenTopBar(
            title = BiText("Equipment Comparison", "مشینری کا موازنہ").resolve(lang),
            subtitle = BiText("Side-by-Side Technical Matrix", "خصوصیات اور اصولوں کا تقابل").resolve(lang),
            language = lang,
            onToggleLanguage = { viewModel.toggleLanguage() },
            onBack = onBack
        )

        // Slot Selector Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                modifier = Modifier.weight(1f).clickable { selectingSlot = 1 },
                color = if (selectingSlot == 1) ElectricBlue.copy(alpha = 0.2f) else GlassSlate,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, if (selectingSlot == 1) ElectricBlue else BorderSlate)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text("A • ${BiText("Select First", "پہلی مشین").resolve(lang)}", style = MaterialTheme.typography.labelSmall, color = ElectricBlue)
                    Text(eqA.name.resolve(lang), style = MaterialTheme.typography.titleSmall, color = CleanWhite, maxLines = 1)
                }
            }
            Surface(
                modifier = Modifier.weight(1f).clickable { selectingSlot = 2 },
                color = if (selectingSlot == 2) LuxuryGold.copy(alpha = 0.2f) else GlassSlate,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, if (selectingSlot == 2) LuxuryGold else BorderSlate)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text("B • ${BiText("Select Second", "دوسری مشین").resolve(lang)}", style = MaterialTheme.typography.labelSmall, color = LuxuryGold)
                    Text(eqB.name.resolve(lang), style = MaterialTheme.typography.titleSmall, color = CleanWhite, maxLines = 1)
                }
            }
        }

        // Picker Strip
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(allEq) { item ->
                val active = (selectingSlot == 1 && item.id == firstId) || (selectingSlot == 2 && item.id == secondId)
                Surface(
                    modifier = Modifier.clickable {
                        if (selectingSlot == 1) firstId = item.id else secondId = item.id
                    },
                    color = if (active) ElectricBlue.copy(alpha = 0.22f) else GlassSlate,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, if (active) ElectricBlue else BorderSlate)
                ) {
                    Text(item.name.resolve(lang), style = MaterialTheme.typography.labelMedium, color = CleanWhite, modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp))
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                CompareRowCard(
                    title = BiText("Category", "کیٹیگری").resolve(lang),
                    left = viewModel.getCategory(eqA.categoryId)?.title?.resolve(lang) ?: "",
                    right = viewModel.getCategory(eqB.categoryId)?.title?.resolve(lang) ?: ""
                )
            }
            item {
                CompareRowCard(
                    title = BiText("3D Interactive Model", "3D ماڈل").resolve(lang),
                    left = if (eqA.has3dModel) BiText("Available (OpenGL 3D)", "دستیاب (3D)").resolve(lang) else BiText("2D Diagram Only", "صرف 2D ڈایاگرام").resolve(lang),
                    right = if (eqB.has3dModel) BiText("Available (OpenGL 3D)", "دستیاب (3D)").resolve(lang) else BiText("2D Diagram Only", "صرف 2D ڈایاگرام").resolve(lang)
                )
            }
            item {
                CompareRowCard(
                    title = BiText("Purpose & Function", "مقصد اور کام").resolve(lang),
                    left = eqA.purposeAndFunction.resolve(lang),
                    right = eqB.purposeAndFunction.resolve(lang)
                )
            }
            item {
                CompareRowCard(
                    title = BiText("Working Principle", "کام کرنے کا اصول").resolve(lang),
                    left = eqA.workingPrinciple.resolve(lang),
                    right = eqB.workingPrinciple.resolve(lang)
                )
            }
            item {
                CompareRowCard(
                    title = BiText("Key Specifications", "اہم تفصیلات").resolve(lang),
                    left = eqA.specifications.joinToString("\n") { "${it.label.resolve(lang)}: ${it.value}" },
                    right = eqB.specifications.joinToString("\n") { "${it.label.resolve(lang)}: ${it.value}" }
                )
            }
        }
    }
}

@Composable
private fun CompareRowCard(title: String, left: String, right: String) {
    GlassIndustrialCard {
        Text(title, style = MaterialTheme.typography.labelLarge, color = AmberGlow)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(left, style = MaterialTheme.typography.bodySmall, color = CleanWhite, modifier = Modifier.weight(1f))
            BoxSeparator()
            Text(right, style = MaterialTheme.typography.bodySmall, color = CoolSilver, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun BoxSeparator() {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .width(1.dp)
            .height(44.dp)
            .background(BorderSlate)
    )
}

@Composable
fun SettingsAboutScreen(
    viewModel: MainViewModel,
    uiState: AppUiState,
    onBack: () -> Unit
) {
    val lang = uiState.preferences.language
    val prefs = uiState.preferences
    val offlineCount = uiState.lessonProgress.values.count { it.isSavedOffline }
    val offlineKb = viewModel.repository.lessons
        .filter { uiState.lessonProgress[it.id]?.isSavedOffline == true }
        .sumOf { it.sizeKb }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
    ) {
        ScreenTopBar(
            title = BiText("Settings & About", "سیٹنگز اور تعارف").resolve(lang),
            subtitle = "PetroleumWorld3D v1.0",
            language = lang,
            onToggleLanguage = { viewModel.toggleLanguage() },
            onBack = onBack
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                GlassIndustrialCard {
                    Text(BiText("Language / زبان", "زبان منتخب کریں").resolve(lang), style = MaterialTheme.typography.titleMedium, color = LuxuryGold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = { viewModel.setLanguage(AppLanguage.EN) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (lang == AppLanguage.EN) ElectricBlue else GlassSlate,
                                contentColor = if (lang == AppLanguage.EN) DeepNavy else CleanWhite
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("English", fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { viewModel.setLanguage(AppLanguage.UR) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (lang == AppLanguage.UR) LuxuryGold else GlassSlate,
                                contentColor = if (lang == AppLanguage.UR) DeepNavy else CleanWhite
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("اردو (Urdu)", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            item {
                GlassIndustrialCard {
                    Text(BiText("3D Engine & Performance", "3D انجن اور کارکردگی").resolve(lang), style = MaterialTheme.typography.titleMedium, color = ElectricBlue)
                    Spacer(modifier = Modifier.height(8.dp))
                    SettingSwitchRow(
                        title = BiText("Auto-Rotate 3D Models", "3D ماڈل خودکار گھمائیں").resolve(lang),
                        checked = prefs.autoRotate3d,
                        onCheckedChange = { viewModel.setAutoRotate3d(it) },
                        testTag = "switch_autorotate"
                    )
                    HorizontalDivider(color = BorderSlate, modifier = Modifier.padding(vertical = 6.dp))
                    SettingSwitchRow(
                        title = BiText("Show Floating 3D Part Labels", "3D پرزوں کے لیبل دکھائیں").resolve(lang),
                        checked = prefs.show3dLabels,
                        onCheckedChange = { viewModel.setShow3dLabels(it) },
                        testTag = "switch_labels"
                    )
                    HorizontalDivider(color = BorderSlate, modifier = Modifier.padding(vertical = 6.dp))
                    SettingSwitchRow(
                        title = BiText("Low-End Device Optimization", "کمزور ڈیوائسز کے لیے بہتر کارکردگی").resolve(lang),
                        checked = prefs.lowEndPerformanceMode,
                        onCheckedChange = { viewModel.setLowEndMode(it) },
                        testTag = "switch_low_end"
                    )
                }
            }

            item {
                GlassIndustrialCard {
                    Text(BiText("Offline Storage & History", "آف لائن ڈیٹا اور سرچ ہسٹری").resolve(lang), style = MaterialTheme.typography.titleMedium, color = SafetyEmerald)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "${BiText("Saved Offline Lessons", "محفوظ آف لائن اسباق").resolve(lang)}: $offlineCount ($offlineKb KB)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CleanWhite
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { viewModel.clearSearchHistory() },
                        colors = ButtonDefaults.buttonColors(containerColor = GlassSlate, contentColor = CleanWhite),
                        border = BorderStroke(1.dp, BorderSlate),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.DeleteSweep, contentDescription = null, tint = LuxuryGold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(BiText("Clear Search History", "سرچ ہسٹری صاف کریں").resolve(lang))
                    }
                }
            }

            item {
                GlassIndustrialCard(borderColor = ElectricBlue.copy(alpha = 0.45f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = ElectricBlue)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(BiText("About PetroleumWorld3D", "ایپ کا تعارف اور آرکیٹیکچر").resolve(lang), style = MaterialTheme.typography.titleMedium, color = CleanWhite)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = BiText(
                            "Built natively for Android using Kotlin, Jetpack Compose Material 3, Room SQLite, DataStore, and a zero-dependency OpenGL ES 2.0 3D CAD rendering engine. Supports offline learning, APK/AAB export, and full English/Urdu bilingual technical education with zero paid APIs.",
                            "یہ اینڈرائیڈ ایپ Kotlin، Jetpack Compose، Room Database اور OpenGL ES 3D انجن پر بنائی گئی ہے۔ اس میں انٹرنیٹ کے بغیر مکمل تعلیم، اردو و انگریزی سپورٹ اور APK ایکسپورٹ کی مکمل سہولت موجود ہے۔"
                        ).resolve(lang),
                        style = MaterialTheme.typography.bodySmall,
                        color = CoolSilver
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingSwitchRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    testTag: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.bodyMedium, color = CleanWhite, modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.testTag(testTag),
            colors = SwitchDefaults.colors(checkedThumbColor = DeepNavy, checkedTrackColor = ElectricBlue)
        )
    }
}
