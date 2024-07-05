package com.meet.photosappmvi.viewmodel

sealed class PhotoIntent {
    data object GetRandomPhotos : PhotoIntent()
    data class GetSearchedPhotos(val query: String) : PhotoIntent()
}