package com.meet.photosappmvi.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.meet.photosappmvi.presentation.components.ErrorComponent
import com.meet.photosappmvi.presentation.components.ListPhotosPaging
import com.meet.photosappmvi.presentation.components.LoadingComponent
import com.meet.photosappmvi.viewmodel.HomeIntent
import com.meet.photosappmvi.viewmodel.HomeViewModel
import com.meet.photosappmvi.viewmodel.PhotosState

@Composable
fun HomeScreen(modifier: Modifier = Modifier,homeViewModel: HomeViewModel = viewModel()) {

    Column(modifier=modifier.background(color = Color.White)) {

        when (val state = homeViewModel.randomPhotosState.collectAsState().value) {
            is PhotosState.Loading -> LoadingComponent(modifier)

            is PhotosState.Error -> {
                ErrorComponent(message = state.message, modifier,onRetry = {
                    homeViewModel.processIntent(HomeIntent.GetRandomPhotos)
                })
            }

            is PhotosState.Success -> {
                ListPhotosPaging(lazyPagingItems = state.data.collectAsLazyPagingItems())
            }
            else->{}
        }
    }
}