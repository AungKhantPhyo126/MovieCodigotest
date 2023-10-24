package com.akpdev.moviecodigotest.exception

/**
 * Custom exception classes represented for domain exception throughout the app
 */
open class DomainException(override val message: String?): Throwable(message)

data class InvalidDataException(
    val paramName: String
): DomainException("$paramName is not valid.")

object NoConnectionException: DomainException("No connection available.")

object DisconnectException: DomainException("Network disconnected.")