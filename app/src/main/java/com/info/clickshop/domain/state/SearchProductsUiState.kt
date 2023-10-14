package com.info.clickshop.domain.state

import com.info.clickshop.data.dto.Product


sealed class SearchProductsUiState {

    object Loading : SearchProductsUiState()
    data class Success(val data: List<Product>) : SearchProductsUiState()
    data class Error(val message: String) : SearchProductsUiState()

}