package com.meet.photosappmvi.domain.repository

import com.meet.photosappmvi.data.base.BaseModel
import com.meet.photosappmvi.data.model.Photo
import com.meet.photosappmvi.data.model.User
import com.meet.photosappmvi.domain.api.ApiService
import com.meet.photosappmvi.wrapper.Response

object PhotosRepository {
    private val apiService = ApiService()

    suspend fun getPhotos(page: Int, perPage: Int): Response<List<Photo>> {
        return apiService.getPhotos(page,perPage)
    }

    suspend fun getSearchedPhotos(page: Int, perPage: Int,query: String): Response<BaseModel> {
        return apiService.getSearchedPhotos(query,page,perPage)
    }

    suspend fun getLikedPhotos(): Response<List<Photo>> {
        return apiService.getLikedPhotos()
    }

    suspend fun getUserProfile(): Response<User> {
        return apiService.getUserProfile()
    }

}