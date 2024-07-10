package com.meet.photosappmvi.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.meet.photosappmvi.presentation.components.ErrorComponent
import com.meet.photosappmvi.presentation.components.ListPhotos
import com.meet.photosappmvi.presentation.components.LoadingComponent
import com.meet.photosappmvi.presentation.components.SearchComponent
import com.meet.photosappmvi.viewmodel.PhotoIntent
import com.meet.photosappmvi.viewmodel.PhotosState
import com.meet.photosappmvi.viewmodel.PhotosViewModel

@Composable
fun SearchScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    photosViewModel: PhotosViewModel = viewModel()
) {
    LaunchedEffect(key1 = true) {
        photosViewModel.processIntent(PhotoIntent.GetSearchedPhotos(query = "cat"))
    }

    Column(modifier = modifier) {
        SearchComponent(onSearchClick = { query ->
            photosViewModel.processIntent(PhotoIntent.GetSearchedPhotos(query = query))
        })

        when (val state = photosViewModel.state.collectAsState().value) {
            is PhotosState.Loading -> LoadingComponent(modifier)

            is PhotosState.Error -> {
                ErrorComponent(message = state.message, modifier, onRetry = {
                    photosViewModel.processIntent(PhotoIntent.GetRandomPhotos)
                })
            }

            is PhotosState.Success -> {
                ListPhotos(lazyPagingItems = state.data.collectAsLazyPagingItems())
            }
        }
    }

}