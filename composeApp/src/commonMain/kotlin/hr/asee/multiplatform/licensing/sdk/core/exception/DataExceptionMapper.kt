package hr.asee.multiplatform.licensing.sdk.core.exception

import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.util.network.UnresolvedAddressException


class DataExceptionMapper : ExceptionMappers.Data {
    override fun map(e: Exception): DataException {
        return when (e) {
            is SocketTimeoutException, is UnresolvedAddressException -> DataException.NetworkException()
            is ResponseException -> mapResponseException(e)
            else -> DataException.UnknownException()
        } as DataException
    }

    private fun mapResponseException(responseException: ResponseException): Exception {
        return when (responseException.response.status.value) {
            in 400..499 -> DataException.ClientException()
            in 500..599 -> DataException.ServerException()
            else -> DataException.UnknownException()
        }
    }
}

