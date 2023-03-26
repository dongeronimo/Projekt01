package com.geronimodesenvolvimentos.experimental.project01.features.user.services

interface CognitoService {
    suspend fun signUp(userId:String, password:String, email:String): CognitoSignInResult
    suspend fun confirmUser(userId: String, code:String): CognitoSignInResult

    suspend fun login(userId: String, password: String)
}