package com.info.clickshop.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.info.clickshop.common.util.NetworkResponseState
import com.info.clickshop.data.repository.ClickShopRepository
import com.info.clickshop.domain.mapper.Mapper.toCartDTO
import com.info.clickshop.domain.mapper.Mapper.toFavoriteDTO
import com.info.clickshop.domain.model.ProductUiModel
import com.info.clickshop.domain.state.CommentsUiState
import com.info.clickshop.domain.state.DummyProductsUiState
import com.info.clickshop.domain.state.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailMVVM @Inject constructor(
    private val repository: ClickShopRepository
):ViewModel(){
    private val _detailProduct= MutableLiveData<ProductUiState>()
    val detailProduct: LiveData<ProductUiState> = _detailProduct

    private val _alsoLikeProducts = MutableLiveData<DummyProductsUiState>()
    val alsoLikeProducts: LiveData<DummyProductsUiState> = _alsoLikeProducts

    private val _comments = MutableLiveData<CommentsUiState>()
    val comments: LiveData<CommentsUiState> = _comments

    init {
        getAlsoLikeProducts(10)
        getComments(5)
    }


    fun getDetailProduct(id : Int) {
        viewModelScope.launch {
            repository.getDetails(id).collectLatest {
                when (it) {
                    is NetworkResponseState.Success ->
                         it.result?.let { it1 ->
                             _detailProduct.value = ProductUiState.Success(it1)
                    }

                    is NetworkResponseState.Error -> {
                        _detailProduct.value = ProductUiState.Error(it.exception.message.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _detailProduct.value = ProductUiState.Loading
                    }


                }
            }
        }
    }

    private fun getAlsoLikeProducts(limit: Int) {
        viewModelScope.launch {
            repository.get10FlashProductsData(limit).collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        _alsoLikeProducts.value = it.result?.let { it1 -> DummyProductsUiState.Success(it1) }

                    }

                    is NetworkResponseState.Error -> {
                        _alsoLikeProducts.value = DummyProductsUiState.Error(it.exception.message.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _alsoLikeProducts.value = DummyProductsUiState.Loading
                    }
                }
            }
        }
    }

    private fun getComments(limit: Int) {
        viewModelScope.launch {
            repository.get10CommentsData(limit).collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        _comments.value = it.result?.let { it1 -> CommentsUiState.Success(it1) }

                    }

                    is NetworkResponseState.Error -> {
                        _comments.value = CommentsUiState.Error(it.exception.message.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _comments.value = CommentsUiState.Loading
                    }
                }
            }
        }
    }


    fun addFavorite(product:ProductUiModel){
        viewModelScope.launch {
            repository.addFav(product.toFavoriteDTO())
        }
    }

    fun deleteData(product:ProductUiModel){
        viewModelScope.launch {
            repository.deleteFav(product.toFavoriteDTO())
        }
    }

    fun addCartProduct(product: ProductUiModel) {
        viewModelScope.launch {
            repository.addCart(product.toCartDTO())
        }
    }
}