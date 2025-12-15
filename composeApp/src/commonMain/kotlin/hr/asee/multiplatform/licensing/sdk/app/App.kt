package hr.asee.multiplatform.licensing.sdk.app

import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable

import cafe.adriel.voyager.navigator.Navigator
import hr.asee.multiplatform.licensing.sdk.presentation.screens.FirstScreen


@Composable
fun AppContent() {
    MaterialTheme {
        Navigator(FirstScreen)
    }
}

