package com.geronimodesenvolvimentos.experimental.project01.features.user.services

enum class CognitoSignInState {
    SENT_VERIFICATION_CODE_TO_EMAIL,
    USERNAME_ALREDY_EXISTS,
    UNKNOWN_ERROR}

data class CognitoSignInResult(public val state:CognitoSignInState)

val CognitoSentVerificationCode = CognitoSignInResult(state = CognitoSignInState.SENT_VERIFICATION_CODE_TO_EMAIL)
val CognitoErrorUsernameAlredyExists = CognitoSignInResult(state = CognitoSignInState.USERNAME_ALREDY_EXISTS)
val CognitoUnknownError = CognitoSignInResult(state = CognitoSignInState.UNKNOWN_ERROR)