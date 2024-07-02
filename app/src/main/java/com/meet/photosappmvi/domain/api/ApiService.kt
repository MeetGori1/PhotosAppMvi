package com.meet.photosappmvi.domain.api

import com.meet.photosappmvi.data.model.Photos
import com.meet.photosappmvi.domain.client.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class ApiService {
    private val ktorClient = KtorClient.client

    suspend fun getRandomMeal(): Photos {
        val response: HttpResponse = ktorClient.get(HttpRoutes.RANDOM_MEALS)
        return response.body()
    }

    suspend fun getPhotosBySearch(query: String): Photos {
        val response: HttpResponse = ktorClient.get("${HttpRoutes.PHOTOS_BY_SEARCH}?s=$query")
        return response.body()
    }
}