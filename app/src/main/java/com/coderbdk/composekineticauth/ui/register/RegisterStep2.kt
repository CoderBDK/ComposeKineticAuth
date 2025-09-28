package com.coderbdk.composekineticauth.ui.register

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coderbdk.composekineticauth.R
import com.coderbdk.composekineticauth.data.model.Register
import com.coderbdk.composekineticauth.ui.components.CircularCheckbox
import com.coderbdk.composekineticauth.ui.components.KineticLoginButton
import com.coderbdk.composekineticauth.ui.components.KineticPasswordStrengthIndicator
import com.coderbdk.composekineticauth.ui.components.KineticStepProgressIndicator
import com.coderbdk.composekineticauth.ui.components.KineticTextField
import com.coderbdk.composekineticauth.ui.components.kineticGlowingBorder
import com.coderbdk.composekineticauth.ui.model.AuthUiEvent
import com.coderbdk.composekineticauth.ui.model.RegisterState
import com.coderbdk.composekineticauth.ui.theme.ComposeKineticAuthTheme
import com.coderbdk.composekineticauth.uitl.ValidationUtils


@Composable
fun RegisterStep2(
    uiState: RegisterState,
    onEvent: (AuthUiEvent.RegisterEvent) -> Unit,
    onNext: () -> Unit, onBack: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "GlowAnimation")

    val animatedFloat by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "glowAnim"
    )
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val canGoNext =
        ValidationUtils.isPasswordValid(uiState.confirmPassword) && uiState.confirmPassword == uiState.password

    Box(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        colorScheme.surfaceContainer,
                        colorScheme.surfaceBright,
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .kineticGlowingBorder(animatedFloat = animatedFloat, cornerRadius = 24.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,

            ) {
            Text(
                text = stringResource(R.string.register_title),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            Spacer(Modifier.height(32.dp))

            Text(
                text = "Step 2: Create Password",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Spacer(Modifier.height(8.dp))
            KineticStepProgressIndicator(
                totalSteps = 3,
                currentStep = 2
            )
            Spacer(Modifier.height(8.dp))

            Text(
                text = "Step 2 of 3",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
            )
            Spacer(Modifier.height(8.dp))
            KineticTextField(
                value = uiState.password,
                onValueChange = {
                    onEvent(AuthUiEvent.RegisterEvent.OnPasswordChanged(it))
                },
                label = { Text("Password") },
                placeholder = { Text(text = stringResource(R.string.login_field_password_placeholder)) },
                trailingIcon = {
                    if (ValidationUtils.isPasswordValid(uiState.password)) {
                        CircularCheckbox(checked = true, onCheckedChange = {}, size = 20.dp)
                        return@KineticTextField
                    }
                    val icon = if (passwordVisible)
                        ImageVector.vectorResource(R.drawable.outline_visibility_off_24)
                    else
                        ImageVector.vectorResource(R.drawable.outline_visibility_24)
                    IconButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            KineticTextField(
                value = uiState.confirmPassword,
                onValueChange = {
                    onEvent(AuthUiEvent.RegisterEvent.OnConfirmPasswordChanged(it))
                },
                label = { Text("Confirm Password") },
                placeholder = { Text(text = stringResource(R.string.login_field_password_placeholder)) },
                trailingIcon = {
                    if (canGoNext) {
                        CircularCheckbox(checked = true, onCheckedChange = {}, size = 20.dp)
                        return@KineticTextField
                    }
                    val icon = if (confirmPasswordVisible)
                        ImageVector.vectorResource(R.drawable.outline_visibility_off_24)
                    else
                        ImageVector.vectorResource(R.drawable.outline_visibility_24)
                    IconButton(
                        onClick = {
                            confirmPasswordVisible = !confirmPasswordVisible
                        }
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            KineticPasswordStrengthIndicator(password = uiState.password)

            Spacer(Modifier.height(16.dp))
            KineticLoginButton(
                text = "Next",
                onClick = onNext,
                fontSize = 20.sp,
                enabled = canGoNext,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(16.dp))

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
            ) {
                Text("Back")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterStep2Preview() {
    ComposeKineticAuthTheme(darkTheme = false) {
        Surface {
            RegisterStep2(
                uiState = RegisterState("", "", "", "", "", ""),
                onEvent = {}, onNext = {}, onBack = {})
        }
    }
}