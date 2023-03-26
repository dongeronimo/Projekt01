package com.geronimodesenvolvimentos.experimental.project01.features.user.services

interface CognitoService {
    suspend fun signUp(userId:String, password:String, email:String): CognitoResponse
    suspend fun confirmUser(userId: String, code:String): CognitoResponse

    suspend fun login(userId: String, password: String): CognitoResponse
}