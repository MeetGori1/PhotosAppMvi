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
}