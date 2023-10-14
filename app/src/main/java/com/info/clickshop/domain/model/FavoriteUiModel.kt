package com.info.clickshop.domain.model

data class FavoriteUiModel(
    val id : Int,
    val title : String,
    val rating:Int,
    val price:Int,
    val discount : Int,
    val image : String,
)