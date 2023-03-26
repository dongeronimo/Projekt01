package com.geronimodesenvolvimentos.experimental.project01.features.user.module

import com.geronimodesenvolvimentos.experimental.project01.features.user.services.CognitoService
import com.geronimodesenvolvimentos.experimental.project01.features.user.services.CognitoServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object CognitoServiceModule {
    @Provides
    @Named("real")
    fun provideReal():CognitoService{
        return CognitoServiceImpl()
    }

}