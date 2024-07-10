package com.meet.photosappmvi.presentation.components

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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.meet.photosappmvi.data.model.Photos

@Composable
fun ListPhotosPaging(lazyPagingItems: LazyPagingItems<Photos>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(lazyPagingItems.itemCount) { index ->
            val photo = lazyPagingItems[index]
            if (photo != null) {
                PhotoItem(item = photo)
            }
        }

        lazyPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingComponent(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingComponent(modifier = Modifier.fillMaxWidth()) }
                }

                loadState.refresh is LoadState.Error -> {
                    val errorState = lazyPagingItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorComponent(
                            message = errorState.error.message ?: "An error occurred",
                            modifier = Modifier.fillParentMaxSize(),
                            onRetry = { retry() }
                        )
                    }
                }

                loadState.append is LoadState.Error -> {
                    val errorState = lazyPagingItems.loadState.append as LoadState.Error
                    item {
                        ErrorComponent(
                            message = errorState.error.message ?: "An error occurred",
                            modifier = Modifier.fillMaxWidth(),
                            onRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun PhotoItem(item: Photos, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            if (item.urls?.full.isNullOrEmpty().not()) {
                AsyncImage(
                    model = item.urls?.full,
                    contentDescription = "thumb",
                    modifier = modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    placeholder = rememberAsyncImagePainter(item.urls?.thumb),
                    error = rememberAsyncImagePainter(item.urls?.thumb)
                )
            }
        }
    }
}