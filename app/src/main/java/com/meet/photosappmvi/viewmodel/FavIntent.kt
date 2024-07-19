package com.meet.photosappmvi.viewmodel

sealed class FavIntent {
    data object GetLikedPhotos : FavIntent()
}