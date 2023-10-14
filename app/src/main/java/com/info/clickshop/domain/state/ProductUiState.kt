package com.info.clickshop.domain.state

import com.info.clickshop.common.util.NetworkResponseState
import com.info.clickshop.data.dto.Product
import com.info.clickshop.data.dto.ProductsDTO


sealed class ProductUiState {

    object Loading : ProductUiState()
    data class Success(val data: Product) : ProductUiState()
    data class Error(val message: String) : ProductUiState()
}