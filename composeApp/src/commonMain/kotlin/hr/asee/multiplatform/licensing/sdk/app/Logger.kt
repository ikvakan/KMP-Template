package hr.asee.multiplatform.licensing.sdk.app

import io.github.aakira.napier.Napier

expect fun initLogger()

fun logDebug(message: String) = Napier.d(message)
fun logInfo(message: String) = Napier.i(message)
fun logError(message: String, throwable: Throwable? = null) = Napier.e(message, throwable)