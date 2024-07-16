package com.meet.photosappmvi.presentation.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import com.meet.photosappmvi.data.model.Photo

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ListPhotos(photoList: List<Photo>, animatedVisibilityScope: AnimatedVisibilityScope, modifier: Modifier = Modifier, onclick:(String)->Unit) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(photoList.size) { index ->
            val photo = photoList[index]

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 5.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                onClick = { onclick(Gson().toJson(photo)) }
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    if (photo.urls?.full.isNullOrEmpty().not()) {
                        AsyncImage(
                            model = photo.urls?.full,
                            contentDescription = "thumb",
                            modifier = modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clip(RoundedCornerShape(8.dp)).sharedElement(
                                    state = rememberSharedContentState(key = "image/${photo.urls?.full}"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { _, _ ->
                                        tween(durationMillis = 500)
                                    }
                                ),
                            contentScale = ContentScale.Crop,
                            placeholder = rememberAsyncImagePainter(photo.urls?.thumb),
                            error = rememberAsyncImagePainter(photo.urls?.thumb)
                        )
                    }
                }
            }
        }
    }
}
