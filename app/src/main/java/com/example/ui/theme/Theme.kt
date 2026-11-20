package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val PetroleumDarkColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = DeepNavy,
    primaryContainer = ElevatedSteel,
    onPrimaryContainer = CleanWhite,
    secondary = LuxuryGold,
    onSecondary = DeepNavy,
    secondaryContainer = ColorTokens.GoldContainer,
    onSecondaryContainer = AmberGlow,
    tertiary = RoyalBlue,
    onTertiary = CleanWhite,
    background = DeepNavy,
    onBackground = CleanWhite,
    surface = MidnightBlue,
    onSurface = CleanWhite,
    surfaceVariant = GlassSlate,
    onSurfaceVariant = CoolSilver,
    outline = BorderSlate,
    outlineVariant = MutedSteel,
    error = HazardCrimson,
    onError = CleanWhite
)

private object ColorTokens {
    val GoldContainer = androidx.compose.ui.graphics.Color(0xFF33260F)
}

val PetroleumShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun PetroleumWorldTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PetroleumDarkColorScheme,
        typography = PetroleumTypography,
        shapes = PetroleumShapes,
        content = content
    )
}
