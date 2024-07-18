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
    private val _randomPhotosState = MutableStateFlow<PhotosState>(PhotosState.Initial) // Start with Initial
    val randomPhotosState: StateFlow<PhotosState> = _randomPhotosState

    private val _searchedPhotosState = MutableStateFlow<PhotosState>(PhotosState.Initial)
    val searchedPhotosState: StateFlow<PhotosState> = _searchedPhotosState

    private val _likedPhotosState = MutableStateFlow<PhotosState>(PhotosState.Initial)
    val likedPhotosState: StateFlow<PhotosState> = _likedPhotosState

    private val _userProfileState = MutableStateFlow<PhotosState>(PhotosState.Initial)
    val userProfileState: StateFlow<PhotosState> = _userProfileState

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
            _randomPhotosState.value = PhotosState.Loading
            try {
                val photos = Pager(PagingConfig(pageSize = 8)) {
                    PagingDataSource(HttpRoutes.GET_PHOTOS)
                }.flow.cachedIn(viewModelScope)
                _randomPhotosState.value = PhotosState.Success(photos)
            } catch (e: Exception) {
                _randomPhotosState.value = PhotosState.Error("Error fetching photos: ${e.message}")
            }
        }
    }

    private fun searchPhotos(query: String){
        viewModelScope.launch {
            _searchedPhotosState.value = PhotosState.Loading
            try {
                val photos = Pager(PagingConfig(pageSize = 8)) {
                    PagingDataSource(HttpRoutes.SEARCH_PHOTOS,query=query)
                }.flow.cachedIn(viewModelScope)
                _searchedPhotosState.value = PhotosState.Success(photos)
            } catch (e: Exception) {
                _searchedPhotosState.value = PhotosState.Error("Error fetching photos: ${e.message}")
            }
        }
    }

    private fun getLikedPhotos(){
        viewModelScope.launch {
            _likedPhotosState.value = PhotosState.Loading
            when(val response = PhotosRepository.getLikedPhotos()) {
                is Response.Success -> {
                    _likedPhotosState.value = PhotosState.OnLikedPhotoResult(response.value)
                }

                is Response.Error -> {
                    _likedPhotosState.value = PhotosState.Error(response.error)
                }
            }
        }
    }

    private fun getUserProfile(){
        viewModelScope.launch {
            _userProfileState.value = PhotosState.Loading
            when(val response = PhotosRepository.getUserProfile()) {
                is Response.Success -> {
                    _userProfileState.value = PhotosState.OnUserProfileResult(response.value)
                }

                is Response.Error -> {
                    _userProfileState.value = PhotosState.Error(response.error)
                }
            }
        }
    }
}