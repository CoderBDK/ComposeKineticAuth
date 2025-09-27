package com.coderbdk.composekineticauth.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.coderbdk.composekineticauth.ui.theme.ComposeKineticAuthTheme

@Composable
fun KineticAuthApp(modifier: Modifier = Modifier) {
    ComposeKineticAuthTheme(dynamicColor = false) {
        Scaffold(modifier = modifier) {
            KineticAuthNavDisplay(modifier = Modifier.padding(it))
        }
    }
}