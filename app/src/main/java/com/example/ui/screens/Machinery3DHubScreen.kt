package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.example.engine3d.Interactive3DViewer
import com.example.model.BiText
import com.example.model.Model3DType
import com.example.ui.components.ScreenTopBar
import com.example.ui.theme.AmberGlow
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
fun Machinery3DHubScreen(
    initialModelType: Model3DType,
    viewModel: MainViewModel,
    uiState: AppUiState,
    isFullScreen: Boolean,
    onFullScreenChange: (Boolean) -> Unit,
    onOpenEquipmentDetail: (String) -> Unit
) {
    val lang = uiState.preferences.language
    var selectedModel by remember(initialModelType) { mutableStateOf(initialModelType) }

    val modelCatalog = remember {
        listOf(
            Triple(Model3DType.CENTRIFUGAL_PUMP, BiText("Centrifugal Pump", "سینٹری فیوگل پمپ"), "eq_centrifugal_pump"),
            Triple(Model3DType.MUD_PUMP, BiText("Triplex Mud Pump", "ٹرائی پلیکس مڈ پمپ"), "eq_mud_pump"),
            Triple(Model3DType.SEPARATOR_3PHASE, BiText("3-Phase Separator", "تھری فیز سیپریٹر"), "eq_separator_3phase"),
            Triple(Model3DType.CHRISTMAS_TREE, BiText("Christmas Tree", "کرسمس ٹری"), "eq_christmas_tree"),
            Triple(Model3DType.DRILLING_RIG, BiText("Drilling Rig", "ڈرلنگ رگ"), "eq_drilling_rig"),
            Triple(Model3DType.GATE_VALVE, BiText("Gate Valve", "گیٹ والو"), "eq_gate_valve"),
            Triple(Model3DType.DISTILLATION_COLUMN, BiText("Distillation Tower", "ڈسٹیلیشن ٹاور"), "eq_distillation_column"),
            Triple(Model3DType.RECIPROCATING_COMPRESSOR, BiText("Gas Compressor", "گیس کمپریسر"), "eq_compressor"),
            Triple(Model3DType.BOP_STACK, BiText("BOP Stack", "بی او پی اسٹیک"), "eq_bop_stack")
        )
    }

    val matchingEqId = modelCatalog.find { it.first == selectedModel }?.third

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
    ) {
        if (!isFullScreen) {
            ScreenTopBar(
                title = BiText("Interactive 3D Machinery", "لائیو 3D مشینری لیب").resolve(lang),
                subtitle = BiText("360° OpenGL • Internal • Exploded • Cutaway", "گھمائیں • اندرونی • کھلا ماڈل • کراس سیکشن").resolve(lang),
                language = lang,
                onToggleLanguage = { viewModel.toggleLanguage() }
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(modelCatalog) { (type, title, _) ->
                    val isSel = selectedModel == type
                    Surface(
                        modifier = Modifier
                            .clickable { selectedModel = type }
                            .testTag("select_3d_${type.name.lowercase()}"),
                        color = if (isSel) ElectricBlue.copy(alpha = 0.22f) else GlassSlate,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, if (isSel) ElectricBlue else BorderSlate)
                    ) {
                        Text(
                            text = title.resolve(lang),
                            style = MaterialTheme.typography.labelLarge,
                            color = if (isSel) ElectricBlue else CoolSilver,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .then(if (isFullScreen) Modifier.padding(8.dp) else Modifier.verticalScroll(rememberScrollState()).padding(16.dp))
        ) {
            Interactive3DViewer(
                modelType = selectedModel,
                language = lang,
                initialAutoRotate = uiState.preferences.autoRotate3d,
                initialShowLabels = uiState.preferences.show3dLabels,
                lowEndMode = uiState.preferences.lowEndPerformanceMode,
                isFullScreen = isFullScreen,
                onToggleFullScreen = { onFullScreenChange(!isFullScreen) },
                modifier = Modifier.fillMaxWidth()
            )

            if (!isFullScreen && matchingEqId != null) {
                Spacer(modifier = Modifier.height(14.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpenEquipmentDetail(matchingEqId) }
                        .testTag("btn_3d_open_encyclopedia"),
                    color = GlassSlate,
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, LuxuryGold.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = BiText("Read Full 14-Section Engineering Guide →", "مکمل 14 حصوں پر مشتمل انجینئرنگ تفصیل پڑھیں →").resolve(lang),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = AmberGlow
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = BiText("Specs, Working Principle, Maintenance, Troubleshooting & Safety", "خصوصیات، کام کا اصول، مینٹیننس اور سیفٹی").resolve(lang),
                            style = MaterialTheme.typography.bodySmall,
                            color = CleanWhite
                        )
                    }
                }
            }
        }
    }
}
