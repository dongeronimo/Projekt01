package com.geronimodesenvolvimentos.experimental.project01.infra.cognitoPool.module

import com.amazonaws.regions.Regions
import com.geronimodesenvolvimentos.experimental.project01.infra.cognitoPool.CognitoPoolDataSource
import com.geronimodesenvolvimentos.experimental.project01.infra.cognitoPool.CognitoPoolDataSourceReal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CognitoPoolDataSourceModule {
    @Provides
    @Singleton
    @Named("mock")
    fun provideMock(): CognitoPoolDataSource = object: CognitoPoolDataSource {
            override fun getPoolID(): String = "GET YOUR POOL ID IN AWS CONSOLE"
            override fun getClientID(): String = "GET YOUR CLIENT ID IN AWS CONSOLE"
            override fun getClientSecret(): String = "GET YOUR SECRET IN AWS CONSOLE"
            override fun getAwsRegion(): Regions = Regions.SA_EAST_1
    }
    @Provides
    @Singleton
    @Named("real")
    fun provideReal(): CognitoPoolDataSource = CognitoPoolDataSourceReal()

}