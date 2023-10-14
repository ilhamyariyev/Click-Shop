package com.info.clickshop.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.info.clickshop.data.local.db.cart.CartDAO
import com.info.clickshop.data.local.db.cart.CartDTO
import com.info.clickshop.data.local.db.favorite.FavoriteDAO
import com.info.clickshop.data.local.db.favorite.FavoriteDTO


@Database(entities = [FavoriteDTO::class, CartDTO::class], version = 2, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun getFavDao(): FavoriteDAO
    abstract fun getCartDao(): CartDAO
}