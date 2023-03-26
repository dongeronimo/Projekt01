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
    @Named("mockSuccessSignUp")
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
    }

    @Provides
    @Named("mockFailureUsernameExistsSignUp")
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
    }

    @Provides
    @Named("mockFailureErrorSignUp")
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
    }
}