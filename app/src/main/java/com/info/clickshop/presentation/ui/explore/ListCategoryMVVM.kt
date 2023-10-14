package com.info.clickshop.presentation.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.info.clickshop.common.util.NetworkResponseState
import com.info.clickshop.data.repository.ClickShopRepository
import com.info.clickshop.domain.state.CategoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListCategoryMVVM @Inject constructor(
    private val repository: ClickShopRepository
) : ViewModel(){

    private val _categories = MutableLiveData<CategoryUiState>()
    val categories: LiveData<CategoryUiState> = _categories

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            repository.getCategories().collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        _categories.value = it.result?.let { it1 -> CategoryUiState.Success(it1) }

                    }

                    is NetworkResponseState.Error -> {
                        _categories.value = CategoryUiState.Error(it.exception.message.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _categories.value = CategoryUiState.Loading
                    }
                }
            }
        }
    }

}