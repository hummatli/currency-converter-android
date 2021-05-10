package com.mobline.data.repository

import com.mobline.data.remote.auth.error.ServerProblemDescription
import com.mobline.domain.repository.ErrorConverterRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

class ErrorConverterRepositoryImpl(
    val jsonSerializer: Json
) : ErrorConverterRepository {

    override fun getError(code: Int, identifier: String) {
        val errorBody = jsonSerializer.encodeToString(ServerProblemDescription(identifier))

        val errorResponse = Response.error<String>(
            code,
            errorBody
                .toResponseBody("application/json".toMediaTypeOrNull())
        )

        throw HttpException(errorResponse)
    }
}