package com.geronimodesenvolvimentos.experimental.project01.features.user.module

import android.content.Context
import com.geronimodesenvolvimentos.experimental.project01.features.user.services.*
import com.geronimodesenvolvimentos.experimental.project01.infra.cognitoPool.CognitoPoolDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object CognitoServiceModule {
    @Provides
    @Named("real")
    fun provideReal(@ApplicationContext appContext: Context,
        @Named("real") cognitoPoolData: CognitoPoolDataSource)
    :CognitoService{
        return CognitoServiceImpl(appContext, cognitoPoolData)
    }
    @Provides
    @Named("mockHappyPath")
    fun provideMockSignUpSuccess(@ApplicationContext appContext: Context,
                                 @Named("mock") cognitoPoolData: CognitoPoolDataSource)
        : CognitoService = object : CognitoService {
        override suspend fun signUp(
            userId: String,
            password: String,
            email: String
        ): CognitoSignInResult {
            delay(timeMillis = 100L)
            return CognitoSentVerificationCode;
        }

        override suspend fun confirmUser(userId: String, code: String): CognitoSignInResult {
            delay(timeMillis = 100L)
            return CognitoEmailConfirmed
        }
    }
    @Provides
    @Named("mockFailureBadConfirmationCode")
    fun provideBadConfirmationCode(@ApplicationContext appContext: Context,
                                   @Named("mock") cognitoPoolData: CognitoPoolDataSource) :
            CognitoService = object :CognitoService {
        override suspend fun signUp(
            userId: String,
            password: String,
            email: String
        ): CognitoSignInResult {
            delay(timeMillis = 100L)
            return CognitoSentVerificationCode;
        }

        override suspend fun confirmUser(userId: String, code: String): CognitoSignInResult {
            delay(timeMillis = 100L)
            return CognitoBadConfirmationCode;
        }
    }

    @Provides
    @Named("mockFailureCodeExpired")
    fun provideCodeExpired(@ApplicationContext appContext: Context,
                           @Named("mock") cognitoPoolData: CognitoPoolDataSource) :
            CognitoService = object :CognitoService {
        override suspend fun signUp(
            userId: String,
            password: String,
            email: String
        ): CognitoSignInResult {
            delay(timeMillis = 100L)
            return CognitoSentVerificationCode;
        }

        override suspend fun confirmUser(userId: String, code: String): CognitoSignInResult {
            delay(timeMillis = 100L)
            return CognitoCodeExpired;
        }
    }

    @Provides
    @Named("mockFailureUsernameExists")
    fun provideMockSignUpFailureUsernameExists(@ApplicationContext appContext: Context,
                                 @Named("mock") cognitoPoolData: CognitoPoolDataSource)
            : CognitoService = object : CognitoService {
        override suspend fun signUp(
            userId: String,
            password: String,
            email: String
        ): CognitoSignInResult {
            delay(timeMillis = 100L)
            return CognitoErrorUsernameAlredyExists;
        }

        override suspend fun confirmUser(userId: String, code: String): CognitoSignInResult {
            TODO("Not yet implemented")
        }
    }

    @Provides
    @Named("mockFailureError")
    fun provideMockSignUpFailureError(@ApplicationContext appContext: Context,
                                      @Named("mock") cognitoPoolData: CognitoPoolDataSource)
            : CognitoService = object : CognitoService {
        override suspend fun signUp(
            userId: String,
            password: String,
            email: String
        ): CognitoSignInResult {
            delay(timeMillis = 100L)
            return CognitoUnknownError;
        }

        override suspend fun confirmUser(userId: String, code: String): CognitoSignInResult {
            delay(timeMillis = 100L)
            return CognitoUnknownError;
        }
    }
}