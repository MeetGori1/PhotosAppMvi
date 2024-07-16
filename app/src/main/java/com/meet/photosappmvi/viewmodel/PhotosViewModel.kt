package com.meet.photosappmvi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.meet.photosappmvi.domain.api.HttpRoutes
import com.meet.photosappmvi.domain.pagingdatasource.PagingDataSource
import com.meet.photosappmvi.domain.repository.PhotosRepository
import com.meet.photosappmvi.wrapper.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhotosViewModel : ViewModel() {
    private val _state = MutableStateFlow<PhotosState>(PhotosState.Loading)
    val state: StateFlow<PhotosState> = _state

    fun processIntent(intent: PhotoIntent) {
        viewModelScope.launch {
            when (intent) {
                is PhotoIntent.GetRandomPhotos -> {
                    getPhotos()
                }
                is PhotoIntent.GetSearchedPhotos -> {
                    searchPhotos(intent.query)
                }
                is PhotoIntent.GetLikedPhotos -> {
                    getLikedPhotos()
                }
                is PhotoIntent.GetUserProfile -> {
                    getUserProfile()
                }
            }
        }
    }

    private fun getPhotos() {
        viewModelScope.launch {
            _state.value = PhotosState.Loading
            try {
                val photos = Pager(PagingConfig(pageSize = 8)) {
                    PagingDataSource(HttpRoutes.GET_PHOTOS)
                }.flow.cachedIn(viewModelScope)
                _state.value = PhotosState.Success(photos)
            } catch (e: Exception) {
                _state.value = PhotosState.Error("Error fetching photos: ${e.message}")
            }
        }
    }

    private fun searchPhotos(query: String){
        viewModelScope.launch {
            _state.value = PhotosState.Loading
            try {
                val photos = Pager(PagingConfig(pageSize = 8)) {
                    PagingDataSource(HttpRoutes.SEARCH_PHOTOS,query=query)
                }.flow.cachedIn(viewModelScope)
                _state.value = PhotosState.Success(photos)
            } catch (e: Exception) {
                _state.value = PhotosState.Error("Error fetching photos: ${e.message}")
            }
        }
    }

    private fun getLikedPhotos(){
        viewModelScope.launch {
            _state.value = PhotosState.Loading
            when(val response = PhotosRepository.getLikedPhotos()) {
                is Response.Success -> {
                    _state.value = PhotosState.OnLikedPhotoResult(response.value)
                }

                is Response.Error -> {
                    _state.value = PhotosState.Error(response.error)
                }
            }
        }
    }

    private fun getUserProfile(){
        viewModelScope.launch {
            _state.value = PhotosState.Loading
            when(val response = PhotosRepository.getUserProfile()) {
                is Response.Success -> {
                    _state.value = PhotosState.OnUserProfileResult(response.value)
                }

                is Response.Error -> {
                    _state.value = PhotosState.Error(response.error)
                }
            }
        }
    }
}