package com.info.clickshop.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.info.clickshop.common.util.NetworkResponseState
import com.info.clickshop.data.repository.ClickShopRepository
import com.info.clickshop.domain.state.CategoryUiState
import com.info.clickshop.domain.state.DummyProductsUiState
import com.info.clickshop.domain.state.SearchProductsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeMVVM @Inject constructor(
    private val repository:ClickShopRepository
) :ViewModel(){

    private val _top5Products = MutableLiveData<DummyProductsUiState>()
    val top5Products: LiveData<DummyProductsUiState> = _top5Products

    private val _categories = MutableLiveData<CategoryUiState>()
    val categories: LiveData<CategoryUiState> = _categories

    private val _allProducts = MutableLiveData<DummyProductsUiState>()
    val allProducts: LiveData<DummyProductsUiState> = _allProducts

    private val _top10DummyProducts = MutableLiveData<DummyProductsUiState>()
    val top10DummyProducts: LiveData<DummyProductsUiState> = _top10DummyProducts

    private val _allDummyProducts = MutableLiveData<DummyProductsUiState>()
    val allDummyProducts: LiveData<DummyProductsUiState> = _allDummyProducts

    private val _searchData = MutableLiveData<SearchProductsUiState>()
    val searchData: LiveData<SearchProductsUiState> get() = _searchData


    init {
        getCategories()
        getAllDummyProducts()
        get5Products(5)
        get10MegaProducts(10)
        get10FlashProducts(10)
    }


    private fun get10MegaProducts(limit: Int) {
        viewModelScope.launch {
            repository.get10MegaProductsData(limit).collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        _top10DummyProducts.value = it.result?.let { it1 -> DummyProductsUiState.Success(it1) }

                    }

                    is NetworkResponseState.Error -> {
                        _top10DummyProducts.value = DummyProductsUiState.Error(it.exception.message.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _top10DummyProducts.value = DummyProductsUiState.Loading
                    }
                }
            }
        }
    }

    private fun get10FlashProducts(limit: Int) {
        viewModelScope.launch {
            repository.get10FlashProductsData(limit).collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        _allProducts.value = it.result?.let { it1 -> DummyProductsUiState.Success(it1) }

                    }

                    is NetworkResponseState.Error -> {
                        _allProducts.value = DummyProductsUiState.Error(it.exception.message.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _allProducts.value = DummyProductsUiState.Loading
                    }
                }
            }
        }
    }

    private fun getAllDummyProducts() {
        viewModelScope.launch {
            repository.getAllProductsData().collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        _allDummyProducts.value = it.result?.let { it1 -> DummyProductsUiState.Success(it1) }

                    }

                    is NetworkResponseState.Error -> {
                        _allDummyProducts.value = DummyProductsUiState.Error(it.exception.message.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _allDummyProducts.value = DummyProductsUiState.Loading
                    }
                }
            }
        }
    }



    private fun get5Products(limit:Int) {
        viewModelScope.launch {
            repository.get5ProductData(limit).collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        _top5Products.value = it.result?.let { it1 -> DummyProductsUiState.Success(it1) }

                    }

                    is NetworkResponseState.Error -> {
                        _top5Products.value = DummyProductsUiState.Error(it.exception.message.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _top5Products.value = DummyProductsUiState.Loading
                    }
                }
            }
        }
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