package hr.asee.multiplatform.licensing.sdk.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import hr.asee.multiplatform.licensing.sdk.di.initKoin

fun main() {
    initKoin { }
    initLogger()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "LicensingSDK",
        ) {
            AppContent()
        }
    }
}