package com.meet.photosappmvi.viewmodel

sealed class HomeIntent {
    data object GetRandomPhotos : HomeIntent()
}