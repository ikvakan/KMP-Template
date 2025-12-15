package hr.asee.multiplatform.licensing.sdk.presentation.screens

import hr.asee.multiplatform.licensing.sdk.app.logDebug
import hr.asee.multiplatform.licensing.sdk.core.app.BaseScreenModel
import kotlinx.coroutines.Job


data class FirstScreenViewModelUiState(
    val showContent: Boolean = false,
    val progress: Boolean = false,
    val exception: Exception? = null,
) {
    fun updateProgressState(
        progress: Boolean = false,
        exception: Exception? = null
    ) =
        this.copy(
            progress = progress,
            exception = exception
        )

    fun toggleShowContent() = this.copy(showContent = !showContent)
}

class DefaultScreenModel(
) : BaseScreenModel<FirstScreenViewModelUiState>(FirstScreenViewModelUiState()) {

    private var screen1Job: Job? = null

    fun toggleShowContent() {
        updateState { state ->
            state.toggleShowContent()
        }
    }

    /**
     * Navigation can be done from screen model or from the UI layer see [SecondScreenContent] or [SecondScreenContent] for examples
     * */
    fun goToSecondScreen(id: Int) {
        onNavigate(SecondScreen(randomNumber = id.toString()))
    }

    fun goBack() {
        onBack()
    }

    override fun onDispose() {
        screen1Job?.cancel()
        super.onDispose()
        logDebug("onDispose:$this")
    }
}
