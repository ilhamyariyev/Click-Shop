package com.info.clickshop.presentation.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.info.clickshop.common.util.NetworkResponseState
import com.info.clickshop.data.local.db.cart.CartDTO
import com.info.clickshop.data.local.db.favorite.FavoriteDTO
import com.info.clickshop.data.repository.ClickShopRepository
import com.info.clickshop.domain.mapper.Mapper.toCartDTO
import com.info.clickshop.domain.mapper.Mapper.toCartUiList
import com.info.clickshop.domain.model.CartUiModel
import com.info.clickshop.domain.state.CartUiState
import com.info.clickshop.domain.state.FavoriteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartMVVM @Inject constructor(
    private val repository: ClickShopRepository
): ViewModel() {

    private val _cart = MutableLiveData<CartUiState>()
    val cart : LiveData<CartUiState> get() = _cart

    private val _totalPrice = MutableLiveData(0)
    val totalPrice: LiveData<Int> get() = _totalPrice

    init {
        getCart()
    }

    private fun getCart(){
        viewModelScope.launch {
            repository.getCart().collectLatest {
                when(it){
                    is NetworkResponseState.Success->{
                        _cart.value = it.result?.let { it1 -> CartUiState.Success(it1) }
                        setTotal(it.result?.toCartUiList() ?: emptyList())
                    }
                    is NetworkResponseState.Error->{
                        _cart.value = CartUiState.Error("error fav")
                    }
                    is NetworkResponseState.Loading->{
                        _cart.value = CartUiState.Loading
                    }

                }
            }
        }
    }

    fun deleteCart(product: CartDTO){
        viewModelScope.launch {
            repository.deleteCart(product)
            getCart()
        }
    }

    fun decreasePrice(price: Int) {
        _totalPrice.value = _totalPrice.value?.minus(price)
    }

    fun increasePrice(price: Int) {
        _totalPrice.value = _totalPrice.value?.plus(price)
    }

    private fun setTotal(list: List<CartUiModel>) {
        list.forEach { increasePrice(it.price) }
    }

}