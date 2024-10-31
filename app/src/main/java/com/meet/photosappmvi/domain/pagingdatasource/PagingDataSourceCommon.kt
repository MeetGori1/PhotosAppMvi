package com.meet.photosappmvi.domain.pagingdatasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.meet.photosappmvi.data.model.Photo
import com.meet.photosappmvi.domain.api.HttpRoutes
import com.meet.photosappmvi.domain.repository.PhotosRepository
import com.meet.photosappmvi.domain.wrapper.Response

class PagingDataSource(private val endPoint: String, private val query: String? = null) :
    PagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: 1
        when (endPoint) {
            HttpRoutes.GET_PHOTOS -> {
                when (val response = PhotosRepository.getPhotos(page, params.loadSize)) {
                    is Response.Success -> {
                        val photos = response.value.data
                        return LoadResult.Page(
                            data = photos,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (photos.isEmpty()) null else page + 1
                        )
                    }

                    is Response.Error -> {
                        return LoadResult.Error(Exception(response.error))
                    }

                    else -> return LoadResult.Error(Exception("Something went wrong"))
                }
            }

            HttpRoutes.SEARCH_PHOTOS -> {
                when (val response =
                    PhotosRepository.getSearchedPhotos(page, params.loadSize, query ?: "")) {
                    is Response.Success -> {
                        val photos = response.value.data
                        return LoadResult.Page(
                            data = photos,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (photos.isEmpty() == true) null else page + 1
                        )
                    }

                    is Response.Error -> {
                        return LoadResult.Error(Exception(response.error))
                    }

                    else -> return LoadResult.Error(Exception("Something went wrong"))
                }
            }

            else -> return LoadResult.Error(Exception("Wrong Url"))
        }
    }
}