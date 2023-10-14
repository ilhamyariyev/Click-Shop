package com.info.clickshop.domain.state

import com.info.clickshop.data.dto.ProductsDTO


sealed class DummyProductsUiState {

    object Loading : DummyProductsUiState()
    data class Success(val data: ProductsDTO) : DummyProductsUiState()
    data class Error(val message: String) : DummyProductsUiState()
}