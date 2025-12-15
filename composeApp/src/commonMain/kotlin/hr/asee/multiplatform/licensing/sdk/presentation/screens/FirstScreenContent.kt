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
import kotlin.random.Random

object FirstScreen : Screen {
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
                    topBarTitle = "Screen 1",
                    onBack = {
                        navigator.pop()
                    }
                )
            }
        ) {
            FirstScreenContent(
                title = "Screen 1",
                showContent = uiState.showContent,
                toggleShowContent = { defaultScreenModel.toggleShowContent() },
                onNavigate = { id ->

//                    navigator.push(SecondScreen)
                    defaultScreenModel.goToSecondScreen(id)
                }
            )
        }
    }

}


@Composable
fun FirstScreenContent(
    title: String,
    showContent: Boolean,
    toggleShowContent: (Boolean) -> Unit,
    onNavigate: ((Int) -> Unit)?,
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
            }
        }
        val id = Random.nextInt(1, 100)
        Button(onClick = { onNavigate?.invoke(id) }) {
            Text("Navigate to Screen 2")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FirstScreen() {
    MaterialTheme {

    }
}