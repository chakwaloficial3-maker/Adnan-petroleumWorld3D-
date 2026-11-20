package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.BiText
import com.example.ui.components.GlassIndustrialCard
import com.example.ui.components.ScreenTopBar
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BorderSlate
import com.example.ui.theme.CleanWhite
import com.example.ui.theme.CoolSilver
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.HazardCrimson
import com.example.ui.theme.SafetyEmerald
import com.example.viewmodel.AppUiState
import com.example.viewmodel.MainViewModel

@Composable
fun HseSafetyScreen(
    viewModel: MainViewModel,
    uiState: AppUiState,
    onBack: () -> Unit
) {
    val lang = uiState.preferences.language

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
    ) {
        ScreenTopBar(
            title = BiText("HSE & Safety Center", "ایچ ایس ای اور سیفٹی سینٹر").resolve(lang),
            subtitle = BiText("Zero Harm • H2S • Fire • PPE • PTW", "حفاظتی اصول • گیس الرٹ • فائر سیفٹی").resolve(lang),
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .border(1.dp, HazardCrimson.copy(alpha = 0.55f), RoundedCornerShape(18.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_hse_safety),
                        contentDescription = "HSE Industrial Safety",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.verticalGradient(listOf(DeepNavy.copy(alpha = 0.1f), DeepNavy.copy(alpha = 0.92f))))
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.HealthAndSafety, contentDescription = null, tint = HazardCrimson, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = BiText("Stop Work Authority (SWA)", "کام روکنے کا اختیار (Stop Work Authority)").resolve(lang),
                                style = MaterialTheme.typography.titleMedium,
                                color = CleanWhite
                            )
                            Text(
                                text = BiText("Every worker has the right & duty to stop unsafe operations.", "ہر ورکر کو غیر محفوظ کام فوری روکنے کا حق اور فرض حاصل ہے۔").resolve(lang),
                                style = MaterialTheme.typography.bodySmall,
                                color = AmberGlow
                            )
                        }
                    }
                }
            }

            items(viewModel.repository.hseTopics) { topic ->
                GlassIndustrialCard(borderColor = HazardCrimson.copy(alpha = 0.35f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Icon(Icons.Default.WarningAmber, contentDescription = null, tint = HazardCrimson, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(topic.title.resolve(lang), style = MaterialTheme.typography.titleMedium, color = CleanWhite)
                        }
                        Surface(
                            color = HazardCrimson.copy(alpha = 0.18f),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, HazardCrimson.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = topic.severityLabel.resolve(lang),
                                style = MaterialTheme.typography.labelSmall,
                                color = HazardCrimson,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(topic.overview.resolve(lang), style = MaterialTheme.typography.bodyMedium, color = CoolSilver)
                    Spacer(modifier = Modifier.height(10.dp))

                    topic.checklist.forEach { item ->
                        Row(modifier = Modifier.padding(vertical = 3.dp)) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 7.dp)
                                    .size(6.dp)
                                    .background(SafetyEmerald, RoundedCornerShape(3.dp))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(item.resolve(lang), style = MaterialTheme.typography.bodySmall, color = CleanWhite)
                        }
                    }

                    if (topic.criticalLimits.isNotEmpty()) {
                        HorizontalDivider(color = BorderSlate, modifier = Modifier.padding(vertical = 8.dp))
                        topic.criticalLimits.forEach { (label, valText) ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(label.resolve(lang), style = MaterialTheme.typography.labelSmall, color = ElectricBlue)
                                Text(valText, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = AmberGlow)
                            }
                        }
                    }
                }
            }
        }
    }
}
