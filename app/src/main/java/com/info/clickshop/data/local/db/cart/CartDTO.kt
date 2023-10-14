package com.info.clickshop.data.local.db.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("cart_product")
data class CartDTO (
    @PrimaryKey(false)
    val id : Int,
    val title : String,
    val price:Int,
    val image : String,
    var quantity : Int=1
)