package com.meet.photosappmvi.domain.api

import com.meet.photosappmvi.data.base.BaseModel
import com.meet.photosappmvi.data.model.Photo
import com.meet.photosappmvi.data.model.User
import com.meet.photosappmvi.domain.client.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.path

class ApiService {
    private val ktorClient = KtorClient.client

    suspend fun getPhotos(page: Int = 1, perPage: Int = 20): List<Photo> {
        val response: HttpResponse = ktorClient.get {
            url {
                path(HttpRoutes.GET_PHOTOS)
                parameters.append("page", page.toString())
                parameters.append("per_page", perPage.toString())
            }
        }
        return response.body()
    }

    suspend fun getSearchedPhotos(query: String,page: Int, perPage: Int): BaseModel {
        val response: HttpResponse = ktorClient.get {
            url {
                path(HttpRoutes.SEARCH_PHOTOS)
                parameters.append("page", page.toString())
                parameters.append("per_page", perPage.toString())
                parameters.append("query", query)
            }
        }
        return response.body()
    }

    suspend fun getLikedPhotos(): List<Photo> {
        val response: HttpResponse = ktorClient.get {
            url {
                path(HttpRoutes.GET_LIKED_PHOTOS)
            }
        }
        return response.body()
    }

    suspend fun getUserProfile(): User {
        val response: HttpResponse = ktorClient.get {
            url {
                path(HttpRoutes.GET_USER)
            }
        }
        return response.body()
    }
}