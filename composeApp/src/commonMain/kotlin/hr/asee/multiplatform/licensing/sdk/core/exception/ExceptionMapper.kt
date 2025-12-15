package hr.asee.multiplatform.licensing.sdk.core.exception

interface ExceptionMapper<out T> {
    fun map(e: Exception): T

}
