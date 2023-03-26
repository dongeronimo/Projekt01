package com.geronimodesenvolvimentos.experimental.project01.test

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.amazonaws.mobileconnectors.cognitoidentityprovider.*
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.amazonaws.regions.Regions
import kotlinx.coroutines.yield

//TODO: tranformar isso numa entidade que pode ser injetada
//TODO: injetar appContext
//TODO: criar uma fonte pros atributos e injetar essa fonte
class Cognito(private val appContext:Context, private val poolID:String,
              private val clientID:String, private val clientSecret:String,
              private val awsRegion:Regions) {
    private val userPool = CognitoUserPool(appContext, poolID, clientID, clientSecret, awsRegion)
    //TODO: Acabar c isso pq a classe n pode ter estados. Já q o signUp precisa de email botar como param da signup e criar o userAttributes lá
    private val userAttributes = CognitoUserAttributes()
    private val TAG = "GEGE"
    //TODO: Criar versoes mockadas pros casos de failure e sucesso.
    suspend fun signUpInBackground(userId:String, password: String) {
        var flag:Boolean = false;
        val signUpCallback = object: SignUpHandler {
            override fun onSuccess(
                cognitoUser: CognitoUser?,
                userConfirmed: Boolean,
                cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails?
            ) {
                //TODO: Tem que informar q é sucesso
                Log.d(TAG, "Sign-up success")
                flag = true
//                Toast.makeText(appContext, "Sign-up success", Toast.LENGTH_LONG).show()
                if(userConfirmed){
                    // This user must be confirmed and a confirmation code was sent to the user
                    // cognitoUserCodeDeliveryDetails will indicate where the confirmation code was sent
                    // Get the confirmation code from user
                }else{
//                    Toast.makeText(appContext, "Error: User Confirmed before", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(exception: Exception) {
                //TODO: tem que informar que deu erro.
                Toast.makeText(appContext,"Sign-up failed", Toast.LENGTH_LONG).show();
                flag = true
//                Log.d(TAG, "Sign-up failed: " + exception);
            }
        }
        userPool.signUpInBackground(userId, password, this.userAttributes,
            null, signUpCallback)
        //TODO: melhorar esse controle
        while(flag == false){
            yield()
        }
        //TODO: ver o que vai retornar
        Log.d(TAG, "fim da corotina, pode retornar o resultado")
    }

    fun confirmUser(userId: String, code:String){
        val cognitoUser = userPool.getUser(userId)
        cognitoUser.confirmSignUpInBackground(code, false, confirmationCallback)
    }
    private val signUpCallback = object: SignUpHandler {
        override fun onSuccess(
            cognitoUser: CognitoUser?,
            userConfirmed: Boolean,
            cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails?
        ) {
            Log.d(TAG, "Sign-up success")
            Toast.makeText(appContext, "Sign-up success", Toast.LENGTH_LONG).show()
            if(userConfirmed){
                // This user must be confirmed and a confirmation code was sent to the user
                // cognitoUserCodeDeliveryDetails will indicate where the confirmation code was sent
                // Get the confirmation code from user
            }else{
                Toast.makeText(appContext, "Error: User Confirmed before", Toast.LENGTH_LONG).show()
            }
        }
        override fun onFailure(exception: Exception) {
            Toast.makeText(appContext,"Sign-up failed", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Sign-up failed: " + exception);
        }
    }
    private val confirmationCallback = object: GenericHandler {
        override fun onSuccess() {
            // User was successfully confirmed
            Toast.makeText(appContext,"User Confirmed", Toast.LENGTH_LONG).show();
        }

        override fun onFailure(exception: java.lang.Exception?) {
            // User confirmation failed. Check exception for the cause.
        }
    }
    fun addAttribute(key:String, value:String){
        userAttributes.addAttribute(key,value)
    }
    fun userLogin(userId:String, password: String){
        val cognitoUser = userPool.getUser(userId)
        val authenticationHandler = object: AuthenticationHandler {
            override fun onSuccess(userSession: CognitoUserSession?, newDevice: CognitoDevice?) {
                Toast.makeText(appContext,"Sign in success", Toast.LENGTH_LONG).show();
            }

            override fun getAuthenticationDetails(
                authenticationContinuation: AuthenticationContinuation?,
                userId: String?
            ) {
                val authenticationDetails = AuthenticationDetails(userId, password, null)
                // Pass the user sign-in credentials to the continuation
                // Pass the user sign-in credentials to the continuation
                authenticationContinuation!!.setAuthenticationDetails(authenticationDetails)
                // Allow the sign-in to continue
                // Allow the sign-in to continue
                authenticationContinuation.continueTask()
            }

            override fun getMFACode(continuation: MultiFactorAuthenticationContinuation?) {
                TODO("Not yet implemented")
            }

            override fun authenticationChallenge(continuation: ChallengeContinuation?) {
                TODO("Not yet implemented")
            }

            override fun onFailure(exception: java.lang.Exception?) {
                Toast.makeText(appContext,"Sign in Failure", Toast.LENGTH_LONG).show();
            }

        }
        cognitoUser.getSessionInBackground(authenticationHandler)
    }
}