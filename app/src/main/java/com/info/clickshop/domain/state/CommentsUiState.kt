package com.info.clickshop.domain.state

import com.info.clickshop.data.dto.CommentsDTO


sealed class CommentsUiState {

    object Loading : CommentsUiState()
    data class Success(val data: CommentsDTO) : CommentsUiState()
    data class Error(val message: String) : CommentsUiState()
}