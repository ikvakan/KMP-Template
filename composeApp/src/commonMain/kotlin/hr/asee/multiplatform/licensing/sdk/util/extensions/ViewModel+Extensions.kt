package hr.asee.multiplatform.licensing.sdk.util.extensions

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import hr.asee.multiplatform.licensing.sdk.core.app.Coroutine
import kotlinx.coroutines.CoroutineScope

fun ScreenModel.coroutine(block: suspend CoroutineScope.() -> Unit) =
    Coroutine(this.screenModelScope, block)
