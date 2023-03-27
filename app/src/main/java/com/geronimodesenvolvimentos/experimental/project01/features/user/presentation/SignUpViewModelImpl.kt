package com.geronimodesenvolvimentos.experimental.project01.features.user.presentation


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geronimodesenvolvimentos.experimental.project01.features.user.services.CognitoService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SignUpViewModelImpl @Inject constructor(
    @Named("real")
    private val cognitoService:CognitoService
): SignUpViewModel(), LifecycleObserver {


    override fun doSignUp(){
        viewModelScope.launch {
            cognitoService.signUp(username, password, email)
        }
    }

}