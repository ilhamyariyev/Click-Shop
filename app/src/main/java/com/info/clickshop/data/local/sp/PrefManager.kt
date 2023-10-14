package com.info.clickshop.data.local.sp

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PrefManager @Inject constructor(@ApplicationContext val context:Context) {

    private val masterKey:MasterKey by lazy {
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
    }

    private val sharedPreferences : SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            "user_info",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveName(name:String){
        sharedPreferences.edit().putString("name",name).apply()
    }

    fun saveEmail(email:String) {
        sharedPreferences.edit().putString("email",email).apply()
    }

    fun savePassword(password:String) {
        sharedPreferences.edit().putString("password",password).apply()
    }


}