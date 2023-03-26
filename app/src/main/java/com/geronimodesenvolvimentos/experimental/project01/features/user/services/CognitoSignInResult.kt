package com.geronimodesenvolvimentos.experimental.project01.features.user.services

enum class CognitoSignInState {
    SENT_VERIFICATION_CODE_TO_EMAIL,
    CONFIRMED,
    BAD_CONFIRMATION_CODE,
    CONFIRMATION_CODE_EXPIRED,
    USERNAME_ALREDY_EXISTS,
    UNKNOWN_ERROR}

data class CognitoSignInResult(val state:CognitoSignInState)

val CognitoSentVerificationCode = CognitoSignInResult(state = CognitoSignInState.SENT_VERIFICATION_CODE_TO_EMAIL)
val CognitoErrorUsernameAlredyExists = CognitoSignInResult(state = CognitoSignInState.USERNAME_ALREDY_EXISTS)
val CognitoUnknownError = CognitoSignInResult(state = CognitoSignInState.UNKNOWN_ERROR)
val CognitoEmailConfirmed = CognitoSignInResult(state = CognitoSignInState.CONFIRMED)
val CognitoBadConfirmationCode = CognitoSignInResult(state = CognitoSignInState.BAD_CONFIRMATION_CODE)
val CognitoCodeExpired = CognitoSignInResult(state= CognitoSignInState.CONFIRMATION_CODE_EXPIRED)