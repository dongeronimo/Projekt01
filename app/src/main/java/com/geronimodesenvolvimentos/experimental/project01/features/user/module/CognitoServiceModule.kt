package com.geronimodesenvolvimentos.experimental.project01.features.user.module

import android.content.Context
import com.geronimodesenvolvimentos.experimental.project01.features.user.services.CognitoService
import com.geronimodesenvolvimentos.experimental.project01.features.user.services.CognitoServiceImpl
import com.geronimodesenvolvimentos.experimental.project01.infra.cognitoPool.CognitoPoolDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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

}