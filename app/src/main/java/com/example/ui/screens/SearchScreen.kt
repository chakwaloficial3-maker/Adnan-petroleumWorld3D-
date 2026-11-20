package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.model.BiText
import com.example.ui.components.EquipmentListCard
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    viewModel: MainViewModel,
    uiState: AppUiState,
    onBack: () -> Unit,
    onOpenEquipment: (String) -> Unit
) {
    val lang = uiState.preferences.language
    val repo = viewModel.repository
    var query by remember { mutableStateOf("") }
    var only3D by remember { mutableStateOf(false) }

    val results = remember(query, only3D) {
        repo.searchEquipment(query = query, only3D = only3D)
    }
    val dictMatches = remember(query) {
        if (query.trim().length < 2) emptyList()
        else repo.dictionaryTerms.filter {
            it.termEn.contains(query.trim(), true) ||
                it.termUr.contains(query.trim(), true) ||
                it.definition.matches(query)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
    ) {
        ScreenTopBar(
            title = BiText("Smart Search", "اسمارٹ تلاش").resolve(lang),
            subtitle = BiText("English & Urdu • Equipment, Parts & Terms", "انگریزی اور اردو • مشینری، پرزے اور اصطلاحات").resolve(lang),
            language = lang,
            onToggleLanguage = { viewModel.toggleLanguage() },
            onBack = onBack
        )

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("smart_search_input"),
            placeholder = {
                Text(
                    BiText("Type impeller, BOP, مڈ پمپ, H2S, distillation...", "امپیلر، بی او پی، مڈ پمپ، H2S لکھیں...").resolve(lang),
                    color = CoolSilver
                )
            },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ElectricBlue) },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear search", tint = CoolSilver)
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { viewModel.recordSearch(query) }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ElectricBlue,
                unfocusedBorderColor = BorderSlate,
                focusedContainerColor = GlassSlate,
                unfocusedContainerColor = GlassSlate,
                focusedTextColor = CleanWhite,
                unfocusedTextColor = CleanWhite
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = only3D,
                onClick = { only3D = !only3D },
                label = { Text(BiText("3D Models Only", "صرف 3D ماڈلز").resolve(lang)) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = LuxuryGold.copy(alpha = 0.22f),
                    selectedLabelColor = LuxuryGold,
                    containerColor = GlassSlate,
                    labelColor = CoolSilver
                )
            )
            Text(
                text = "${results.size} ${BiText("matches", "نتائج").resolve(lang)}",
                style = MaterialTheme.typography.labelMedium,
                color = CoolSilver
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Search History Chips
            if (query.isBlank() && uiState.searchHistory.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.History, contentDescription = null, tint = LuxuryGold)
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(BiText("Recent Searches", "حالیہ تلاش").resolve(lang), style = MaterialTheme.typography.titleSmall, color = CleanWhite)
                        }
                        Text(
                            text = BiText("Clear", "صاف کریں").resolve(lang),
                            style = MaterialTheme.typography.labelMedium,
                            color = ElectricBlue,
                            modifier = Modifier.clickable { viewModel.clearSearchHistory() }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        uiState.searchHistory.forEach { past ->
                            Surface(
                                modifier = Modifier.clickable { query = past },
                                color = GlassSlate,
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, BorderSlate)
                            ) {
                                Text(past, style = MaterialTheme.typography.labelMedium, color = CleanWhite, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
                            }
                        }
                    }
                }
            }

            // Matching Technical Dictionary Terms
            if (dictMatches.isNotEmpty()) {
                item {
                    Text(
                        text = BiText("Technical Dictionary Matches", "ڈکشنری اصطلاحات").resolve(lang),
                        style = MaterialTheme.typography.titleSmall,
                        color = LuxuryGold
                    )
                }
                items(dictMatches.take(4)) { term ->
                    Surface(
                        color = GlassSlate,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, LuxuryGold.copy(alpha = 0.35f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("${term.termEn} • ${term.termUr}", style = MaterialTheme.typography.titleSmall, color = LuxuryGold)
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(term.definition.resolve(lang), style = MaterialTheme.typography.bodySmall, color = CleanWhite)
                        }
                    }
                }
            }

            // Equipment Results
            items(results) { item ->
                val catName = viewModel.getCategory(item.categoryId)?.title?.resolve(lang) ?: ""
                EquipmentListCard(
                    item = item,
                    categoryTitle = catName,
                    language = lang,
                    isFavorite = item.id in uiState.favoriteIds,
                    onToggleFavorite = { viewModel.toggleFavorite(item.id) },
                    onClick = {
                        if (query.isNotBlank()) viewModel.recordSearch(query)
                        onOpenEquipment(item.id)
                    }
                )
            }
        }
    }
}
