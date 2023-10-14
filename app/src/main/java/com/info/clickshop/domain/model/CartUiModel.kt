package com.info.clickshop.domain.model

data class CartUiModel(
    val id : Int,
    val title : String,
    var price:Int,
    val image : String,
    var quantity : Int
)
