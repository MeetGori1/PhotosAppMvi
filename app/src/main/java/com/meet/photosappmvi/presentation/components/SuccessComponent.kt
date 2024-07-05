package com.meet.photosappmvi.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.meet.photosappmvi.data.model.Photos

@Composable
fun SuccessComponent(
    lazyPagingItems: LazyPagingItems<Photos>,
    modifier: Modifier = Modifier,
    onSearchClick: (String) -> Unit
) {
    Column() {
        SearchComponent(onSearchClick = onSearchClick)
        ListPhotos(lazyPagingItems = lazyPagingItems)
    }
}