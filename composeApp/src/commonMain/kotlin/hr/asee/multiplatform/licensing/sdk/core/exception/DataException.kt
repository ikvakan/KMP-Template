package hr.asee.multiplatform.licensing.sdk.core.exception

sealed class DataException : Exception() {
    class ClientException : DataException()
    class ServerException : DataException()
    class NetworkException : DataException()
    class DatabaseException : DataException()
    class UnknownException : DataException()
}
