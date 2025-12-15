package hr.asee.multiplatform.licensing.sdk.core.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import hr.asee.multiplatform.licensing.sdk.app.logInfo
import hr.asee.multiplatform.licensing.sdk.core.navigation.NavigationEvent
import hr.asee.multiplatform.licensing.sdk.presentation.composables.LaunchErrorSnackBar
import kotlinx.coroutines.CoroutineScope


@Composable
fun <S> BaseAppScreen(
    modifier: Modifier = Modifier,
    screenModel: BaseScreenModel<S>,
    progress: Boolean = false,
    coroutineScope: CoroutineScope,
    exception: Exception? = null,
    topBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val navigator = LocalNavigator.currentOrThrow

    /**
     * Handles navigation events from view model (screen model) see [BaseScreenModel]
     * */
    LaunchedEffect(Unit) {
        logInfo("monitoringNavigationFlow")
        screenModel.navigationEvent.collect { event ->
            when (event) {
                is NavigationEvent.Navigate -> navigator.push(event.screen as Screen)
                NavigationEvent.Back -> navigator.pop()
            }
        }
    }

    Scaffold(
        topBar = topBar,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        content = { paddingValues ->
            /**
            handles error or exception messages, for now commented out (not yet implemented)
             */
            if (exception != null) {
                LaunchErrorSnackBar(
                    coroutineScope = coroutineScope,
                    snackBarHostState = snackBarHostState,
                    errorMessage = "Error",
                    onDismiss = { }
                )
            }

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column {
                    content()
                }

                if (progress) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    )
}
