package com.geronimodesenvolvimentos.experimental.project01.features.user.services

import java.util.*

enum class CognitoState {
    SENT_VERIFICATION_CODE_TO_EMAIL,
    INVALID_PASSWORD,
    CONFIRMED,
    BAD_CONFIRMATION_CODE,
    CONFIRMATION_CODE_EXPIRED,
    USERNAME_ALREDY_EXISTS,
    UNKNOWN_ERROR,
    ACCESS_DENIED,
    PENDING_CONFIRMATION,
    SIGNED_IN}

data class Tokens(val accessToken:String, val accessTokenExpiration: Date,
                  val refreshToken:String, val idToken:String)
data class CognitoResponse(val state:CognitoState) {
    var tokens: Tokens? = null
    var username: String? = null
}


val CognitoSentVerificationCode = CognitoResponse(state = CognitoState.SENT_VERIFICATION_CODE_TO_EMAIL)
val CognitoErrorUsernameAlredyExists = CognitoResponse(state = CognitoState.USERNAME_ALREDY_EXISTS)
val CognitoUnknownError = CognitoResponse(state = CognitoState.UNKNOWN_ERROR)
val CognitoEmailConfirmed = CognitoResponse(state = CognitoState.CONFIRMED)
val CognitoBadConfirmationCode = CognitoResponse(state = CognitoState.BAD_CONFIRMATION_CODE)
val CognitoCodeExpired = CognitoResponse(state= CognitoState.CONFIRMATION_CODE_EXPIRED)
val CognitoInvalidPassword = CognitoResponse(state=CognitoState.INVALID_PASSWORD)
val CognitoAccessDenied = CognitoResponse(state=CognitoState.ACCESS_DENIED)
val CognitoPendingConfirmation = CognitoResponse(state=CognitoState.PENDING_CONFIRMATION)
fun CognitoLogInResult(name: String,
                       accessToken: String,
                       refreshToken: String,
                       idToken: String,
                       accessTokenExpiration: Date) =
    CognitoResponse(state=CognitoState.SIGNED_IN).apply {
        tokens = Tokens(accessToken, accessTokenExpiration, refreshToken, idToken)
        username = name
    }
