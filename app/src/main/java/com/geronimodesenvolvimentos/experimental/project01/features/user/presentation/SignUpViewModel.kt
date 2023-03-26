package com.geronimodesenvolvimentos.experimental.project01.features.user.presentation


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(): ViewModel(), LifecycleObserver {
    fun updateUsername(value: String) {
        username = value
    }
    fun updateEmail(value: String) {
        email = value
    }
    fun updatePassword(value: String) {
        password = value
    }

    var username by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
}