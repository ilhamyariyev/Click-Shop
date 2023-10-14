package com.info.clickshop.data.dto


import com.google.gson.annotations.SerializedName

data class ProductsDTO(
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("total")
    val total: Int
)