package hr.asee.multiplatform.licensing.sdk.core.app


import hr.asee.multiplatform.licensing.sdk.core.exception.ExceptionMappers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException

typealias CoroutineBlockCallback = suspend () -> Unit
typealias ProgressChangedCallback = (Coroutine.Progress) -> Unit
typealias StartedCallback = () -> Unit
typealias ExceptionCallback = (Exception) -> Unit
typealias CancelCallback = (Exception) -> Unit
typealias FinishedCallback = () -> Unit


/**
 * A wrapper around a suspendable coroutine block that provides
 * structured state handling for:
 *
 *  - Progress updates (start, in-progress, finished)
 *  - Exception mapping via [ExceptionMappers.Data]
 *  - Custom callbacks for lifecycle stages (start, exception, cancel, finish)
 *
 * This class allows ScreenModels/ViewModels to execute coroutines
 */

class Coroutine(
    private val coroutineScope: CoroutineScope,
    private val coroutineBlock: suspend CoroutineScope.() -> Unit
) {
    data class Progress(
        val inProgress: Boolean = true,
        val exception: Exception? = null
    )

    private lateinit var exceptionMapper: ExceptionMappers.Data

    private var progress = Progress()
    private var progressChangedCallback: ProgressChangedCallback? = null
    private var startedCallBack: StartedCallback? = null
    private var exceptionCallback: ExceptionCallback? = null
    private var cancelCallback: CancelCallback? = null
    private var finishedCallback: FinishedCallback? = null
    private var coroutineContext: CoroutineContext = EmptyCoroutineContext
    private var coroutineStart: CoroutineStart = CoroutineStart.DEFAULT

    fun onProgressChanged(callback: ProgressChangedCallback) =
        apply { this.progressChangedCallback = callback }

    fun onException(callback: ((Exception) -> Unit)?) = apply { exceptionCallback = callback }
    fun setExceptionMapper(exceptionMapper: ExceptionMappers.Data) =
        apply { this.exceptionMapper = exceptionMapper }

    fun onStarted(callback: StartedCallback) = apply { startedCallBack = callback }
    fun onCanceled(callback: CancelCallback) = apply { cancelCallback = callback }
    fun onFinish(callback: FinishedCallback) = apply { finishedCallback = callback }

    fun withContext(context: CoroutineContext) = apply { coroutineContext = context }
    fun setStart(start: CoroutineStart) = apply { coroutineStart = start }

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    fun launch() = coroutineScope.launch {
        try {
            startedCallBack?.invoke()
            updateProgress(inProgress = true)
            coroutineBlock.invoke(this)
            updateProgress(inProgress = false)
            finishedCallback?.invoke()
        } catch (e: CancellationException) {
            cancelCallback?.invoke(e)
            updateProgress(inProgress = false)
        } catch (e: Exception) {
            exceptionCallback?.invoke(e)
            updateProgress(
                inProgress = false,
                exception = mapException(exceptionMapper, e)
            )
        }
    }

    private fun updateProgress(inProgress: Boolean = false, exception: Exception? = null) {
        progress = progress.copy(inProgress = inProgress, exception = exception)
        progressChangedCallback?.invoke(progress)
    }

    private fun mapException(mapper: ExceptionMappers.Data, e: Exception) = mapper.map(e)
}