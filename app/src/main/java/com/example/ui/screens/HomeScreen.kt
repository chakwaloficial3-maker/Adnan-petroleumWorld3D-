package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Schema
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.AppLanguage
import com.example.model.BiText
import com.example.model.EquipmentItem
import com.example.model.Model3DType
import com.example.ui.components.EquipmentListCard
import com.example.ui.components.GlassIndustrialCard
import com.example.ui.components.LanguagePillSwitch
import com.example.ui.components.SectionHeaderRow
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BorderSlate
import com.example.ui.theme.CleanWhite
import com.example.ui.theme.CoolSilver
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.GlassSlate
import com.example.ui.theme.HazardCrimson
import com.example.ui.theme.LuxuryGold
import com.example.ui.theme.MidnightBlue
import com.example.ui.theme.SafetyEmerald
import com.example.viewmodel.AppUiState
import com.example.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    uiState: AppUiState,
    onOpenSearch: () -> Unit,
    onOpenEncyclopedia: (String?) -> Unit,
    onOpen3DModel: (Model3DType) -> Unit,
    onOpenEquipmentDetail: (String) -> Unit,
    onOpenLearning: () -> Unit,
    onOpenCalculators: () -> Unit,
    onOpenDiagrams: () -> Unit,
    onOpenHse: () -> Unit,
    onOpenCompare: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val lang = uiState.preferences.language
    val repo = viewModel.repository
    val featured3D = repo.get3DEquipment()
    val popularEquipment = repo.getPopularEquipment()
    val recentItems = uiState.recentIds.mapNotNull { repo.getEquipmentById(it) }
    val favoriteItems = uiState.favoriteIds.mapNotNull { repo.getEquipmentById(it) }
    val dailyFact = viewModel.currentDailyFact()

    val featuredCategoryCards = listOf(
        Triple("cat_drilling_rigs", R.drawable.img_hero_rig, BiText("Drilling & Rigs", "ڈرلنگ اور رگز")),
        Triple("cat_production", R.drawable.img_wellhead_production, BiText("Production & Wells", "پروڈکشن اور کنویں")),
        Triple("cat_refinery", R.drawable.img_refinery_complex, BiText("Refining & Units", "ریفائنری اور یونٹس")),
        Triple("cat_hse", R.drawable.img_hse_safety, BiText("HSE & Safety", "ایچ ایس ای سیفٹی"))
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Top Branding Bar + Language Selector + Settings
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.app_icon_petroleum),
                    contentDescription = "PetroleumWorld3D Logo",
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, LuxuryGold.copy(alpha = 0.6f), RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "PetroleumWorld3D",
                        style = MaterialTheme.typography.titleLarge,
                        color = CleanWhite
                    )
                    Text(
                        text = BiText("Oil & Gas 3D Encyclopedia", "آئل اینڈ گیس 3D انسائیکلوپیڈیا").resolve(lang),
                        style = MaterialTheme.typography.labelSmall,
                        color = ElectricBlue
                    )
                }
                LanguagePillSwitch(language = lang, onToggle = { viewModel.toggleLanguage() })
                Spacer(modifier = Modifier.width(6.dp))
                IconButton(
                    onClick = onOpenSettings,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(GlassSlate)
                        .testTag("btn_open_settings")
                ) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings", tint = CoolSilver)
                }
            }
        }

        // 2. Smart Search Bar Trigger
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(onClick = onOpenSearch)
                    .testTag("home_search_bar"),
                color = GlassSlate,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, ElectricBlue.copy(alpha = 0.45f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = ElectricBlue)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = BiText(
                            "Search rigs, pumps, valves, Urdu/English terms...",
                            "مشینری، پمپ، والوز، اردو یا انگریزی اصطلاحات تلاش کریں..."
                        ).resolve(lang),
                        style = MaterialTheme.typography.bodyMedium,
                        color = CoolSilver,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        // 3. Cinematic Hero Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(215.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .border(
                        1.dp,
                        Brush.linearGradient(listOf(ElectricBlue.copy(alpha = 0.6f), LuxuryGold.copy(alpha = 0.6f))),
                        RoundedCornerShape(22.dp)
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_hero_rig),
                    contentDescription = "Offshore Oil Rig Hero",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    DeepNavy.copy(alpha = 0.20f),
                                    DeepNavy.copy(alpha = 0.72f),
                                    DeepNavy.copy(alpha = 0.95f)
                                )
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(18.dp)
                ) {
                    Surface(
                        color = LuxuryGold.copy(alpha = 0.22f),
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, LuxuryGold.copy(alpha = 0.7f))
                    ) {
                        Text(
                            text = BiText("20 CATEGORIES • 9 REAL 3D MODELS", "20 کیٹیگریز • 9 لائیو 3D ماڈلز").resolve(lang),
                            style = MaterialTheme.typography.labelSmall,
                            color = AmberGlow,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = BiText(
                            "Inspect Oil & Gas Machinery in Interactive 3D",
                            "تیل اور گیس کی جدید مشینری کا 3D مشاہدہ کریں"
                        ).resolve(lang),
                        style = MaterialTheme.typography.headlineSmall,
                        color = CleanWhite
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = { onOpen3DModel(Model3DType.CENTRIFUGAL_PUMP) },
                            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = DeepNavy),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("hero_launch_3d_button")
                        ) {
                            Icon(Icons.Default.ViewInAr, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = BiText("Open 3D Lab", "3D لیب کھولیں").resolve(lang),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Button(
                            onClick = { onOpenEncyclopedia(null) },
                            colors = ButtonDefaults.buttonColors(containerColor = GlassSlate, contentColor = CleanWhite),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, BorderSlate),
                            modifier = Modifier.testTag("hero_browse_encyclopedia_button")
                        ) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(BiText("20 Topics", "20 موضوعات").resolve(lang))
                        }
                    }
                }
            }
        }

        // 4. Daily Petroleum Fact
        item {
            GlassIndustrialCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                borderColor = LuxuryGold.copy(alpha = 0.45f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Lightbulb, contentDescription = null, tint = LuxuryGold, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${BiText("Daily Petroleum Fact", "آج کی پیٹرولیم معلومات").resolve(lang)} • ${dailyFact.categoryTag.resolve(lang)}",
                            style = MaterialTheme.typography.labelMedium,
                            color = LuxuryGold
                        )
                    }
                    Text(
                        text = BiText("Next →", "اگلا →").resolve(lang),
                        style = MaterialTheme.typography.labelMedium,
                        color = ElectricBlue,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { viewModel.nextDailyFact() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = dailyFact.title.resolve(lang),
                    style = MaterialTheme.typography.titleMedium,
                    color = CleanWhite
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = dailyFact.fact.resolve(lang),
                    style = MaterialTheme.typography.bodySmall,
                    color = CoolSilver
                )
            }
        }

        // 5. Main Category Cards with Generated Industrial Imagery
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SectionHeaderRow(
                    title = BiText("Industry Sectors (20 Categories)", "انڈسٹری سیکٹرز (20 کیٹیگریز)").resolve(lang),
                    actionText = BiText("All 20 →", "تمام 20 →").resolve(lang),
                    icon = Icons.Default.AutoAwesome,
                    onAction = { onOpenEncyclopedia(null) }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(featuredCategoryCards) { (catId, imgRes, title) ->
                    Box(
                        modifier = Modifier
                            .width(176.dp)
                            .height(126.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .border(1.dp, BorderSlate, RoundedCornerShape(18.dp))
                            .clickable { onOpenEncyclopedia(catId) }
                            .testTag("home_cat_card_$catId")
                    ) {
                        Image(
                            painter = painterResource(id = imgRes),
                            contentDescription = title.resolve(lang),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color.Transparent, DeepNavy.copy(alpha = 0.92f))
                                    )
                                )
                        )
                        Text(
                            text = title.resolve(lang),
                            style = MaterialTheme.typography.titleSmall,
                            color = CleanWhite,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp)
                        )
                    }
                }
            }
        }

        // 6. Featured 3D Machinery Carousel
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SectionHeaderRow(
                    title = BiText("Featured 3D Machinery", "نمایاں 3D مشینری").resolve(lang),
                    actionText = BiText("3D Hub →", "3D حب →").resolve(lang),
                    icon = Icons.Default.ViewInAr,
                    onAction = { onOpen3DModel(Model3DType.CENTRIFUGAL_PUMP) }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(featured3D) { item ->
                    GlassIndustrialCard(
                        modifier = Modifier
                            .width(220.dp)
                            .testTag("home_3d_card_${item.id}"),
                        borderColor = ElectricBlue.copy(alpha = 0.55f),
                        onClick = { item.model3dType?.let(onOpen3DModel) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = ElectricBlue.copy(alpha = 0.16f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "360° CAD",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ElectricBlue,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                            Icon(Icons.Default.ViewInAr, contentDescription = null, tint = LuxuryGold, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = item.name.resolve(lang),
                            style = MaterialTheme.typography.titleMedium,
                            color = CleanWhite,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${item.mainComponents.size} ${BiText("interactive parts", "انٹرایکٹو پرزے").resolve(lang)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = AmberGlow
                        )
                    }
                }
            }
        }

        // 7. Quick Functional Hubs Grid (Learning, Calculators, HSE, Diagrams, Compare)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SectionHeaderRow(
                    title = BiText("Engineering & Learning Suite", "انجینئرنگ اور لرننگ سینٹر").resolve(lang)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickHubTile(
                        title = BiText("Learning Center", "لرننگ سینٹر").resolve(lang),
                        subtitle = BiText("Lessons, Quiz, Urdu Dict", "اسباق، کوئز، ڈکشنری").resolve(lang),
                        icon = Icons.Default.School,
                        accent = ElectricBlue,
                        modifier = Modifier.weight(1f),
                        testTag = "hub_learning",
                        onClick = onOpenLearning
                    )
                    QuickHubTile(
                        title = BiText("Calculators", "کیلکولیٹرز").resolve(lang),
                        subtitle = BiText("Mud, API, HHP, Units", "مڈ، API، پمپ فارمولے").resolve(lang),
                        icon = Icons.Default.Calculate,
                        accent = LuxuryGold,
                        modifier = Modifier.weight(1f),
                        testTag = "hub_calculators",
                        onClick = onOpenCalculators
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickHubTile(
                        title = BiText("HSE & Safety", "ایچ ایس ای سیفٹی").resolve(lang),
                        subtitle = BiText("H2S, PPE, Fire, PTW", "گیس سیفٹی، فائر، LOTO").resolve(lang),
                        icon = Icons.Default.HealthAndSafety,
                        accent = HazardCrimson,
                        modifier = Modifier.weight(1f),
                        testTag = "hub_hse",
                        onClick = onOpenHse
                    )
                    QuickHubTile(
                        title = BiText("2D Diagrams", "ٹیکنیکل ڈایاگرامز").resolve(lang),
                        subtitle = BiText("P&ID & Schematics", "پروسیس فلو خاکے").resolve(lang),
                        icon = Icons.Default.Schema,
                        accent = SafetyEmerald,
                        modifier = Modifier.weight(1f),
                        testTag = "hub_diagrams",
                        onClick = onOpenDiagrams
                    )
                }
                QuickHubTile(
                    title = BiText("Compare Equipment Side-by-Side", "مشینری کا موازنہ کریں").resolve(lang),
                    subtitle = BiText("Compare specs, principles & applications", "اہم خصوصیات اور اصولوں کا تقابل").resolve(lang),
                    icon = Icons.Default.CompareArrows,
                    accent = ElectricBlue,
                    modifier = Modifier.fillMaxWidth(),
                    testTag = "hub_compare",
                    onClick = onOpenCompare
                )
            }
        }

        // 8. Recently Viewed Equipment (if any)
        if (recentItems.isNotEmpty()) {
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    SectionHeaderRow(
                        title = BiText("Recently Viewed", "حال ہی میں دیکھے گئے").resolve(lang),
                        icon = Icons.Default.History
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(recentItems) { item ->
                        CompactEquipmentChip(item, lang) { onOpenEquipmentDetail(item.id) }
                    }
                }
            }
        }

        // 9. Favorites (if any)
        if (favoriteItems.isNotEmpty()) {
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    SectionHeaderRow(
                        title = BiText("Saved Favorites", "محفوظ شدہ پسندیدہ").resolve(lang),
                        icon = Icons.Default.Bookmark
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(favoriteItems) { item ->
                        CompactEquipmentChip(item, lang) { onOpenEquipmentDetail(item.id) }
                    }
                }
            }
        }

        // 10. Popular Equipment Section
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SectionHeaderRow(
                    title = BiText("Popular Equipment", "مقبول مشینری اور آلات").resolve(lang),
                    actionText = BiText("See All", "سب دیکھیں").resolve(lang),
                    onAction = { onOpenEncyclopedia(null) }
                )
            }
        }

        items(popularEquipment.take(6)) { item ->
            val catName = viewModel.getCategory(item.categoryId)?.title?.resolve(lang) ?: ""
            EquipmentListCard(
                item = item,
                categoryTitle = catName,
                language = lang,
                isFavorite = item.id in uiState.favoriteIds,
                onToggleFavorite = { viewModel.toggleFavorite(item.id) },
                onClick = { onOpenEquipmentDetail(item.id) },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun QuickHubTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accent: Color,
    testTag: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    GlassIndustrialCard(
        modifier = modifier.testTag(testTag),
        borderColor = accent.copy(alpha = 0.42f),
        onClick = onClick
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accent.copy(alpha = 0.16f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = accent, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleSmall, color = CleanWhite, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = CoolSilver, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Composable
private fun CompactEquipmentChip(
    item: EquipmentItem,
    language: AppLanguage,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(175.dp)
            .clickable(onClick = onClick),
        color = GlassSlate,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, BorderSlate)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = item.name.resolve(language),
                style = MaterialTheme.typography.titleSmall,
                color = CleanWhite,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.shortTagline.resolve(language),
                style = MaterialTheme.typography.bodySmall,
                color = CoolSilver,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
