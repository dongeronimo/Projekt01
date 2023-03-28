package com.geronimodesenvolvimentos.experimental.project01.features.user.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

abstract class SignUpViewModel : ViewModel() {
    var usernameError by mutableStateOf("")
        protected set
    var emailError by mutableStateOf("")
        protected set
    var passwordError by mutableStateOf("")
        protected set
    var generalError by mutableStateOf("")
        protected set

    var username by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var allFieldsAreSet by mutableStateOf(false)
        private set
    fun updateUsername(value: String) {
        username = value.trim()
        allFieldsAreSet =  username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
    }
    fun updateEmail(value: String) {
        email = value.trim()
        allFieldsAreSet =  username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
    }
    fun updatePassword(value: String) {
        password = value.trim()
        allFieldsAreSet =  username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
    }
    abstract fun doSignUp()
}