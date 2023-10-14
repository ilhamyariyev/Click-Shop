package com.info.clickshop.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.info.clickshop.common.util.ValidationHelper
import com.info.clickshop.data.api.ClickShopApi
import com.info.clickshop.common.util.Constants
import com.info.clickshop.data.local.db.RoomDB
import com.info.clickshop.data.local.db.cart.CartDAO
import com.info.clickshop.data.local.db.favorite.FavoriteDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object appModule {


    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideValidationHelper(@ApplicationContext context:Context) = ValidationHelper(context)

    @Provides
    @Singleton
    fun getClickShopApi(): ClickShopApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL_DUMMY)
            .addConverterFactory(GsonConverterFactory.create()).build().create()
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): RoomDB =
        Room.databaseBuilder(
            context,
            RoomDB::class.java,
            "ProductDB"
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideFavDao(db: RoomDB): FavoriteDAO = db.getFavDao()

    @Singleton
    @Provides
    fun provideCartDao(db: RoomDB): CartDAO = db.getCartDao()


}