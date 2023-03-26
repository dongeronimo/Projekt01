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
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import javax.inject.Named

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
        ): CognitoResponse {
            delay(timeMillis = 100L)
            return CognitoSentVerificationCode;
        }

        override suspend fun confirmUser(userId: String, code: String): CognitoResponse {
            delay(timeMillis = 100L)
            return CognitoEmailConfirmed
        }

        override suspend fun login(userId: String, password: String): CognitoResponse {
            delay(timeMillis = 100L)

            return CognitoLogInResult(name = "foobar", accessToken = "babababa",
                refreshToken = "hdkehjrwj", idToken = "3i24u3i", accessTokenExpiration = Date.from(Instant.now()))
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
        ): CognitoResponse {
            delay(timeMillis = 100L)
            return CognitoSentVerificationCode;
        }

        override suspend fun confirmUser(userId: String, code: String): CognitoResponse {
            delay(timeMillis = 100L)
            return CognitoBadConfirmationCode;
        }

        override suspend fun login(userId: String, password: String) : CognitoResponse{
            TODO("Not yet implemented")
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
        ): CognitoResponse {
            delay(timeMillis = 100L)
            return CognitoSentVerificationCode;
        }

        override suspend fun confirmUser(userId: String, code: String): CognitoResponse {
            delay(timeMillis = 100L)
            return CognitoCodeExpired;
        }

        override suspend fun login(userId: String, password: String): CognitoResponse {
            TODO("Not yet implemented")
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
        ): CognitoResponse {
            delay(timeMillis = 100L)
            return CognitoErrorUsernameAlredyExists;
        }

        override suspend fun confirmUser(userId: String, code: String): CognitoResponse {
            TODO("Not yet implemented")
        }

        override suspend fun login(userId: String, password: String) : CognitoResponse{
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
        ): CognitoResponse {
            delay(timeMillis = 100L)
            return CognitoUnknownError;
        }

        override suspend fun confirmUser(userId: String, code: String): CognitoResponse {
            delay(timeMillis = 100L)
            return CognitoUnknownError;
        }

        override suspend fun login(userId: String, password: String): CognitoResponse {
            TODO("Not yet implemented")
        }
    }
}