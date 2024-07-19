package com.meet.photosappmvi.presentation.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.meet.photosappmvi.presentation.components.ErrorComponent
import com.meet.photosappmvi.presentation.components.ListPhotos
import com.meet.photosappmvi.presentation.components.LoadingComponent
import com.meet.photosappmvi.presentation.navigation.NavRoute
import com.meet.photosappmvi.viewmodel.FavIntent
import com.meet.photosappmvi.viewmodel.FavViewModel
import com.meet.photosappmvi.viewmodel.PhotosState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FavScreen(
    navController: NavHostController, modifier: Modifier = Modifier,
    animatedVisibilityScope : AnimatedVisibilityScope,
    favViewModel: FavViewModel = viewModel()
) {

    LaunchedEffect(key1 = true) {
        favViewModel.processIntent(FavIntent.GetLikedPhotos)
    }

    Column(modifier = modifier) {

        when (val state = favViewModel.likedFavIntent.collectAsState().value) {
            is PhotosState.Loading -> LoadingComponent(modifier)

            is PhotosState.Error -> {
                ErrorComponent(message = state.message, modifier, onRetry = {
                    favViewModel.processIntent(FavIntent.GetLikedPhotos)
                })
            }

            is PhotosState.OnLikedPhotoResult->{
                ListPhotos(state.photoList,animatedVisibilityScope){
                    navController.navigate(NavRoute.PhotoDetailScreenRoute(photo = it.urls?.full?:"",description = it.description?:"",likes = it.likes?:0))
                }
            }

            else->{}
        }
    }
}