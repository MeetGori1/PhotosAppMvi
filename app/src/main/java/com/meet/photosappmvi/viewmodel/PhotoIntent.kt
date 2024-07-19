package com.meet.photosappmvi.viewmodel

sealed class PhotoIntent {
    data class GetSearchedPhotos(val query: String) : PhotoIntent()
//    data object GetUserProfile : PhotoIntent()
}