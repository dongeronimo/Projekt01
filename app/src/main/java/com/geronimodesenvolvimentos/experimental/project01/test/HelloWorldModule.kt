package com.geronimodesenvolvimentos.experimental.project01.test

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HelloWorldModule {
    @Provides
    @Singleton
    @Named("mock")
    fun bindHelloWorldMock():HelloWorld{
        return HelloWorldMockImpl();
    }
    @Provides
    @Singleton
    @Named("real")
    fun bindHelloWorldReal():HelloWorld{
        return HelloWorldRealImpl();
    }
}