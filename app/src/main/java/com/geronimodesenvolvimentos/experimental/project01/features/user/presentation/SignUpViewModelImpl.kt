package com.geronimodesenvolvimentos.experimental.project01.features.user.presentation


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geronimodesenvolvimentos.experimental.project01.features.user.services.CognitoInvalidParameter
import com.geronimodesenvolvimentos.experimental.project01.features.user.services.CognitoResponse
import com.geronimodesenvolvimentos.experimental.project01.features.user.services.CognitoService
import com.geronimodesenvolvimentos.experimental.project01.features.user.services.CognitoState
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
        clearErrors()
        viewModelScope.launch {
            val result = cognitoService.signUp(username, password, email)
            if(result.state == CognitoState.INVALID_PARAMETER){
                result.errorMessage?.let {
                    if (it.contains("Value at 'password'")){
                        passwordError = "Password must have at least 8 chars, contain at least 1 number, 1 special character, 1 uppercase letter and 1 lowercase letter"
                    }
                    else if(it.contains("Invalid email address format")){
                        emailError= "Invalid email"
                    }else{
                        generalError = it
                    }
                }
            }
            if(result.state == CognitoState.USERNAME_ALREDY_EXISTS) {
                usernameError = "Username alredy taken"
            }
            if(result.state == CognitoState.UNKNOWN_ERROR){
                generalError = "Something went wrong"
            }
            if(result.state == CognitoState.SENT_VERIFICATION_CODE_TO_EMAIL) {
                //TODO: Ir pra tela de por o cõdigo
            }
            Log.d("GEGE", "status=${result.state}; error = ${result.errorMessage?:{"no error"}}")
        }
    }
    private fun clearErrors() {
        usernameError = ""
        emailError = ""
        passwordError = ""
        generalError = ""
    }
}