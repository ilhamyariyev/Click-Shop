package com.info.clickshop.presentation.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.info.clickshop.common.util.NetworkResponseState
import com.info.clickshop.data.local.db.favorite.FavoriteDTO
import com.info.clickshop.data.repository.ClickShopRepository
import com.info.clickshop.domain.state.FavoriteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMVVM @Inject constructor(
    private val repository: ClickShopRepository
): ViewModel() {

    private val _favorite = MutableLiveData<FavoriteUiState>()
    val favorite : LiveData<FavoriteUiState> get() = _favorite

    init {
        getFav()
    }

    private fun getFav(){
        viewModelScope.launch {
            repository.getFavorites().collectLatest {
                when(it){
                    is NetworkResponseState.Success->{
                        _favorite.value = it.result?.let { it1 -> FavoriteUiState.Success(it1) }
                    }
                    is NetworkResponseState.Error->{
                        _favorite.value = FavoriteUiState.Error("error fav")
                    }
                    is NetworkResponseState.Loading->{
                        _favorite.value = FavoriteUiState.Loading
                    }
                }
            }
        }
    }

    fun deleteFav(product:FavoriteDTO){
        viewModelScope.launch {
            repository.deleteFav(product)
            getFav()
        }
    }


}