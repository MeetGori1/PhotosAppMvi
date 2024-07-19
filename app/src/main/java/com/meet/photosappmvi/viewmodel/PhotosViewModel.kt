package com.meet.photosappmvi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.meet.photosappmvi.domain.api.HttpRoutes
import com.meet.photosappmvi.domain.pagingdatasource.PagingDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhotosViewModel : ViewModel() {

    private val _searchedPhotosState = MutableStateFlow<PhotosState>(PhotosState.Loading)
    val searchedPhotosState: StateFlow<PhotosState> = _searchedPhotosState

    /*private val _userProfileState = MutableStateFlow<PhotosState>(PhotosState.Initial)
    val userProfileState: StateFlow<PhotosState> = _userProfileState*/

    fun processIntent(intent: PhotoIntent) {
        viewModelScope.launch {
            when (intent) {
                is PhotoIntent.GetSearchedPhotos -> {
                    searchPhotos(intent.query)
                }
                /*
                 is PhotoIntent.GetUserProfile -> {
                    getUserProfile()
                }*/
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
/*
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
    }*/
}