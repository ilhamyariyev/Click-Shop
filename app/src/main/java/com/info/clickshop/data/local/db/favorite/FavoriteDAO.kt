package com.info.clickshop.data.local.db.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.info.clickshop.data.local.db.favorite.FavoriteDTO


@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFav(product: FavoriteDTO)

    @Delete
    suspend fun deleteFav(product: FavoriteDTO)

    @Query("SELECT * FROM favorite_product")
    suspend fun getFav(): List<FavoriteDTO>

}