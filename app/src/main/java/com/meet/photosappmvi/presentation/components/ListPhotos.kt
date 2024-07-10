package com.meet.photosappmvi.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.meet.photosappmvi.data.model.Photos

@Composable
fun ListPhotos(photoList: List<Photos>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(photoList.size) { index ->
            val photo = photoList[index]
            PhotoItem(item = photo)
        }
    }
}

