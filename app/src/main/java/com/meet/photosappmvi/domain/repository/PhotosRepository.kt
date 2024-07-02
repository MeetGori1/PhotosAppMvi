package com.meet.photosappmvi.domain.repository

import com.meet.photosappmvi.data.model.Photos
import com.meet.photosappmvi.domain.api.ApiService

object PhotosRepository {

    private val apiService = ApiService()
    suspend fun getRandomMeal(): Photos {
        return apiService.getRandomMeal()
    }

    suspend fun getPhotosBySearch(query: String): Photos {
        return apiService.getPhotosBySearch(query)
    }

}