package hr.asee.multiplatform.licensing.sdk.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import hr.asee.multiplatform.licensing.sdk.core.app.BaseAppScreen
import hr.asee.multiplatform.licensing.sdk.presentation.composables.AppTopBar
import licensingsdk.composeapp.generated.resources.Res
import licensingsdk.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

data class SecondScreen(val randomNumber: String) : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val defaultScreenModel = getScreenModel<DefaultScreenModel>()
        val uiState = defaultScreenModel.uiState.collectAsState().value
        val coroutineScope = rememberCoroutineScope()

        BaseAppScreen(
            screenModel = defaultScreenModel,
            progress = uiState.progress,
            coroutineScope = coroutineScope,
            exception = uiState.exception,
            topBar = {
                AppTopBar(
                    topBarTitle = "Screen 2",
                    onBack = {
//                        navigator.pop()
                        defaultScreenModel.goBack()
                    }
                )
            }
        ) {
            SecondScreenContent(
                title = "Screen 2",
                randomNumber = randomNumber,
                showContent = uiState.showContent,
                toggleShowContent = { defaultScreenModel.toggleShowContent() },
                onNavigate = {
//                    navigator.pop()
                    defaultScreenModel.goBack()
                }
            )
        }
    }

}


@Composable
fun SecondScreenContent(
    title: String,
    showContent: Boolean,
    randomNumber: String,
    toggleShowContent: (Boolean) -> Unit,
    onNavigate: (() -> Unit)?,
) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = { toggleShowContent(showContent) }) {
            Text("Click me!")
        }
        AnimatedVisibility(showContent) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Welcome to screen: $title")
                Text("Passed random number: $randomNumber")
            }
        }
        Button(onClick = { onNavigate?.invoke() }) {
            Text("Back to Screen 1")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenContent() {
    MaterialTheme {

    }
}