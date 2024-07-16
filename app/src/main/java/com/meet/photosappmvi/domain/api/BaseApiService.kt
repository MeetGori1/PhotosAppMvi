package com.meet.photosappmvi.domain.api

import android.util.Log
import com.google.gson.JsonSyntaxException
import com.meet.photosappmvi.domain.client.KtorClient
import com.meet.photosappmvi.wrapper.Response
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.isSuccess
import io.ktor.serialization.JsonConvertException
import io.ktor.utils.io.errors.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException

abstract class BaseApiService {

    val ktorClient = KtorClient.client

    suspend inline fun <reified T> safeApiCall(noinline urlBuilder: URLBuilder.(URLBuilder) -> Unit): Response<T> {
        try {
            val response: HttpResponse = ktorClient.get { url(block = urlBuilder) }
            if (response.status.isSuccess()) {
                val responseBody: T = response.body()
                return Response.Success(responseBody)
            } else {
                val errorMessage = getHttpStatusMessage(response)
                return Response.Error(errorMessage)
            }
        } catch (e: Exception) {
            return Response.Error(getErrorMessage(e))
        }
    }

    fun getHttpStatusMessage(response: HttpResponse): String {
        Log.e("safeApiCall", "::HttpStatusFailed statusCode = ${response.status.value} ")
        return when (response.status.value) {
            HttpStatusCode.BadRequest.value,
            HttpStatusCode.Unauthorized.value,
            HttpStatusCode.Forbidden.value,
            HttpStatusCode.NotFound.value,
            HttpStatusCode.InternalServerError.value,
            HttpStatusCode.BadGateway.value,
            HttpStatusCode.ServiceUnavailable.value,
            HttpStatusCode.GatewayTimeout.value
            -> response.status.description

            else -> "An unexpected error occurred. Please try again later."
        }
    }

    fun getErrorMessage(e: Throwable): String {
        Log.e("safeApiCall", "::Exception ${e::class.java} ")
        return when (e) {
            is UnresolvedAddressException -> "No internet connection. Please check your connection."
            is UnknownHostException -> "No internet connection. Please check your network settings."
            is HttpRequestTimeoutException -> "Request timed out. Please try again later."
            is SocketTimeoutException -> "Request timed out. Please try again later."
            is ConnectException -> "Failed to connect to the server. Please try again later."
            is SocketException -> "Network error. Please check your connection."
            is IOException -> "Please check your connection."
            is JsonSyntaxException -> "API working not correctly. Please try again later."
            is JsonConvertException -> "API working not correctly. Please try again later."
            else -> "An unexpected error occurred. Please try again later."
        }
    }
}