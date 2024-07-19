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

class HomeViewModel : ViewModel() {
    private val _randomPhotosState = MutableStateFlow<PhotosState>(PhotosState.Loading)
    val randomPhotosState: StateFlow<PhotosState> = _randomPhotosState

    init {
        processIntent(HomeIntent.GetRandomPhotos)
    }

    fun processIntent(intent: HomeIntent) {
        viewModelScope.launch {
            when (intent) {
                is HomeIntent.GetRandomPhotos -> {
                    getPhotos()
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

}