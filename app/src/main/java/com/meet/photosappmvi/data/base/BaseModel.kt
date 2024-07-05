package com.meet.photosappmvi.data.base

import com.google.gson.annotations.SerializedName
import com.meet.photosappmvi.data.model.Photos
import kotlinx.serialization.Serializable

@Serializable
data class BaseModel(
    @SerializedName("total") var total: Int? = null,
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("results") var results: List<Photos> = arrayListOf()
)