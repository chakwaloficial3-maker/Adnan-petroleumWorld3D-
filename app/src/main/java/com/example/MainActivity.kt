package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.BiText
import com.example.model.Model3DType
import com.example.ui.screens.CompareEquipmentScreen
import com.example.ui.screens.EncyclopediaScreen
import com.example.ui.screens.EngineeringToolsScreen
import com.example.ui.screens.EquipmentDetailScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.HseSafetyScreen
import com.example.ui.screens.LearningCenterScreen
import com.example.ui.screens.Machinery3DHubScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SettingsAboutScreen
import com.example.ui.screens.ToolsTab
import com.example.ui.theme.CleanWhite
import com.example.ui.theme.CoolSilver
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.GlassSlate
import com.example.ui.theme.MidnightBlue
import com.example.ui.theme.PetroleumWorldTheme
import com.example.viewmodel.MainViewModel

sealed interface AppScreen {
    data object Home : AppScreen
    data class Encyclopedia(val categoryId: String? = null) : AppScreen
    data class Machinery3D(val modelType: Model3DType = Model3DType.CENTRIFUGAL_PUMP) : AppScreen
    data object Learning : AppScreen
    data class Tools(val initialTab: ToolsTab = ToolsTab.CALCULATORS) : AppScreen
    data class EquipmentDetail(val equipmentId: String) : AppScreen
    data object Search : AppScreen
    data object HseSafety : AppScreen
    data class Compare(val equipmentId: String? = null) : AppScreen
    data object Settings : AppScreen
}

private enum class TopNavTab(val label: BiText, val icon: ImageVector) {
    HOME(BiText("Home", "ہوم"), Icons.Default.Home),
    ENCYCLOPEDIA(BiText("Library", "لائبریری"), Icons.Default.MenuBook),
    LAB3D(BiText("3D Lab", "3D لیب"), Icons.Default.ViewInAr),
    LEARN(BiText("Learn", "اسباق"), Icons.Default.School),
    TOOLS(BiText("Tools", "ٹولز"), Icons.Default.Calculate)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetroleumWorldTheme {
                PetroleumWorldRootApp()
            }
        }
    }
}

