package com.meet.photosappmvi.domain.api

import com.meet.photosappmvi.data.model.Photo
import com.meet.photosappmvi.data.model.User
import com.meet.photosappmvi.domain.wrapper.Response
import io.ktor.http.HttpMethod
import io.ktor.http.path

class ApiService : BaseApiService() {

    suspend fun getPhotos(page: Int, perPage: Int):Response<BaseListModel<Photo>> =
        safeListApiCall(
            method = HttpMethod.Get,
            urlBuilder = {
                path(HttpRoutes.GET_PHOTOS)
                parameters.append("page", page.toString())
                parameters.append("per_page", perPage.toString())
            }
        )

    suspend fun getSearchedPhotos(query: String, page: Int, perPage: Int): Response<BaseListModel<Photo>> =
        safeListApiCall(
            method = HttpMethod.Get,
            urlBuilder = {
                path(HttpRoutes.SEARCH_PHOTOS)
                parameters.append("page", page.toString())
                parameters.append("per_page", perPage.toString())
                parameters.append("query", query)
            }
        )



    suspend fun getLikedPhotos(): Response<List<Photo>> =
        safeApiCall (
            method = HttpMethod.Get,
            urlBuilder = {  path(HttpRoutes.GET_LIKED_PHOTOS) } )

    suspend fun getUserProfile(): Response<User> = safeApiCall (
        method = HttpMethod.Get,
        urlBuilder = {  path(HttpRoutes.GET_USER) }
    )


// this is sample for post
//    suspend fun post(request: LoginRequest): ApiResponse<BaseModel<UserModel>> =
//        safeApiCall(
//            method = HttpMethod.Post,
//            urlBuilder = { path("login endpoint) },
//            body = request
//        )

//this is sample for get with params
//    suspend fun getWithParams(
//        month: Int?,
//        year: Int?
//    ): ApiResponse<BaseListModel<responseModel>> =
//        safeListApiCall(
//            method = HttpMethod.Get,
//            urlBuilder = {
//                path("end point")
//                if (month != null) parameters.append("data", data.toString())
//                if (year != null) parameters.append("data", data.toString())
//            }
//        )
//

    // this is sample for post with body
//    suspend fun postWithBody(
//       data:Data
//    ): ApiResponse<BaseModel<AgoraModel>> =
//        safeApiCall(
//            method = HttpMethod.Post,
//            urlBuilder = { path("end point") },
//            body = data
//        )


    //this is sample for patch with append id at and of url
//    suspend fun onPatchMethod(
//        userId: String
//    ): ApiResponse<BaseListModel<EmptyData>> =
//        safeListApiCall(
//            method = HttpMethod.Patch,
//            urlBuilder = {
//                path("end point", userId)
//            }
//        )
//

    // this is for upload media via multipart work with any type of data
//    suspend fun uploadMedia(data: Data): ApiResponse<BaseModel<Response>> =
//        safeApiCall(
//            method = HttpMethod.Patch,
//            urlBuilder = {
//                path("upload media end point", data.userId ?: "")
//            },
//            body = MultiPartFormDataContent(formData {
//                data.media?.forEach { file ->
//                    append("media", file.readBytes(), Headers.build {
//                        append(HttpHeaders.ContentType, file.extension)
//                        append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
//                    })
//                }
//            }),
//            contentType = ContentType.MultiPart.FormData
//        )


}