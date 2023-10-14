package com.info.clickshop.domain.state


sealed class CategoryUiState {

    object Loading : CategoryUiState()
    data class Success(val data: List<String>) : CategoryUiState()
    data class Error(val message: String) : CategoryUiState()
}