@Composable
fun PetroleumWorldRootApp(viewModel: MainViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lang = uiState.preferences.language

    var backStack by remember { mutableStateOf<List<AppScreen>>(listOf(AppScreen.Home)) }
    var is3dFullScreen by remember { mutableStateOf(false) }

    val currentScreen = backStack.last()

    fun navigateTo(screen: AppScreen) {
        is3dFullScreen = false
        backStack = backStack + screen
    }

    fun switchRootTab(tab: TopNavTab) {
        is3dFullScreen = false
        val root = when (tab) {
            TopNavTab.HOME -> AppScreen.Home
            TopNavTab.ENCYCLOPEDIA -> AppScreen.Encyclopedia(null)
            TopNavTab.LAB3D -> AppScreen.Machinery3D(Model3DType.CENTRIFUGAL_PUMP)
            TopNavTab.LEARN -> AppScreen.Learning
            TopNavTab.TOOLS -> AppScreen.Tools(ToolsTab.CALCULATORS)
        }
        backStack = listOf(root)
    }

    fun popBack() {
        if (is3dFullScreen) {
            is3dFullScreen = false
        } else if (backStack.size > 1) {
            backStack = backStack.dropLast(1)
        }
    }

    BackHandler(enabled = is3dFullScreen || backStack.size > 1) {
        popBack()
    }

    val activeTab = when (currentScreen) {
        is AppScreen.Home -> TopNavTab.HOME
        is AppScreen.Encyclopedia, is AppScreen.EquipmentDetail, is AppScreen.Search -> TopNavTab.ENCYCLOPEDIA
        is AppScreen.Machinery3D -> TopNavTab.LAB3D
        is AppScreen.Learning -> TopNavTab.LEARN
        is AppScreen.Tools, is AppScreen.Compare, is AppScreen.HseSafety, is AppScreen.Settings -> TopNavTab.TOOLS
    }

    val showNavBar = !is3dFullScreen && currentScreen !is AppScreen.EquipmentDetail &&
        currentScreen !is AppScreen.Search && currentScreen !is AppScreen.Compare &&
        currentScreen !is AppScreen.Settings && currentScreen !is AppScreen.HseSafety

    BoxWithConstraints(modifier = Modifier.fillMaxSize().background(DeepNavy)) {
        val useNavRail = maxWidth >= 600.dp

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = DeepNavy,
            contentWindowInsets = WindowInsets.safeDrawing,
            bottomBar = {
                if (showNavBar && !useNavRail) {
                    NavigationBar(
                        containerColor = MidnightBlue,
                        contentColor = CleanWhite,
                        windowInsets = WindowInsets.navigationBars
                    ) {
                        TopNavTab.entries.forEach { tab ->
                            val selected = activeTab == tab
                            NavigationBarItem(
                                selected = selected,
                                onClick = { switchRootTab(tab) },
                                modifier = Modifier.testTag("nav_${tab.name.lowercase()}"),
                                icon = { Icon(tab.icon, contentDescription = tab.label.resolve(lang)) },
                                label = { Text(tab.label.resolve(lang), style = MaterialTheme.typography.labelSmall) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = DeepNavy,
                                    selectedTextColor = ElectricBlue,
                                    indicatorColor = ElectricBlue,
                                    unselectedIconColor = CoolSilver,
                                    unselectedTextColor = CoolSilver
                                )
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (showNavBar && useNavRail) {
                    NavigationRail(
                        containerColor = MidnightBlue,
                        contentColor = CleanWhite,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        TopNavTab.entries.forEach { tab ->
                            val selected = activeTab == tab
                            NavigationRailItem(
                                selected = selected,
                                onClick = { switchRootTab(tab) },
                                icon = { Icon(tab.icon, contentDescription = tab.label.resolve(lang)) },
                                label = { Text(tab.label.resolve(lang), style = MaterialTheme.typography.labelSmall) },
                                colors = NavigationRailItemDefaults.colors(
                                    selectedIconColor = DeepNavy,
                                    selectedTextColor = ElectricBlue,
                                    indicatorColor = ElectricBlue,
                                    unselectedIconColor = CoolSilver,
                                    unselectedTextColor = CoolSilver
                                )
                            )
                        }
                    }
                }

                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    when (val scr = currentScreen) {
                        is AppScreen.Home -> HomeScreen(
                            viewModel = viewModel,
                            uiState = uiState,
                            onOpenSearch = { navigateTo(AppScreen.Search) },
                            onOpenEncyclopedia = { catId -> navigateTo(AppScreen.Encyclopedia(catId)) },
                            onOpen3DModel = { type -> navigateTo(AppScreen.Machinery3D(type)) },
                            onOpenEquipmentDetail = { id -> navigateTo(AppScreen.EquipmentDetail(id)) },
                            onOpenLearning = { navigateTo(AppScreen.Learning) },
                            onOpenCalculators = { navigateTo(AppScreen.Tools(ToolsTab.CALCULATORS)) },
                            onOpenDiagrams = { navigateTo(AppScreen.Tools(ToolsTab.DIAGRAMS)) },
                            onOpenHse = { navigateTo(AppScreen.HseSafety) },
                            onOpenCompare = { navigateTo(AppScreen.Compare(null)) },
                            onOpenSettings = { navigateTo(AppScreen.Settings) }
                        )
                        is AppScreen.Encyclopedia -> EncyclopediaScreen(
                            viewModel = viewModel,
                            uiState = uiState,
                            initialCategoryId = scr.categoryId,
                            onOpenSearch = { navigateTo(AppScreen.Search) },
                            onOpenEquipment = { id -> navigateTo(AppScreen.EquipmentDetail(id)) }
                        )
                        is AppScreen.Machinery3D -> Machinery3DHubScreen(
                            initialModelType = scr.modelType,
                            viewModel = viewModel,
                            uiState = uiState,
                            isFullScreen = is3dFullScreen,
                            onFullScreenChange = { is3dFullScreen = it },
                            onOpenEquipmentDetail = { id -> navigateTo(AppScreen.EquipmentDetail(id)) }
                        )
                        is AppScreen.Learning -> LearningCenterScreen(
                            viewModel = viewModel,
                            uiState = uiState
                        )
                        is AppScreen.Tools -> EngineeringToolsScreen(
                            viewModel = viewModel,
                            uiState = uiState,
                            initialTab = scr.initialTab
                        )
                        is AppScreen.EquipmentDetail -> EquipmentDetailScreen(
                            equipmentId = scr.equipmentId,
                            viewModel = viewModel,
                            uiState = uiState,
                            onBack = { popBack() },
                            onLaunch3D = { type -> navigateTo(AppScreen.Machinery3D(type)) },
                            onNavigateEquipment = { id -> navigateTo(AppScreen.EquipmentDetail(id)) },
                            onCompareEquipment = { id -> navigateTo(AppScreen.Compare(id)) }
                        )
                        is AppScreen.Search -> SearchScreen(
                            viewModel = viewModel,
                            uiState = uiState,
                            onBack = { popBack() },
                            onOpenEquipment = { id -> navigateTo(AppScreen.EquipmentDetail(id)) }
                        )
                        is AppScreen.HseSafety -> HseSafetyScreen(
                            viewModel = viewModel,
                            uiState = uiState,
                            onBack = { popBack() }
                        )
                        is AppScreen.Compare -> CompareEquipmentScreen(
                            initialEquipmentId = scr.equipmentId,
                            viewModel = viewModel,
                            uiState = uiState,
                            onBack = { popBack() }
                        )
                        is AppScreen.Settings -> SettingsAboutScreen(
                            viewModel = viewModel,
                            uiState = uiState,
                            onBack = { popBack() }
                        )
                    }
                }
            }
        }
    }
}
