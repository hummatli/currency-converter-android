package com.mobline.data.errors

import com.mobline.data.remote.auth.error.ServerProblemDescription
import com.mobline.domain.exceptions.ErrorMapper
import com.mobline.domain.exceptions.NetworkError
import com.mobline.domain.exceptions.ServerError
import com.mobline.domain.exceptions.UnknownError
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

enum class RemoteErrors(val code: String) {
    UNEXPECTED_ERROR("error.unexpected"),
    TERMS_NOT_ACCEPTED("error.registration.termNotAccepted"),
    ALREADY_REGISTERED("error.user.alreadyRegistered"),
    INVALID_EXPIRY("adapter-error.invalid-expiry"),
}

class RemoteErrorMapper : ErrorMapper {

    override fun mapError(e: Throwable): Throwable = when (e) {
        is HttpException -> mapHttpErrors(e)
        is SocketException,
        is SocketTimeoutException,
        is UnknownHostException,
        -> NetworkError(e)
        else -> UnknownError(e)
    }

    private fun mapHttpErrors(error: HttpException): Throwable {
        val description = try {
            error
                .response()
                ?.errorBody()
                ?.string()
                ?.let { Json.decodeFromString<ServerProblemDescription>(it) }

        } catch (ex: Throwable) {
            null
        } ?: ServerProblemDescription()

        return when (error.code()) {
            401 -> ServerError.NotAuthorized(description.code, description.message)
            in 500..600 -> {
                ServerError.ServerIsDown(description.code, description.message)
            }
            else -> {
                when (description.code) {
                    RemoteErrors.UNEXPECTED_ERROR.code ->
                        ServerError.Unexpected(
                            description.code,
                            description.message
                        )
                    RemoteErrors.TERMS_NOT_ACCEPTED.code ->
                        ServerError.TermsNotAccepted(
                            description.code,
                            description.message
                        )
                    RemoteErrors.ALREADY_REGISTERED.code ->
                        ServerError.AlreadyRegistered(
                            description.code,
                            description.message
                        )
                    else -> ServerError.Unexpected(description.code, description.message)
                }
            }
        }
    }
}