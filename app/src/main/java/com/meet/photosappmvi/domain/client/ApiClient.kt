package com.meet.photosappmvi.domain.client

import android.util.Log
import com.meet.photosappmvi.domain.Constants
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(json)
        }

        defaultRequest {
            url(Constants.BASE_URL)
            header("Accept-Version", Constants.VERSION)
            header("Authorization", "Client-ID ${Constants.CLIENT_ID}")
        }

        install(Logging) {
            logger =    object : Logger {
                override fun log(message: String) {
                    Log.d("KtorHttpClient", message) // Log to Logcat
                }
            }
            level = LogLevel.ALL
            level = LogLevel.ALL
        }

        install(HttpTimeout) {
            connectTimeoutMillis = 30000
            requestTimeoutMillis = 30000
            socketTimeoutMillis = 30000
        }

    }
}