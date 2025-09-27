package com.coderbdk.composekineticauth.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun KineticLoginButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = if (enabled) modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xff39a0d6),
                        Color(0xff4953bf),
                        Color(0xff834bcf)
                    ),
                ),
                shape = RoundedCornerShape(25.dp)
            )
            .shadow(
                elevation = 25.dp,
                spotColor = Color(0xff39a0d6),
            ) else modifier,
        enabled = enabled
    ) {
        Text(text = text, color = color, fontSize = fontSize)
    }
}