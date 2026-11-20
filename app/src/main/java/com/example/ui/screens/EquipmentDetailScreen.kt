package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SettingsInputComponent
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.AppLanguage
import com.example.model.BiText
import com.example.model.Model3DType
import com.example.ui.components.GlassIndustrialCard
import com.example.ui.components.ScreenTopBar
import com.example.ui.components.TechnicalDiagramCard
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BorderSlate
import com.example.ui.theme.CautionOrange
import com.example.ui.theme.CleanWhite
import com.example.ui.theme.CoolSilver
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.GlassSlate
import com.example.ui.theme.HazardCrimson
import com.example.ui.theme.LuxuryGold
import com.example.ui.theme.SafetyEmerald
import com.example.viewmodel.AppUiState
import com.example.viewmodel.MainViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EquipmentDetailScreen(
    equipmentId: String,
    viewModel: MainViewModel,
    uiState: AppUiState,
    onBack: () -> Unit,
    onLaunch3D: (Model3DType) -> Unit,
    onNavigateEquipment: (String) -> Unit,
    onCompareEquipment: (String) -> Unit
) {
    val lang = uiState.preferences.language
    val item = viewModel.getEquipment(equipmentId)

    LaunchedEffect(equipmentId) {
        if (item != null) {
            viewModel.recordEquipmentView(equipmentId)
        }
    }

    if (item == null) {
        Column(modifier = Modifier.fillMaxSize().background(DeepNavy)) {
            ScreenTopBar(
                title = BiText("Not Found", "موجود نہیں").resolve(lang),
                language = lang,
                onToggleLanguage = { viewModel.toggleLanguage() },
                onBack = onBack
            )
        }
        return
    }

    val categoryTitle = viewModel.getCategory(item.categoryId)?.title?.resolve(lang) ?: ""
    val isFav = item.id in uiState.favoriteIds

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
    ) {
        ScreenTopBar(
            title = item.name.resolve(lang),
            subtitle = categoryTitle,
            language = lang,
            onToggleLanguage = { viewModel.toggleLanguage() },
            onBack = onBack,
            trailingAction = {
                IconButton(
                    onClick = { viewModel.toggleFavorite(item.id) },
                    modifier = Modifier.testTag("detail_favorite_btn")
                ) {
                    Icon(
                        imageVector = if (isFav) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Favorite",
                        tint = if (isFav) LuxuryGold else CleanWhite
                    )
                }
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Hero Summary & Action Bar (Launch 3D + Compare)
            item {
                GlassIndustrialCard(
                    borderColor = if (item.has3dModel) ElectricBlue.copy(alpha = 0.65f) else BorderSlate
                ) {
                    Text(
                        text = item.shortTagline.resolve(lang),
                        style = MaterialTheme.typography.titleMedium,
                        color = AmberGlow
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (item.has3dModel && item.model3dType != null) {
                            Button(
                                onClick = { onLaunch3D(item.model3dType) },
                                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = DeepNavy),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f).testTag("detail_launch_3d_btn")
                            ) {
                                Icon(Icons.Default.ViewInAr, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(BiText("Interactive 3D", "لائیو 3D دیکھیں").resolve(lang), fontWeight = FontWeight.Bold)
                            }
                        }
                        OutlinedButton(
                            onClick = { onCompareEquipment(item.id) },
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, LuxuryGold.copy(alpha = 0.7f)),
                            modifier = Modifier.weight(1f).testTag("detail_compare_btn")
                        ) {
                            Icon(Icons.Default.CompareArrows, contentDescription = null, tint = LuxuryGold, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(BiText("Compare", "موازنہ").resolve(lang), color = LuxuryGold)
                        }
                    }
                }
            }

            // 13. Technical Diagram (clearly labeled 2D Schematic vs 3D)
            item {
                TechnicalDiagramCard(
                    diagramType = item.diagramType,
                    language = lang,
                    has3dModel = item.has3dModel
                )
            }

            // 1. Definition
            item {
                DetailSectionCard(
                    title = BiText("1. Definition", "1. تعریف (Definition)"),
                    body = item.definition,
                    language = lang
                )
            }

            // 2. Purpose and Function
            item {
                DetailSectionCard(
                    title = BiText("2. Purpose & Function", "2. مقصد اور کام"),
                    body = item.purposeAndFunction,
                    language = lang
                )
            }

            // 3. Working Principle
            item {
                DetailSectionCard(
                    title = BiText("3. Working Principle", "3. کام کرنے کا اصول (Working Principle)"),
                    body = item.workingPrinciple,
                    language = lang,
                    accent = ElectricBlue
                )
            }

            // 4. Construction
            item {
                DetailSectionCard(
                    title = BiText("4. Construction", "4. ساخت (Construction)"),
                    body = item.construction,
                    language = lang
                )
            }

            // 5. Main Components
            item {
                GlassIndustrialCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.SettingsInputComponent, contentDescription = null, tint = LuxuryGold, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = BiText("5. Main Components", "5. اہم پرزے (Main Components)").resolve(lang),
                            style = MaterialTheme.typography.titleMedium,
                            color = LuxuryGold
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    item.mainComponents.forEachIndexed { idx, comp ->
                        if (idx > 0) HorizontalDivider(color = BorderSlate.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 8.dp))
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(comp.name.resolve(lang), style = MaterialTheme.typography.titleSmall, color = CleanWhite)
                                Surface(color = ElectricBlue.copy(alpha = 0.14f), shape = RoundedCornerShape(6.dp)) {
                                    Text(
                                        text = comp.material,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = ElectricBlue,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(comp.functionDesc.resolve(lang), style = MaterialTheme.typography.bodySmall, color = CoolSilver)
                        }
                    }
                }
            }

            // 6. Types and Classification
            item {
                BulletListSectionCard(
                    title = BiText("6. Types & Classification", "6. اقسام اور درجہ بندی"),
                    items = item.typesAndClassification,
                    language = lang,
                    accent = ElectricBlue
                )
            }

            // 7. Applications
            item {
                BulletListSectionCard(
                    title = BiText("7. Industrial Applications", "7. صنعتی استعمال (Applications)"),
                    items = item.applications,
                    language = lang,
                    accent = SafetyEmerald
                )
            }

            // 8. Technical Specifications
            item {
                GlassIndustrialCard {
                    Text(
                        text = BiText("8. Technical Specifications", "8. تکنیکی تفصیلات (Specifications)").resolve(lang),
                        style = MaterialTheme.typography.titleMedium,
                        color = ElectricBlue
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    item.specifications.forEachIndexed { index, spec ->
                        if (index > 0) HorizontalDivider(color = BorderSlate.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(spec.label.resolve(lang), style = MaterialTheme.typography.bodySmall, color = CoolSilver, modifier = Modifier.weight(1f))
                            Text(spec.value, style = MaterialTheme.typography.labelMedium, color = AmberGlow)
                        }
                    }
                }
            }

            // 9. Maintenance Concepts
            item {
                BulletListSectionCard(
                    title = BiText("9. Maintenance Concepts", "9. دیکھ بھال اور مینٹیننس"),
                    items = item.maintenanceConcepts,
                    language = lang,
                    accent = LuxuryGold,
                    icon = Icons.Default.Build
                )
            }

            // 10. Common Problems
            item {
                BulletListSectionCard(
                    title = BiText("10. Common Problems & Troubleshooting", "10. عام خرابیاں اور حل"),
                    items = item.commonProblems,
                    language = lang,
                    accent = CautionOrange,
                    icon = Icons.Default.ReportProblem
                )
            }

            // 11. Safety Precautions
            item {
                BulletListSectionCard(
                    title = BiText("11. Safety Precautions", "11. حفاظتی تدابیر (Safety)"),
                    items = item.safetyPrecautions,
                    language = lang,
                    accent = HazardCrimson,
                    icon = Icons.Default.Security
                )
            }

            // 12. Related Equipment
            if (item.relatedIds.isNotEmpty()) {
                item {
                    GlassIndustrialCard {
                        Text(
                            text = BiText("12. Related Equipment", "12. متعلقہ مشینری اور آلات").resolve(lang),
                            style = MaterialTheme.typography.titleMedium,
                            color = CleanWhite
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            item.relatedIds.mapNotNull { viewModel.getEquipment(it) }.forEach { rel ->
                                Surface(
                                    modifier = Modifier.clickable { onNavigateEquipment(rel.id) },
                                    color = ElectricBlue.copy(alpha = 0.14f),
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, ElectricBlue.copy(alpha = 0.4f))
                                ) {
                                    Text(
                                        text = rel.name.resolve(lang),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = ElectricBlue,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 14. References & Further Learning
            item {
                GlassIndustrialCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.MenuBook, contentDescription = null, tint = CoolSilver, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = BiText("13. Standards & References", "13. معیارات اور حوالہ جات (API/ASME/ASTM)").resolve(lang),
                            style = MaterialTheme.typography.titleMedium,
                            color = CleanWhite
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    item.references.forEach { ref ->
                        Text("• $ref", style = MaterialTheme.typography.bodySmall, color = CoolSilver, modifier = Modifier.padding(vertical = 2.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailSectionCard(
    title: BiText,
    body: BiText,
    language: AppLanguage,
    accent: Color = LuxuryGold
) {
    GlassIndustrialCard {
        Text(text = title.resolve(language), style = MaterialTheme.typography.titleMedium, color = accent)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = body.resolve(language), style = MaterialTheme.typography.bodyMedium, color = CleanWhite)
    }
}

@Composable
private fun BulletListSectionCard(
    title: BiText,
    items: List<BiText>,
    language: AppLanguage,
    accent: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    GlassIndustrialCard(borderColor = accent.copy(alpha = 0.38f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(icon, contentDescription = null, tint = accent, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(text = title.resolve(language), style = MaterialTheme.typography.titleMedium, color = accent)
        }
        Spacer(modifier = Modifier.height(8.dp))
        items.forEach { entry ->
            Row(modifier = Modifier.padding(vertical = 3.dp)) {
                Box(
                    modifier = Modifier
                        .padding(top = 7.dp)
                        .size(6.dp)
                        .background(accent, RoundedCornerShape(3.dp))
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = entry.resolve(language), style = MaterialTheme.typography.bodyMedium, color = CleanWhite)
            }
        }
    }
}
