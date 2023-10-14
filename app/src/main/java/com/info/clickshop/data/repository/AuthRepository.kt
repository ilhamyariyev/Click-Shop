package com.info.clickshop.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.info.clickshop.common.util.NetworkResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
) {

    suspend fun login(email: String, password: String, ): Flow<NetworkResponseState<AuthResult>> =
        flow {
            emit(NetworkResponseState.Loading)
            val user = auth.signInWithEmailAndPassword(email, password).await()
            emit(NetworkResponseState.Success(user))
        }.catch {
            emit(NetworkResponseState.Error(it as Exception))
        }


    fun register(email: String, password: String): Flow<NetworkResponseState<AuthResult>> =
        flow {
            emit(NetworkResponseState.Loading)
            val user = auth.createUserWithEmailAndPassword(email, password).await()
            emit(NetworkResponseState.Success(user))
        }.catch {
            emit(NetworkResponseState.Error(it as Exception))
        }


    fun resetPassword(email: String): Flow<NetworkResponseState<Void>> =
        flow {
            emit(NetworkResponseState.Loading)
            val email = auth.sendPasswordResetEmail(email).await()
            emit(NetworkResponseState.Success(email))
        }.catch {
            emit(NetworkResponseState.Error(it as Exception))
        }


    fun logOut(): Flow<NetworkResponseState<Boolean>> =
        flow {
            emit(NetworkResponseState.Loading)
            auth.signOut()
            emit(NetworkResponseState.Success(true))
        }.catch {
            emit(NetworkResponseState.Error(it as Exception))
        }


}