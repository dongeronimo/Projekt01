package com.geronimodesenvolvimentos.experimental.project01.features.user.services

import android.content.Context
import android.util.Log
import com.amazonaws.mobileconnectors.cognitoidentityprovider.*
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.util.CognitoServiceConstants.CHLG_TYPE_USER_PASSWORD
import com.amazonaws.services.cognitoidentityprovider.model.CodeMismatchException
import com.amazonaws.services.cognitoidentityprovider.model.ExpiredCodeException
import com.amazonaws.services.cognitoidentityprovider.model.InvalidPasswordException
import com.amazonaws.services.cognitoidentityprovider.model.NotAuthorizedException
import com.amazonaws.services.cognitoidentityprovider.model.UserNotConfirmedException
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
    ): CognitoResponse {
        val userAttributes = CognitoUserAttributes()
        userAttributes.addAttribute("email", email)
        var coroutineDoneFlag = false //semaphore to allow the code to proceed after the signUp is done
        lateinit var result : CognitoResponse
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
                if(exception is InvalidPasswordException){
                    result = CognitoInvalidPassword
                }
                else if(exception is UsernameExistsException){
                    CognitoErrorUsernameAlredyExists;
                }
                else {
                    result = CognitoUnknownError;
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

    override suspend fun confirmUser(userId: String, code: String): CognitoResponse {
        var isDone = false
        val cognitoUser = userPool.getUser(userId);
        lateinit var result: CognitoResponse
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

    override suspend fun login(userId: String, password: String): CognitoResponse {
        val cognitoUser = userPool.getUser(userId)
        lateinit var result:CognitoResponse
        var isDone = false
        val authHandler = object: AuthenticationHandler {
            override fun onSuccess(userSession: CognitoUserSession, newDevice: CognitoDevice?) {
                result = CognitoLogInResult(accessToken = userSession.accessToken.jwtToken,
                    name = userSession.username,
                    refreshToken = userSession.refreshToken.token,
                    idToken = userSession.idToken.jwtToken,
                    accessTokenExpiration = userSession.accessToken.expiration)
                isDone = true;
            }

            override fun getAuthenticationDetails(
                authenticationContinuation: AuthenticationContinuation,
                userId: String?
            ) {
                val authenticationDetails = AuthenticationDetails(userId, password,
                    null)
                Log.d("GEGE", "auth type = ${authenticationDetails.authenticationType}")
                authenticationDetails.authenticationType = CHLG_TYPE_USER_PASSWORD
                authenticationContinuation.setAuthenticationDetails(authenticationDetails)
                authenticationContinuation.continueTask()
            }

            override fun getMFACode(continuation: MultiFactorAuthenticationContinuation?) {
                TODO("Not yet implemented")
            }

            override fun authenticationChallenge(continuation: ChallengeContinuation?) {
                TODO("Not yet implemented")
            }

            override fun onFailure(exception: java.lang.Exception) {
                if(exception is NotAuthorizedException){
                    result = CognitoAccessDenied
                }
                if(exception is UserNotConfirmedException){
                    result = CognitoPendingConfirmation
                }
                else{
                    result = CognitoUnknownError
                }
                isDone = true
            }

        }
        cognitoUser.getSessionInBackground(authHandler)
        while(!isDone){
            yield()
        }
        return result
    }
}