package com.coderbdk.composekineticauth.ui.components


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.coderbdk.composekineticauth.ui.theme.NeonGlowColor

@Composable
fun KineticStepProgressIndicator(
    totalSteps: Int = 3,
    currentStep: Int = 2,
    animationDuration: Int = 800
) {
    val segmentSpacing = 4.dp
    val completedSteps = remember { mutableStateListOf<Int>() }

    var animateTrigger by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (animateTrigger) 1f else 0f,
        animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing),
        finishedListener = {
            animateTrigger = false
            if (!completedSteps.contains(currentStep)) {
                completedSteps.add(currentStep)
            }
        }
    )

    LaunchedEffect(currentStep) {
        if (!completedSteps.contains(currentStep)) {
            animateTrigger = true
        }
    }

    Row(
        Modifier
            .fillMaxWidth()
            .height(8.dp),
        horizontalArrangement = Arrangement.spacedBy(segmentSpacing)
    ) {
        repeat(totalSteps) { index ->
            val stepNumber = index + 1
            val isCompleted = completedSteps.contains(stepNumber) || stepNumber < currentStep
            val isActive = stepNumber == currentStep

            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        if (isCompleted && !isActive) NeonGlowColor else NeonGlowColor.copy(alpha = 0.2f)
                    )
            ) {
                if (isActive) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(if (animateTrigger) progress else 1f)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        NeonGlowColor,
                                        NeonGlowColor,
                                        NeonGlowColor
                                    )
                                ),
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                }
            }
        }
    }
}

