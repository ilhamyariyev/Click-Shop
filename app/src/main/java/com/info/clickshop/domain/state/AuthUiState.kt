package com.info.clickshop.domain.state


sealed class AuthUiState {

    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}