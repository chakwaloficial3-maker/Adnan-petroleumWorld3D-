package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.BiText
import com.example.ui.components.EquipmentListCard
import com.example.ui.components.GlassIndustrialCard
import com.example.ui.components.ScreenTopBar
import com.example.ui.theme.BorderSlate
import com.example.ui.theme.CleanWhite
import com.example.ui.theme.CoolSilver
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.GlassSlate
import com.example.ui.theme.LuxuryGold
import com.example.viewmodel.AppUiState
import com.example.viewmodel.MainViewModel

@Composable
fun EncyclopediaScreen(
    viewModel: MainViewModel,
    uiState: AppUiState,
    initialCategoryId: String?,
    onOpenSearch: () -> Unit,
    onOpenEquipment: (String) -> Unit
) {
    val lang = uiState.preferences.language
    val repo = viewModel.repository
    var selectedCategoryId by remember(initialCategoryId) { mutableStateOf(initialCategoryId) }

    val selectedCategory = selectedCategoryId?.let { repo.getCategoryById(it) }
    val equipmentList = remember(selectedCategoryId) {
        if (selectedCategoryId == null) repo.allEquipment
        else repo.getEquipmentByCategory(selectedCategoryId!!)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
    ) {
        ScreenTopBar(
            title = selectedCategory?.title?.resolve(lang)
                ?: BiText("Petroleum Encyclopedia", "پیٹرولیم انسائیکلوپیڈیا").resolve(lang),
            subtitle = BiText("20 Major Oil & Gas Categories", "20 بنیادی آئل اینڈ گیس کیٹیگریز").resolve(lang),
            language = lang,
            onToggleLanguage = { viewModel.toggleLanguage() },
            onBack = if (selectedCategoryId != null) {
                { selectedCategoryId = null }
            } else null,
            trailingAction = {
                IconButton(onClick = onOpenSearch) {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = ElectricBlue)
                }
            }
        )

        // Category Quick Filter Strip
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedCategoryId == null,
                    onClick = { selectedCategoryId = null },
                    label = { Text(BiText("All 20 Topics", "تمام 20 کیٹیگریز").resolve(lang)) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ElectricBlue.copy(alpha = 0.22f),
                        selectedLabelColor = ElectricBlue,
                        containerColor = GlassSlate,
                        labelColor = CoolSilver
                    )
                )
            }
            items(repo.categories) { cat ->
                val isSel = selectedCategoryId == cat.id
                FilterChip(
                    selected = isSel,
                    onClick = { selectedCategoryId = if (isSel) null else cat.id },
                    label = { Text("${cat.number}. ${cat.title.resolve(lang)}") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = LuxuryGold.copy(alpha = 0.22f),
                        selectedLabelColor = LuxuryGold,
                        containerColor = GlassSlate,
                        labelColor = CoolSilver
                    )
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (selectedCategoryId == null) {
                // Show all 20 Categories Directory first
                items(repo.categories) { category ->
                    val count = repo.getEquipmentByCategory(category.id).size
                    val accent = Color(category.accentColorHex)
                    GlassIndustrialCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("category_row_${category.id}"),
                        borderColor = accent.copy(alpha = 0.4f),
                        onClick = { selectedCategoryId = category.id }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(accent.copy(alpha = 0.16f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = category.number.toString().padStart(2, '0'),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = accent
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        color = GlassSlate,
                                        shape = RoundedCornerShape(6.dp),
                                        border = BorderStroke(1.dp, BorderSlate)
                                    ) {
                                        Text(
                                            text = category.sectorTag.resolve(lang),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = accent,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Text(
                                        text = "$count ${BiText("entries", "اندراجات").resolve(lang)}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = CoolSilver
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = category.title.resolve(lang),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = CleanWhite
                                )
                                Text(
                                    text = category.subtitle.resolve(lang),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = CoolSilver
                                )
                            }
                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = CoolSilver)
                        }
                    }
                }
            } else {
                // Show equipment inside selected category
                items(equipmentList) { item ->
                    val catName = selectedCategory?.title?.resolve(lang) ?: ""
                    EquipmentListCard(
                        item = item,
                        categoryTitle = catName,
                        language = lang,
                        isFavorite = item.id in uiState.favoriteIds,
                        onToggleFavorite = { viewModel.toggleFavorite(item.id) },
                        onClick = { onOpenEquipment(item.id) }
                    )
                }
            }
        }
    }
}
