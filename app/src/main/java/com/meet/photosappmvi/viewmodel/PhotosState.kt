package com.meet.photosappmvi.viewmodel

import androidx.paging.PagingData
import com.meet.photosappmvi.data.model.Photos
import kotlinx.coroutines.flow.Flow

sealed class PhotosState {
    data object Loading : PhotosState()
    data class Error(val message: String) : PhotosState()
    data class Success(val data: Flow<PagingData<Photos>>) : PhotosState()
}