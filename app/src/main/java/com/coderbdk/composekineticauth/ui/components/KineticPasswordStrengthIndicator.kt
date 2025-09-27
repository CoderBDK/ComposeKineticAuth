package com.coderbdk.composekineticauth.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.coderbdk.composekineticauth.R
import com.coderbdk.composekineticauth.ui.theme.NeonGlowColor
import com.coderbdk.composekineticauth.uitl.PasswordStrength
import com.coderbdk.composekineticauth.uitl.ValidationUtils.calculatePasswordStrength

@Composable
fun KineticPasswordStrengthIndicator(
    modifier: Modifier = Modifier,
    password: String,
    animationDuration: Int = 500,
) {
    val strength = remember(password) { calculatePasswordStrength(password) }
    val targetProgress = when (strength) {
        PasswordStrength.WEAK -> 0.33f
        PasswordStrength.MEDIUM -> 0.66f
        PasswordStrength.STRONG -> 1f
    }

    var animateTrigger by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (animateTrigger) targetProgress else 0f,
        animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing)
    )

    LaunchedEffect(password) {
        animateTrigger = true
    }


    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(NeonGlowColor.copy(alpha = 0.2f))
        ) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFFF0000),
                                Color(0xFFFF4000),
                                Color(0xFFFF8000),
                                Color(0xFFFFBF00),
                                Color(0xFFFFFF00),
                                Color(0xFFBFFF00),
                                Color(0xFF80FF00),
                                Color(0xFF40FF00),
                                Color(0xFF00FF00)
                            )
                        )
                    )
            )
        }
        Text(
            text = strength.value,
            color = when (strength) {
                PasswordStrength.WEAK -> Color(0xFFFF0000)
                PasswordStrength.MEDIUM -> Color(0xFFFFBF00)
                PasswordStrength.STRONG -> Color(0xFF00FF00)
            },
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }

    PasswordRequirements(password)
}

@Composable
fun PasswordRequirements(password: String) {
    val requirements = listOf(
        "Minimum 8 characters",
        "Contains a number",
        "Includes a special character"
    )
    val checkedStates = remember(password) {
        listOf(
            password.length >= 8,
            Regex(".*[0-9].*").containsMatchIn(password),
            Regex(".*[!@#$%^&*].*").containsMatchIn(password)
        )
    }

    FlowRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        requirements.forEachIndexed { index, text ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(4.dp)
            ) {
                CircularCheckbox(
                    checked = checkedStates[index],
                    onCheckedChange = { },
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun CircularCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    size: Dp = 16.dp,
    checkedColor: Color = Color(0xFF4CAF50),
    uncheckedColor: Color = Color.Gray
) {
    val transition = updateTransition(targetState = checked, label = "checkboxTransition")
    val circleColor by transition.animateColor(label = "circleColor") { state ->
        if (state) checkedColor else Color.Transparent
    }
    val borderColor by transition.animateColor(label = "borderColor") { state ->
        if (state) checkedColor else uncheckedColor
    }

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .border(width = 2.dp, color = borderColor, shape = CircleShape)
            .background(circleColor)
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                painter = rememberVectorPainter(ImageVector.vectorResource(R.drawable.outline_check_24)),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(size * 0.6f)
            )
        }
    }
}
