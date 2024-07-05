package com.meet.photosappmvi.domain.pagingdatasource

import android.annotation.SuppressLint
import android.net.http.HttpException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.meet.photosappmvi.data.model.Photos
import com.meet.photosappmvi.domain.api.HttpRoutes
import com.meet.photosappmvi.domain.repository.PhotosRepository
import org.json.JSONException
import java.io.IOException

class PagingDataSource(private val endPoint: String, private val query: String? = null) :
    PagingSource<Int, Photos>() {

    override fun getRefreshKey(state: PagingState<Int, Photos>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    @SuppressLint("NewApi")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photos> {
        val page = params.key ?: 1
        try {
            val photos = when (endPoint) {
                HttpRoutes.GET_PHOTOS -> {
                    PhotosRepository.getPhotos(page, params.loadSize)
                }

                HttpRoutes.SEARCH_PHOTOS -> {
                    PhotosRepository.getSearchedPhotos(page, params.loadSize, query ?: "").results
                }

                else -> null
            }

            return LoadResult.Page(
                data = photos ?: mutableListOf(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (photos.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            exception.printStackTrace()
            return LoadResult.Error(exception)
        } catch (httpException: HttpException) {
            httpException.printStackTrace()
            return LoadResult.Error(httpException)
        }catch (e: NullPointerException){
            e.printStackTrace()
            return LoadResult.Error(e)
        }catch (e: JSONException){
            e.printStackTrace()
            return LoadResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }
}