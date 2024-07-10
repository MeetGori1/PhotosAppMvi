package com.meet.photosappmvi.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    @SerializedName("id") var id: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null,
    @SerializedName("color") var color: String? = null,
    @SerializedName("blur_hash") var blurHash: String? = null,
    @SerializedName("likes") var likes: Int? = null,
    @SerializedName("liked_by_user") var likedByUser: Boolean? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("user") var user: User? = User(),
    @SerializedName("current_user_collections") var currentUserCollections: ArrayList<CurrentUserCollections> = arrayListOf(),
    @SerializedName("urls") var urls: Urls? = Urls(),
    @SerializedName("links") var links: Links? = Links()
)

@Serializable
data class ProfileImage(
    @SerializedName("small") var small: String? = null,
    @SerializedName("medium") var medium: String? = null,
    @SerializedName("large") var large: String? = null
)

@Serializable
data class Links(
    @SerializedName("self") var self: String? = null,
    @SerializedName("html") var html: String? = null,
    @SerializedName("photos") var photos: String? = null,
    @SerializedName("likes") var likes: String? = null,
    @SerializedName("portfolio") var portfolio: String? = null,
    @SerializedName("download") var download: String? = null,
    @SerializedName("download_location") var downloadLocation: String? = null
)

@Serializable
data class CurrentUserCollections(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("published_at") var publishedAt: String? = null,
    @SerializedName("last_collected_at") var lastCollectedAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null
)

@Serializable
data class Urls(
    @SerializedName("raw") var raw: String? = null,
    @SerializedName("full") var full: String? = null,
    @SerializedName("regular") var regular: String? = null,
    @SerializedName("small") var small: String? = null,
    @SerializedName("thumb") var thumb: String? = null
)
