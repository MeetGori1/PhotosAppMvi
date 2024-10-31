package com.meet.photosappmvi.domain.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BaseModel<T>(
    @SerialName("error") var error: Boolean? = null,
    @SerialName("message") var message: String? = null,
    @SerialName("statusCode") var statusCode: Int? = null,
    @SerialName("messageCode") var messageCode: String? = null,
    @SerialName("results") var data: T? = null
)

@Serializable
data class BaseListModel<T>(
    @SerialName("error") var error: Boolean? = null,
    @SerialName("message") var message: String? = null,
    @SerialName("statusCode") var statusCode: Int? = null,
    @SerialName("messageCode") var messageCode: String? = null,
    @SerialName("results") var data: List<T> = arrayListOf()
)

@Serializable
data class ErrorResponse(
    @SerialName("error") val error: Boolean,
    @SerialName("message") val message: String,
    @SerialName("statusCode") val statusCode: Int,
    @SerialName("messageCode") val messageCode: String,
    @SerialName("errorMessage") val errorMessage: String
)

@Serializable
data class EmptyData(@SerialName("isEmpty") val isEmpty: Boolean = true)
