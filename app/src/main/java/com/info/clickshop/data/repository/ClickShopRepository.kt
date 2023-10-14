package com.info.clickshop.data.repository


import com.bumptech.glide.load.engine.Resource
import com.info.clickshop.common.util.NetworkResponseState
import com.info.clickshop.data.api.ClickShopApi
import com.info.clickshop.data.dto.CommentsDTO
import com.info.clickshop.data.dto.Product
import com.info.clickshop.data.dto.ProductsDTO
import com.info.clickshop.data.local.db.cart.CartDAO
import com.info.clickshop.data.local.db.cart.CartDTO
import com.info.clickshop.data.local.db.favorite.FavoriteDAO
import com.info.clickshop.data.local.db.favorite.FavoriteDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClickShopRepository @Inject constructor(
    private val api: ClickShopApi,
    private val favoriteDAO: FavoriteDAO,
    private val cartDAO: CartDAO,
) {

    fun get10MegaProductsData(limit:Int): Flow<NetworkResponseState<ProductsDTO>> = flow {
        emit(NetworkResponseState.Loading)
        val response = api.get10MegaProductsApi(limit)
        emit(NetworkResponseState.Success(response))
    }.catch {
        emit(NetworkResponseState.Error(it as Exception))
    }

    fun get10FlashProductsData(limit:Int): Flow<NetworkResponseState<ProductsDTO>> = flow {
        emit(NetworkResponseState.Loading)
        val response = api.get10FlashProductsApi(limit)
        emit(NetworkResponseState.Success(response))
    }.catch {
        emit(NetworkResponseState.Error(it as Exception))
    }

    fun getAllProductsData(): Flow<NetworkResponseState<ProductsDTO>> = flow {
        emit(NetworkResponseState.Loading)
        val response = api.getAllProductsApi()
        emit(NetworkResponseState.Success(response))
    }.catch {
        emit(NetworkResponseState.Error(it as Exception))
    }

    fun getAllFSProductsData(): Flow<NetworkResponseState<ProductsDTO>> = flow {
        emit(NetworkResponseState.Loading)
        val response = api.getAllFSProductsApi()
        emit(NetworkResponseState.Success(response))
    }.catch {
        emit(NetworkResponseState.Error(it as Exception))
    }

    fun getSingleProductData(): Flow<NetworkResponseState<Product>> = flow {
        emit(NetworkResponseState.Loading)
        val response = api.getSingleProductApi()
        emit(NetworkResponseState.Success(response))
    }.catch {
        emit(NetworkResponseState.Error(it as Exception))
    }

    fun getSingleFSProductData(): Flow<NetworkResponseState<Product>> = flow {
        emit(NetworkResponseState.Loading)
        val response = api.getSingleDummyProductApi()
        emit(NetworkResponseState.Success(response))
    }.catch {
        emit(NetworkResponseState.Error(it as Exception))
    }

    fun getCategories() : Flow<NetworkResponseState<List<String>>> = flow {
        emit(NetworkResponseState.Loading)
        val response = api.getCategoriesApi()
        emit(NetworkResponseState.Success(response))
    }.catch {
        emit(NetworkResponseState.Error(it as Exception))
    }

    fun get5ProductData(limit:Int) : Flow<NetworkResponseState<ProductsDTO>> = flow {
        emit(NetworkResponseState.Loading)
        val response = api.get5Product(limit)
        emit(NetworkResponseState.Success(response))
    }.catch {
        emit(NetworkResponseState.Error(it as Exception))
    }

    fun getSearchData(query:String) : Flow<NetworkResponseState<ProductsDTO>> = flow {
        emit(NetworkResponseState.Loading)
        val response = api.getSearch(query)
        emit(NetworkResponseState.Success(response))
    }.catch {
        emit(NetworkResponseState.Error(it as Exception))
    }

    fun getDetails(id:Int) : Flow<NetworkResponseState<Product>> = flow {
        emit(NetworkResponseState.Loading)
        val response = api.getProductDetail(id)
        emit(NetworkResponseState.Success(response))
    }.catch {
        emit(NetworkResponseState.Error(it as Exception))
    }

    fun get10CommentsData(limit:Int): Flow<NetworkResponseState<CommentsDTO>> = flow {
        emit(NetworkResponseState.Loading)
        val response = api.get10CommentsApi(limit)
        emit(NetworkResponseState.Success(response))
    }.catch {
        emit(NetworkResponseState.Error(it as Exception))
    }

    fun getProductsofCategory(category:String) : Flow<NetworkResponseState<ProductsDTO>> = flow {
        emit(NetworkResponseState.Loading)
        val response = api.getProductsofCategory(category)
        emit(NetworkResponseState.Success(response))
    }.catch {
        emit(NetworkResponseState.Error(it as Exception))
    }

    suspend fun addFav(product: FavoriteDTO) {
        favoriteDAO.addFav(product)
    }

    suspend fun deleteFav(product: FavoriteDTO) {
        favoriteDAO.deleteFav(product)
    }

    suspend fun getFavorites(): Flow<NetworkResponseState<List<FavoriteDTO>>> = flow {
        emit(NetworkResponseState.Loading)
        val response = favoriteDAO.getFav()
        emit(NetworkResponseState.Success(response))
    }.catch {
        emit(NetworkResponseState.Error(it as Exception))
    }

    suspend fun addCart(product: CartDTO) {
        cartDAO.addCart(product)
    }

    suspend fun deleteCart(product: CartDTO) {
        cartDAO.deleteCart(product)
    }

    suspend fun getCart(): Flow<NetworkResponseState<List<CartDTO>>> = flow {
        emit(NetworkResponseState.Loading)
        val response = cartDAO.getCart()
        emit(NetworkResponseState.Success(response))
    }.catch {
        emit(NetworkResponseState.Error(it as Exception))
    }
  
}