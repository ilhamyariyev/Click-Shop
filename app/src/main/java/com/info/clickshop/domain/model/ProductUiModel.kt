package com.info.clickshop.domain.model


data class ProductUiModel(
    val id: Int,
    val title: String,
    val description: String,
    val rating: Int,
    val discount: Double,
    val images: List<String>,
    val price: Int,
    val originalPrice: Double,
    val stock: Int,
    val category: String,
    val brand: String,
    val thumbnail: String,
)
