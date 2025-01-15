package com.meet.photosappmvi.domain.api

import android.util.Log
import com.google.gson.JsonSyntaxException
import com.meet.photosappmvi.domain.client.KtorClient
import com.meet.photosappmvi.domain.wrapper.Response
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.JsonConvertException
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.json.Json
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException

abstract class BaseApiService {
    val ktorClient = KtorClient.client

    suspend inline fun <reified T> safeApiCall(
        method: HttpMethod,
        noinline urlBuilder: URLBuilder.() -> Unit,
        body: Any? = null,
        contentType: ContentType = ContentType.Application.Json
    ): Response<T> {
        return try {
            val response: HttpResponse = ktorClient.request {
                url(urlBuilder)
                this.method = method
                if (body != null) {
                    contentType(contentType)
                    setBody(body)
                }
            }
            if (response.status.isSuccess()) {
                val responseBody: T = response.body()
                Response.Success(responseBody)
            } else {
                val errorMessage = getHttpStatusMessage(response)
                Response.Error(errorMessage)
            }
        } catch (e: Exception) {
            Response.Error(getErrorMessage(e))
        }
    }

    suspend inline fun <reified T> safeListApiCall(
        method: HttpMethod,
        noinline urlBuilder: URLBuilder.() -> Unit,
        body: Any? = null,
        contentType: ContentType = ContentType.Application.Json
    ): Response<BaseListModel<T>> {
        return try {
            val response: HttpResponse = ktorClient.request {
                url(urlBuilder)
                this.method = method
                if (body != null) {
                    contentType(contentType)
                    setBody(body)
                    onUpload { bytesSentTotal, contentLength ->
                        println("Sent $bytesSentTotal bytes from $contentLength")
                    }
                }
            }
            if (response.status.isSuccess()) {
                val responseBody: BaseListModel<T> = response.body()
                Response.Success(responseBody)
            } else {
                val errorMessage = getHttpStatusMessage(response)
                Response.Error(errorMessage)
            }
        } catch (e: Exception) {
            Response.Error(getErrorMessage(e))
        }
    }

    suspend fun getHttpStatusMessage(response: HttpResponse): String {
        Log.e(
            "safeApiCall",
            "::HttpStatusFailed statusCode = ${response.status.value}->message = ${response.status.description} "
        )
        val errorResponse = try {
            Json.decodeFromString<ErrorResponse>(response.bodyAsText()).errorMessage
        } catch (e: Exception) {
            response.status.description
        }

        return when (response.status.value) {
            HttpStatusCode.BadRequest.value-> errorResponse
            HttpStatusCode.Unauthorized.value-> errorResponse
            HttpStatusCode.Forbidden.value-> errorResponse
            HttpStatusCode.NotFound.value,
            HttpStatusCode.InternalServerError.value-> errorResponse
            HttpStatusCode.BadGateway.value,
            HttpStatusCode.ServiceUnavailable.value,
            HttpStatusCode.GatewayTimeout.value
            -> response.status.description

            else -> "An unexpected error occurred. Please try again later."
        }
    }

    fun getErrorMessage(e: Throwable): String {
        Log.e("safeApiCall", "::Exception ${e::class.java} ")
        e.printStackTrace()
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
