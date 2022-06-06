package com.example.monster_hunter.utils

import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.*
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException


const val UNKNOWN_ERROR = "UNKNOWN ERROR"
const val SYNTAX_ERROR = "SYNTAX_ERROR"

fun <T> Flow<T>.checkHttpError(): Flow<T> {
    return this.catch { error ->
        when (error) {
            is SocketTimeoutException -> throw SocketTimeoutException()
            is HttpException -> throw ApiError(error.code(), error.response()?.errorBody())
            is JsonSyntaxException -> throw Throwable(SYNTAX_ERROR)
            else -> throw Throwable(UNKNOWN_ERROR)
        }
    }
}

suspend inline fun <T> customFlow(retries:Long = 2,crossinline body: suspend FlowCollector<T>.() -> Unit): Flow<T> {
    return flow {
        body()
    }
        .retry(retries)
        .checkHttpError()
}

data class ApiError(val code:Int, val response: ResponseBody?) : Throwable(response?.string())