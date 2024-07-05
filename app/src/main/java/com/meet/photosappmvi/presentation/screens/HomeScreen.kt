package com.meet.photosappmvi.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.meet.photosappmvi.viewmodel.PhotosViewModel
import com.meet.photosappmvi.presentation.components.ErrorComponent
import com.meet.photosappmvi.presentation.components.LoadingComponent
import com.meet.photosappmvi.presentation.components.SuccessComponent
import com.meet.photosappmvi.viewmodel.PhotoIntent
import com.meet.photosappmvi.viewmodel.PhotosState

@Composable
fun HomeScreen(photosViewModel: PhotosViewModel, modifier: Modifier = Modifier) {
    LaunchedEffect(key1 = true) {
        photosViewModel.processIntent(PhotoIntent.GetRandomPhotos)
    }

    when (val state = photosViewModel.state.collectAsState().value) {
        is PhotosState.Loading -> LoadingComponent(modifier)

        is PhotosState.Error -> {
            ErrorComponent(message = state.message, modifier,onRetry = {
                photosViewModel.processIntent(PhotoIntent.GetRandomPhotos)
            })
        }

        is PhotosState.Success -> {
            SuccessComponent(state.data.collectAsLazyPagingItems(), modifier = modifier, onSearchClick = { query ->
                photosViewModel.processIntent(PhotoIntent.GetSearchedPhotos(query = query))
            })
        }

    }
}