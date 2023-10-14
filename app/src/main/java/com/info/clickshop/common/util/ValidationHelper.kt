package com.info.clickshop.common.util

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class ValidationHelper(private val context: Context) {

    private fun isEmailValid(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun isEmailEmpty(email: String) : Boolean{
        return email.isNotEmpty()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }

    private fun isPasswordLength(password: String): Boolean {
        return password.length>=6
    }

    private fun isPasswordAgainValid(passwordAgain: String): Boolean {
        return passwordAgain.isNotEmpty()
    }

    fun validateData(
        emailET: EditText,
        passwordET: EditText,
        emailTIL: TextInputLayout,
        passTIL: TextInputLayout
    ): Boolean {
        val email = emailET.text.toString().trim()
        val password = passwordET.text.toString().trim()

        if (!isEmailEmpty(email)){
            with(emailTIL){
                isErrorEnabled = true
                error = "Email cannot be empty"
            }
            return false
        } else {
            emailTIL.isErrorEnabled = false
        }

        if (!isEmailValid(email)) {
            with(emailTIL){
                isErrorEnabled = true
                error = "Invalid email format"
            }
            return false
        } else {
            emailTIL.isErrorEnabled = false
        }

        if (!isPasswordValid(password)) {
            with(passTIL){
                isErrorEnabled = true
                error = "Password cannot be empty"
            }
            return false
        } else {
            passTIL.isErrorEnabled = false
        }

        if (!isPasswordLength(password)) {
            with(passTIL){
                isErrorEnabled = true
                error = "Password must be 6 characters"
            }
            return false
        } else {
            passTIL.isErrorEnabled = false
        }

        return true
    }

    fun validateRegisterData(
        passwordET:EditText,
        passwordAgainET:EditText,
        passwordAgainTIL:TextInputLayout
    ):Boolean{
        val password = passwordET.text.toString().trim()
        val passwordAgain = passwordAgainET.text.toString().trim()

        if (!isPasswordAgainValid(passwordAgain)){
            with(passwordAgainTIL){
                isErrorEnabled = true
                error = "Confirm password can not be empty"
            }
            return false
        } else {
            passwordAgainTIL.isErrorEnabled = false
        }

        if (password != passwordAgain){
            with(passwordAgainTIL){
                isErrorEnabled = true
                error = "Password does not match"
            }
            return false
        } else {
            passwordAgainTIL.isErrorEnabled = false
        }

        return true
    }

}