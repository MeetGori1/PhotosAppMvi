package com.meet.photosappmvi.domain.api

import com.meet.photosappmvi.data.base.BaseModel
import com.meet.photosappmvi.data.model.Photo
import com.meet.photosappmvi.data.model.User
import com.meet.photosappmvi.domain.client.KtorClient
import com.meet.photosappmvi.wrapper.Response
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.path

class ApiService : BaseApiService() {

    suspend fun getPhotos(page: Int, perPage: Int): Response<List<Photo>> =
        safeApiCall {
            path(HttpRoutes.GET_PHOTOS)
            parameters.append("page", page.toString())
            parameters.append("per_page", perPage.toString())
        }

    suspend fun getSearchedPhotos(query: String, page: Int, perPage: Int): Response<BaseModel> =
        safeApiCall {
            path(HttpRoutes.SEARCH_PHOTOS)
            parameters.append("page", page.toString())
            parameters.append("per_page", perPage.toString())
            parameters.append("query", query)
        }


    suspend fun getLikedPhotos(): Response<List<Photo>> =
        safeApiCall { path(HttpRoutes.GET_LIKED_PHOTOS) }

    suspend fun getUserProfile(): Response<User> = safeApiCall { path(HttpRoutes.GET_USER) }

}