package com.info.clickshop.data.api

import com.info.clickshop.data.dto.CommentsDTO
import com.info.clickshop.data.dto.Product
import com.info.clickshop.data.dto.ProductsDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ClickShopApi {

    @GET("products")
    suspend fun get5Product(
        @Query("limit") limit:Int
    ): ProductsDTO

    @GET("products")
    suspend fun get10FlashProductsApi(
        @Query("limit") limit:Int
    ): ProductsDTO

    @GET("products")
    suspend fun get10MegaProductsApi(
        @Query("limit") limit:Int
    ): ProductsDTO

    @GET("products")
    suspend fun getAllFSProductsApi(): ProductsDTO

    @GET("products/14")
    suspend fun getSingleDummyProductApi(
    ): Product

    @GET("products/7")
    suspend fun getSingleProductApi(
    ): Product

    @GET("products/categories")
    suspend fun getCategoriesApi(): List<String>

    @GET("products")
    suspend fun getAllProductsApi(): ProductsDTO

    @GET("products/search")
    suspend fun getSearch(
        @Query("q") query: String,
    ) : ProductsDTO

    @GET("products/{id}")
    suspend fun getProductDetail(
        @Path("id") id: Int,
    ): Product

    @GET("comments")
    suspend fun get10CommentsApi(
        @Query("limit") limit:Int
    ): CommentsDTO

    @GET("products/category/{category}")
    suspend fun getProductsofCategory(
        @Path("category") category: String,
    ): ProductsDTO
}