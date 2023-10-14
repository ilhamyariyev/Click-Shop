package com.info.clickshop.domain.state

import com.info.clickshop.data.local.db.favorite.FavoriteDTO

sealed class FavoriteUiState {

    object Loading : FavoriteUiState()
    data class Success(val data: List<FavoriteDTO>) : FavoriteUiState()
    data class Error(val message: String) : FavoriteUiState()
}