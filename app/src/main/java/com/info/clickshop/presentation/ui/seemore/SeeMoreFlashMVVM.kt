package com.info.clickshop.presentation.ui.seemore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.info.clickshop.common.util.NetworkResponseState
import com.info.clickshop.data.repository.ClickShopRepository
import com.info.clickshop.domain.state.DummyProductsUiState
import com.info.clickshop.domain.state.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SeeMoreFlashMVVM @Inject constructor(
    private val repository: ClickShopRepository
):ViewModel() {

    private val _singleProduct = MutableLiveData<ProductUiState>()
    val singleProduct: LiveData<ProductUiState> = _singleProduct

    private val _allDummyProducts = MutableLiveData<DummyProductsUiState>()
    val allDummyProducts: LiveData<DummyProductsUiState> = _allDummyProducts

    init {
        getSingleProduct()
        getAllDummyProducts()
    }

    private fun getSingleProduct() {
        viewModelScope.launch {
            repository.getSingleProductData().collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        _singleProduct.value = it.result?.let { it1 ->
                            ProductUiState.Success(
                                it1
                            )
                        }

                    }

                    is NetworkResponseState.Error -> {
                        _singleProduct.value = ProductUiState.Error(it.exception.message.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _singleProduct.value = ProductUiState.Loading
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


}