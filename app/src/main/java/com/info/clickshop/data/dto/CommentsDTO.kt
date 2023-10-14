package com.info.clickshop.data.dto


import com.google.gson.annotations.SerializedName

data class CommentsDTO(
    @SerializedName("comments")
    val comments: List<Comment>,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("total")
    val total: Int
)