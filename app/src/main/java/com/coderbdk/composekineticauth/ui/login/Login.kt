package com.coderbdk.composekineticauth.ui.login

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coderbdk.composekineticauth.R
import com.coderbdk.composekineticauth.data.model.Login
import com.coderbdk.composekineticauth.ui.auth.AuthViewModel
import com.coderbdk.composekineticauth.ui.components.CircularCheckbox
import com.coderbdk.composekineticauth.ui.components.KineticLoginButton
import com.coderbdk.composekineticauth.ui.components.KineticTextField
import com.coderbdk.composekineticauth.ui.components.kineticGlowingBorder
import com.coderbdk.composekineticauth.ui.model.AuthUiEvent
import com.coderbdk.composekineticauth.ui.model.LoginState
import com.coderbdk.composekineticauth.ui.theme.ComposeKineticAuthTheme
import com.coderbdk.composekineticauth.uitl.ValidationUtils

@Composable
fun Login(
    uiState: LoginState,
    onEvent: (AuthUiEvent.LoginEvent) -> Unit, onCreateAccount: () -> Unit
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
                text = stringResource(R.string.login_title),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            Spacer(Modifier.height(56.dp))
            KineticTextField(
                value = uiState.email,
                onValueChange = {
                    onEvent(AuthUiEvent.LoginEvent.OnEmailChanged(it))
                },
                label = { Text("Email") },
                placeholder = { Text(text = stringResource(R.string.login_field_email_placeholder)) },
                trailingIcon = {
                    if (ValidationUtils.isValidEmail(uiState.email)) {
                        CircularCheckbox(checked = true, onCheckedChange = {}, size = 20.dp)
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(4.dp))
            KineticTextField(
                value = uiState.password,
                onValueChange = {
                    onEvent(AuthUiEvent.LoginEvent.OnPasswordChanged(it))
                },
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
                label = { Text("Password") },
                placeholder = { Text(text = stringResource(R.string.login_field_password_placeholder)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            KineticLoginButton(
                text = "Login",
                onClick = {
                    onEvent(AuthUiEvent.LoginEvent.OnLoginRequest)
                },
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(Modifier.weight(1f))
                Text("OR", modifier = Modifier.padding(horizontal = 8.dp))
                HorizontalDivider(Modifier.weight(1f))
            }
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.border(
                        width = 1.dp,
                        shape = CircleShape,
                        color = colorScheme.outlineVariant
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.outline_fingerprint_24),
                        null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                Spacer(Modifier.width(24.dp))
                IconButton(
                    onClick = {},
                    modifier = Modifier.border(
                        width = 1.dp,
                        shape = CircleShape,
                        color = colorScheme.primary
                    )
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.google),
                        null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier.border(
                        width = 1.dp,
                        shape = CircleShape,
                        color = colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.facebook),
                        null, tint = Color(0xFF3E67B7),
                        modifier = Modifier
                            .fillMaxSize()

                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                TextButton(
                    onClick = {}
                ) {
                    Text("Forgot Password?")
                }
                TextButton(
                    onClick = onCreateAccount
                ) {
                    Text("Create Account")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    ComposeKineticAuthTheme(darkTheme = true) {
        Surface {
            Login(uiState = LoginState("", ""), onEvent = {}, onCreateAccount = {})
        }
    }


}