package com.info.clickshop.domain.state

import com.info.clickshop.data.local.db.cart.CartDTO
import com.info.clickshop.data.local.db.favorite.FavoriteDTO
import com.info.clickshop.domain.model.CartUiModel

sealed class CartUiState {

    object Loading : CartUiState()
    data class Success(val data: List<CartDTO>) : CartUiState()
    data class Error(val message: String) : CartUiState()
}