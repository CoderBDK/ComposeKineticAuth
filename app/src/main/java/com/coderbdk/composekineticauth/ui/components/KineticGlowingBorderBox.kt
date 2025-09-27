package com.coderbdk.composekineticauth.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.coderbdk.composekineticauth.ui.theme.ComposeKineticAuthTheme
import com.coderbdk.composekineticauth.ui.theme.HighlightColor
import com.coderbdk.composekineticauth.ui.theme.NeonGlowColor

@Composable
fun KineticGlowingBorderBox(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier.kineticGlowingBorder(cornerRadius = cornerRadius)
    ) {
        content(this)
    }
}

@Composable
fun Modifier.kineticGlowingBorder(
    animatedFloat: Float = 1f,
    cornerRadius: Dp = 24.dp
): Modifier {
    return this
        .padding(24.dp)
        .border(
            width = 4.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    NeonGlowColor,
                    Color.Transparent,
                    HighlightColor,
                    NeonGlowColor.copy(alpha = 0.5f),
                    Color.Transparent
                ),
                start = Offset(-animatedFloat * 250f, -animatedFloat * 250f),
                end = Offset(animatedFloat * 500f, animatedFloat * 1000f)
            ),
            shape = RoundedCornerShape(cornerRadius)
        )
        .dropShadow(
            shape = RoundedCornerShape(cornerRadius / 2),
            shadow = Shadow(cornerRadius / 2, colorScheme.outlineVariant)
        )
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    colorScheme.surfaceContainer,
                    colorScheme.surfaceBright
                )
            ),
            shape = RoundedCornerShape(cornerRadius)
        )
}


@Preview(showBackground = true)
@Composable
fun KineticGlowingBorderBoxPreview() {

    ComposeKineticAuthTheme {
        Box(Modifier.background(Color.Black)) {
            KineticGlowingBorderBox(
                Modifier
                    .fillMaxSize()

            ) {

            }
        }
    }


}