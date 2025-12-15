package hr.asee.multiplatform.licensing.sdk.core.app

import cafe.adriel.voyager.core.model.ScreenModel
import hr.asee.multiplatform.licensing.sdk.app.logDebug
import hr.asee.multiplatform.licensing.sdk.core.exception.ExceptionMappers
import hr.asee.multiplatform.licensing.sdk.core.navigation.NavigationEvent
import hr.asee.multiplatform.licensing.sdk.util.extensions.coroutine
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseScreenModel<S>(initialState: S) : ScreenModel, KoinComponent {

    private val exceptionMapper: ExceptionMappers.Data by inject()
    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>(extraBufferCapacity = 1)
    val navigationEvent = _navigationEvent.asSharedFlow()

    protected fun updateState(updater: (S) -> S) {
        _uiState.update(updater)
    }

    protected fun launchCoroutine(
        coroutineBlock: CoroutineBlockCallback,
        onException: ExceptionCallback,
        onProgressChanged: ProgressChangedCallback = {},
    ) = coroutine {
        coroutineBlock()
    }
        .setExceptionMapper(exceptionMapper)
        .onException(onException)
        .onProgressChanged(onProgressChanged)
        .launch()

    protected fun onNavigate(screen: Any) {
        logDebug("onNavigate: $screen")
        _navigationEvent.tryEmit(NavigationEvent.Navigate(screen))
    }

    protected fun onBack() {
        logDebug("onBack")
        _navigationEvent.tryEmit(NavigationEvent.Back)
    }

    override fun onDispose() {
        super.onDispose()
    }
}