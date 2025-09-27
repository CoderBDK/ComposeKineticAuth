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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coderbdk.composekineticauth.R
import com.coderbdk.composekineticauth.data.model.Register
import com.coderbdk.composekineticauth.ui.auth.AuthViewModel
import com.coderbdk.composekineticauth.ui.components.CircularCheckbox
import com.coderbdk.composekineticauth.ui.components.KineticLoginButton
import com.coderbdk.composekineticauth.ui.components.KineticStepProgressIndicator
import com.coderbdk.composekineticauth.ui.components.KineticTextField
import com.coderbdk.composekineticauth.ui.components.kineticGlowingBorder
import com.coderbdk.composekineticauth.ui.model.AuthUiEvent
import com.coderbdk.composekineticauth.ui.theme.ComposeKineticAuthTheme
import com.coderbdk.composekineticauth.uitl.ValidationUtils

@Composable
fun RegisterStep1(
    uiState: Register,
    onEvent: (AuthUiEvent.RegisterEvent) -> Unit,
    onNext: () -> Unit,
    onBackToLogin: () -> Unit
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

    val isFirstNameValid = ValidationUtils.isValidName(uiState.firstName)
    val isLastNameValid = ValidationUtils.isValidName(uiState.lastName)
    val isEmailValid = ValidationUtils.isValidEmail(uiState.email)
    val canGoNext = isFirstNameValid && isEmailValid
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
                text = "Step 1: Personal Info",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Spacer(Modifier.height(16.dp))
            KineticStepProgressIndicator(
                totalSteps = 3,
                currentStep = 1
            )
            Spacer(Modifier.height(16.dp))

            Text(
                text = "Step 1 of 3",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
            )
            Spacer(Modifier.height(8.dp))
            KineticTextField(
                value = uiState.firstName,
                onValueChange = {
                    onEvent(AuthUiEvent.RegisterEvent.OnFirstNameChanged(it))
                },
                label = { Text("First Name") },
                placeholder = { Text(text = stringResource(R.string.register_field_first_name_placeholder)) },
                trailingIcon = {
                    if (isFirstNameValid) {
                        CircularCheckbox(checked = true, onCheckedChange = {}, size = 20.dp)
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            KineticTextField(
                value = uiState.lastName,
                onValueChange = {
                    onEvent(AuthUiEvent.RegisterEvent.OnLastNameChanged(it))
                },
                label = { Text("Last Name") },
                placeholder = { Text(text = stringResource(R.string.register_field_last_name_placeholder)) },
                trailingIcon = {
                    if (isLastNameValid) {
                        CircularCheckbox(checked = true, onCheckedChange = {}, size = 20.dp)
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            KineticTextField(
                value = uiState.email,
                onValueChange = {
                    onEvent(AuthUiEvent.RegisterEvent.OnEmailChanged(it))
                },
                label = { Text("Email") },
                placeholder = { Text(text = stringResource(R.string.login_field_email_placeholder)) },
                trailingIcon = {
                    if (isEmailValid) {
                        CircularCheckbox(checked = true, onCheckedChange = {}, size = 20.dp)
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier.fillMaxWidth()
            )
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

            TextButton(
                onClick = onBackToLogin,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Back to Login")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterStep1Preview() {
    ComposeKineticAuthTheme(darkTheme = false) {
        Surface {
            RegisterStep1(
                uiState = Register("", "", "", "", "", ""),
                onEvent = {},
                onNext = {},
                onBackToLogin = {})
        }
    }


}