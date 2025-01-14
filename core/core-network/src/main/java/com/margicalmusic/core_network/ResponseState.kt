package com.margicalmusic.core_network

import com.margicalmusic.core_utils.UiText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class ResponseState<T> {
    data class Success<T>(val data: T) : ResponseState<T>()
    data class Error<T>(val uiText: UiText) : ResponseState<T>()
}

object ErrorHandler {
    fun <T> parseRequestException(e: Exception): ResponseState.Error<T> {
        if (BuildConfig.DEBUG) e.printStackTrace()
        return when (e) {
            is HttpException -> {
                e.response()?.errorBody()?.string()?.let { errorString ->
                    try {
                        val errorBody = Json.parseToJsonElement(errorString).jsonObject
                        ResponseState.Error(UiText.DynamicText(errorBody["message"]?.jsonPrimitive!!.content))
                    } catch (e: Exception) {
                        ResponseState.Error(UiText.DynamicText(errorString)) // <T> is required
                    }
                } ?: run {
                    ResponseState.Error(UiText.DynamicText(e.message()))
                }
            }

            is UnknownHostException -> {
                ResponseState.Error(UiText.StaticText(R.string.server_error))
            }

            is ConnectException -> {
                ResponseState.Error(UiText.StaticText(R.string.connection_error))
            }

            is SocketTimeoutException -> {
                ResponseState.Error(UiText.StaticText(R.string.socket_exception_error))
            }

            is IOException -> {
                ResponseState.Error(UiText.StaticText(R.string.check_internet))
            }

            else -> {
                ResponseState.Error(UiText.StaticText(R.string.something_error))
            }
        }
    }
}