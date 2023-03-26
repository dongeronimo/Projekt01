package com.geronimodesenvolvimentos.experimental.project01.features.user.services

import android.content.Context
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.amazonaws.services.cognitoidentityprovider.model.CodeMismatchException
import com.amazonaws.services.cognitoidentityprovider.model.ExpiredCodeException
import com.amazonaws.services.cognitoidentityprovider.model.UsernameExistsException
import com.geronimodesenvolvimentos.experimental.project01.infra.cognitoPool.CognitoPoolDataSource
import kotlinx.coroutines.yield

class CognitoServiceImpl(private val appContext:Context,
                         private val cognitoPoolDataSource: CognitoPoolDataSource) : CognitoService {
    private val userPool = CognitoUserPool(appContext, cognitoPoolDataSource.getPoolID(),
        cognitoPoolDataSource.getClientID(), cognitoPoolDataSource.getClientSecret(),
        cognitoPoolDataSource.getAwsRegion())
    override suspend fun signUp(
        userId: String,
        password: String,
        email: String
    ): CognitoSignInResult {
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

    override suspend fun confirmUser(userId: String, code: String): CognitoSignInResult {
        var isDone = false
        val cognitoUser = userPool.getUser(userId);
        lateinit var result: CognitoSignInResult
        cognitoUser.confirmSignUpInBackground(code, false, object: GenericHandler {
            override fun onSuccess() {
                result = CognitoEmailConfirmed
                isDone = true
            }

            override fun onFailure(exception: java.lang.Exception?) {
                if(exception is ExpiredCodeException){
                    result = CognitoCodeExpired
                }
                else if(exception is CodeMismatchException){
                    result = CognitoBadConfirmationCode
                }
                else {
                    result = CognitoUnknownError
                }
                isDone = true
            }
        })
        while(!isDone){
            yield()
        }
        return result
    }
}