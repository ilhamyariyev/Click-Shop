package com.info.clickshop.presentation.ui.explore

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
class CategoryProductsMVVM @Inject constructor(
    private val repository:ClickShopRepository
):ViewModel(){

    private val _categoryProduct= MutableLiveData<DummyProductsUiState>()
    val categoryProduct: LiveData<DummyProductsUiState> = _categoryProduct


    fun getCategoryProduct(category:String) {
        viewModelScope.launch {
            repository.getProductsofCategory(category).collectLatest {
                when (it) {
                    is NetworkResponseState.Success ->{
                        _categoryProduct.value = it.result?.let { it1 -> DummyProductsUiState.Success(it1) }
                    }

                    is NetworkResponseState.Error -> {
                        _categoryProduct.value = DummyProductsUiState.Error(it.exception.message.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _categoryProduct.value = DummyProductsUiState.Loading
                    }


                }
            }
        }
    }
}