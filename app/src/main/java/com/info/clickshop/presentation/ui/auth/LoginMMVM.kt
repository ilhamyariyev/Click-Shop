package com.info.clickshop.presentation.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.info.clickshop.common.util.NetworkResponseState
import com.info.clickshop.data.repository.AuthRepository
import com.info.clickshop.domain.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginMMVM @Inject constructor(
    private val authRepository: AuthRepository
) :ViewModel(){
    private val _authResult = MutableLiveData<AuthUiState>()
    val authResult: LiveData<AuthUiState> get() = _authResult

    private val _resetPassword = MutableLiveData<AuthUiState>()
    val resetPassword: LiveData<AuthUiState> get() = _resetPassword

    fun loginUser(email: String, password:String) {
        viewModelScope.launch{
            authRepository.login(email,password).collectLatest{
                when (it) {
                    is NetworkResponseState.Loading -> {
                        _authResult.value = AuthUiState.Loading
                    }
                    is NetworkResponseState.Success -> {
                        _authResult.value = AuthUiState.Success
                    }
                    is NetworkResponseState.Error -> {
                        _authResult.value = AuthUiState.Error(it.exception.message.toString())
                    }
                }

            }
        }
    }

    fun resetPassword(email:String){
        viewModelScope.launch {
            authRepository.resetPassword(email).collectLatest {
                when (it) {
                    is NetworkResponseState.Loading -> {
                        _resetPassword.value = AuthUiState.Loading
                    }
                    is NetworkResponseState.Success -> {
                        _resetPassword.value = AuthUiState.Success
                    }
                    is NetworkResponseState.Error -> {
                        _resetPassword.value = AuthUiState.Error(it.exception.message.toString())
                    }
                }
            }
        }
    }

}