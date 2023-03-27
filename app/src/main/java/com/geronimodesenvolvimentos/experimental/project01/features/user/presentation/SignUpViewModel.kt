package com.geronimodesenvolvimentos.experimental.project01.features.user.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

abstract class SignUpViewModel : ViewModel() {
    var username by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    fun updateUsername(value: String) {
        username = value
    }
    fun updateEmail(value: String) {
        email = value
    }
    fun updatePassword(value: String) {
        password = value
    }

    abstract fun doSignUp()
}