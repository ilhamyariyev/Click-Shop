package com.info.clickshop.data.local.db.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.info.clickshop.data.local.db.cart.CartDTO

@Dao
interface CartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCart(product: CartDTO)

    @Delete
    suspend fun deleteCart(product: CartDTO)

    @Query("SELECT * FROM cart_product")
    suspend fun getCart() : List<CartDTO>

}