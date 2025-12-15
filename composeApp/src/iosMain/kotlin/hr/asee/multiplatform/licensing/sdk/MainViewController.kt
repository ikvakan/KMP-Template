package hr.asee.multiplatform.licensing.sdk

import androidx.compose.ui.window.ComposeUIViewController
import hr.asee.multiplatform.licensing.sdk.app.App
import hr.asee.multiplatform.licensing.sdk.app.initLogger
import hr.asee.multiplatform.licensing.sdk.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initLogger()
        initKoin()
    }
) { App() }