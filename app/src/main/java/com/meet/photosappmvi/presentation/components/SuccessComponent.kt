package com.meet.photosappmvi.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.meet.photosappmvi.data.model.Photo

@Composable
fun SuccessComponent(
    lazyPagingItems: LazyPagingItems<Photo>,
    modifier: Modifier = Modifier,
    onSearchClick: (String) -> Unit
) {

}