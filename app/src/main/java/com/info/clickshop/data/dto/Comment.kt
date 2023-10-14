package com.info.clickshop.data.dto


import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("body")
    val body: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("postId")
    val postId: Int,
    @SerializedName("user")
    val user: User
)