package com.meet.photosappmvi.viewmodel

import androidx.paging.PagingData
import com.meet.photosappmvi.data.model.Photo
import com.meet.photosappmvi.data.model.User
import kotlinx.coroutines.flow.Flow

sealed class PhotosState {
    data object Loading : PhotosState()
    data class Error(val message: String) : PhotosState()
    data class Success(val data: Flow<PagingData<Photo>>) : PhotosState()
    data class OnLikedPhotoResult(val photoList: List<Photo>) : PhotosState()
    data class OnUserProfileResult(val user: User) : PhotosState()
}