package com.meet.photosappmvi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meet.photosappmvi.domain.repository.PhotosRepository
import com.meet.photosappmvi.wrapper.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavViewModel : ViewModel() {

    private val _likedFavIntent = MutableStateFlow<PhotosState>(PhotosState.Loading)
    val likedFavIntent : StateFlow<PhotosState> = _likedFavIntent

    fun processIntent(intent: FavIntent) {
        viewModelScope.launch {
            when (intent) {
                is FavIntent.GetLikedPhotos -> {
                    getLikedPhotos()
                }
            }
        }
    }

    private fun getLikedPhotos(){
        viewModelScope.launch {
            _likedFavIntent.value = PhotosState.Loading
            when(val response = PhotosRepository.getLikedPhotos()) {
                is Response.Success -> {
                    _likedFavIntent.value = PhotosState.OnLikedPhotoResult(response.value)
                }

                is Response.Error -> {
                    _likedFavIntent.value = PhotosState.Error(response.error)
                }
            }
        }
    }

}