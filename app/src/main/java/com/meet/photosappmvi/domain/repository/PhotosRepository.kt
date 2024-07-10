package com.meet.photosappmvi.domain.repository

import com.meet.photosappmvi.data.base.BaseModel
import com.meet.photosappmvi.data.model.Photos
import com.meet.photosappmvi.data.model.User
import com.meet.photosappmvi.domain.api.ApiService

object PhotosRepository {
    private val apiService = ApiService()

    suspend fun getPhotos(page: Int, perPage: Int): List<Photos> {
        return apiService.getPhotos(page,perPage)
    }

    suspend fun getSearchedPhotos(page: Int, perPage: Int,query: String): BaseModel {
        return apiService.getSearchedPhotos(query,page,perPage)
    }

    suspend fun getLikedPhotos(): List<Photos> {
        return apiService.getLikedPhotos()
    }

    suspend fun getUserProfile(): User {
        return apiService.getUserProfile()
    }

}