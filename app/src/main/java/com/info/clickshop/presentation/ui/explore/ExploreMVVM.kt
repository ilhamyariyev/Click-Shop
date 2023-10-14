package com.info.clickshop.presentation.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.info.clickshop.common.util.NetworkResponseState
import com.info.clickshop.data.repository.ClickShopRepository
import com.info.clickshop.domain.state.CategoryUiState
import com.info.clickshop.domain.state.SearchProductsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExploreMVVM @Inject constructor(
    private val repository: ClickShopRepository
) : ViewModel(){

    private val _categories = MutableLiveData<CategoryUiState>()
    val categories: LiveData<CategoryUiState> = _categories

    private val _searchData = MutableLiveData<SearchProductsUiState>()
    val searchData: LiveData<SearchProductsUiState> get() = _searchData

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

    fun getSearch(query: String) {
        viewModelScope.launch {
            repository.getSearchData(query).collectLatest {
                when (it) {
                    is NetworkResponseState.Success-> {
                        _searchData.value = it.result?.products?.let { it1 -> SearchProductsUiState.Success(it1) }
                    }
                    is NetworkResponseState.Error-> {
                        _searchData.value = SearchProductsUiState.Error(it.exception.message.toString())
                    }
                    is NetworkResponseState.Loading-> {
                        _searchData.value = SearchProductsUiState.Loading
                    }
                }
            }
        }

    }
}