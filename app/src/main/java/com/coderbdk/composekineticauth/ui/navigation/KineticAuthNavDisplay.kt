package com.coderbdk.composekineticauth.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.coderbdk.composekineticauth.ui.auth.AuthViewModel
import com.coderbdk.composekineticauth.ui.login.Login
import com.coderbdk.composekineticauth.ui.register.RegisterStep1
import com.coderbdk.composekineticauth.ui.register.RegisterStep2
import com.coderbdk.composekineticauth.ui.register.RegisterStep3
import kotlinx.serialization.Serializable

@Serializable
data object LoginNav : NavKey

@Serializable
data object RegisterStep1Nav : NavKey

@Serializable
data object RegisterStep2Nav : NavKey

@Serializable
data object RegisterStep3Nav : NavKey

@Composable
fun KineticAuthNavDisplay(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(LoginNav)
    val viewModel = viewModel<AuthViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<LoginNav> {
                Login(uiState.login, viewModel::onEvent, onCreateAccount = {
                    backStack.add(RegisterStep1Nav)
                })
            }
            entry<RegisterStep1Nav> {
                RegisterStep1(
                    uiState = uiState.register,
                    onEvent = viewModel::onEvent,
                    onNext = {
                        backStack.add(RegisterStep2Nav)
                    },
                    onBackToLogin = {
                        backStack.removeLastOrNull()
                    })
            }
            entry<RegisterStep2Nav> {
                RegisterStep2(
                    uiState = uiState.register,
                    onEvent = viewModel::onEvent,
                    onNext = {
                        backStack.add(RegisterStep3Nav)
                    },
                    onBack = {
                        backStack.removeLastOrNull()
                    })
            }
            entry<RegisterStep3Nav> {
                RegisterStep3(
                    uiState = uiState.register,
                    onEvent = viewModel::onEvent,
                    onBack = {
                        backStack.removeLastOrNull()
                    })
            }
        },
        transitionSpec = {
            // Slide in from right when navigating forward
            slideInHorizontally(initialOffsetX = { it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { -it })
        },
        popTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        },
        predictivePopTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        },
    )
}