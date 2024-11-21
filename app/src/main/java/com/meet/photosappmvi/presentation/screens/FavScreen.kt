package com.meet.photosappmvi.presentation.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.meet.photosappmvi.presentation.components.ErrorComponent
import com.meet.photosappmvi.presentation.components.ListPhotos
import com.meet.photosappmvi.presentation.components.LoadingComponent
import com.meet.photosappmvi.presentation.navigation.NavRoute
import com.meet.photosappmvi.viewmodel.PhotoIntent
import com.meet.photosappmvi.viewmodel.PhotosState
import com.meet.photosappmvi.viewmodel.PhotosViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FavScreen(
    navController: NavHostController, modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    photosViewModel: PhotosViewModel = viewModel()
) {
    val apiCalled = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!apiCalled.value) {
            photosViewModel.processIntent(PhotoIntent.GetLikedPhotos)
            apiCalled.value = true
        }
    }

    Column(modifier = modifier) {
        when (val state = photosViewModel.likedPhotosState.collectAsState().value) {
            is PhotosState.Initial -> {}
            is PhotosState.Loading -> LoadingComponent(modifier)
            is PhotosState.Error -> {
                ErrorComponent(message = state.message, modifier, onRetry = {
                    photosViewModel.processIntent(PhotoIntent.GetLikedPhotos)
                })
            }
            is PhotosState.OnLikedPhotoResult -> {
                ListPhotos(state.photoList, animatedVisibilityScope) {
                    navController.navigate(NavRoute.PhotoDetailScreenRoute(photo = it))
                }
            }
            else -> {}
        }
    }
}
