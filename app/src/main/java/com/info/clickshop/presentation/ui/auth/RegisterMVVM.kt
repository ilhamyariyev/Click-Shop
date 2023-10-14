package com.info.clickshop.presentation.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.info.clickshop.common.util.NetworkResponseState
import com.info.clickshop.data.local.sp.PrefManager
import com.info.clickshop.data.repository.AuthRepository
import com.info.clickshop.domain.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterMVVM @Inject constructor(
    private val sp: PrefManager,
    private val authRepository:AuthRepository
) :ViewModel(){

    private val _authResult = MutableLiveData<AuthUiState>()
    val authResult: LiveData<AuthUiState> get() = _authResult

    fun createAccountWithEmailAndPassword(email: String, password: String,name: String) {
        viewModelScope.launch {
            authRepository.register(email, password).collectLatest {
                when (it) {
                    is NetworkResponseState.Loading -> {
                        _authResult.value = AuthUiState.Loading
                    }
                    is NetworkResponseState.Success -> {
                        _authResult.value = AuthUiState.Success
                        sp.saveName(name)
                        sp.saveEmail(email)
                        sp.savePassword(password)
                    }
                    is NetworkResponseState.Error -> {
                        _authResult.value = AuthUiState.Error(it.exception.message.toString())
                    }
                }
            }
        }
    }

    fun saveEmail(email: String) {
        sp.saveEmail(email)
    }

    fun savePassword(password: String) {
        sp.savePassword(password)
    }

    fun saveName(name:String){
        sp.saveName(name)
    }

}







