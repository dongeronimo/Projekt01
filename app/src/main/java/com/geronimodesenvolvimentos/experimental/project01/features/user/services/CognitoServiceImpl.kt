package com.geronimodesenvolvimentos.experimental.project01.features.user.services

import android.content.Context
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.amazonaws.services.cognitoidentityprovider.model.UsernameExistsException
import com.geronimodesenvolvimentos.experimental.project01.infra.cognitoPool.CognitoPoolDataSource
import kotlinx.coroutines.yield

class CognitoServiceImpl(private val appContext:Context,
                         private val cognitoPoolDataSource: CognitoPoolDataSource) : CognitoService {
    override suspend fun signUp(
        userId: String,
        password: String,
        email: String
    ): CognitoSignInResult {
        val userPool = CognitoUserPool(appContext, cognitoPoolDataSource.getPoolID(),
            cognitoPoolDataSource.getClientID(), cognitoPoolDataSource.getClientSecret(),
            cognitoPoolDataSource.getAwsRegion())
        val userAttributes = CognitoUserAttributes()
        userAttributes.addAttribute("email", email)
        var coroutineDoneFlag = false //semaphore to allow the code to proceed after the signUp is done
        lateinit var result : CognitoSignInResult
        val signUpCallback = object: SignUpHandler {
            override fun onSuccess(
                cognitoUser: CognitoUser?,
                userConfirmed: Boolean,
                cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails?
            ) {
                result = CognitoSentVerificationCode;
                coroutineDoneFlag = true //allows the coroutine to proceed
            }
            override fun onFailure(exception: Exception) {
                result = if(exception is UsernameExistsException){
                    CognitoErrorUsernameAlredyExists;
                } else {
                    CognitoUnknownError;
                }
                coroutineDoneFlag = true//allows the coroutine to proceed
            }
        }
        userPool.signUpInBackground(userId, password, userAttributes,null, signUpCallback)
        while(!coroutineDoneFlag){//blocks coroutine progress until the flag is true.
            yield() //yields to other coroutines to run, i'm waiting for a result here
        }
        return result
    }
}