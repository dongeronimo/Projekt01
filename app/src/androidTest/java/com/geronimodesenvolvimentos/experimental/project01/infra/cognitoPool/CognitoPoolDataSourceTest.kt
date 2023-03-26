package com.geronimodesenvolvimentos.experimental.project01.infra.cognitoPool

import com.amazonaws.regions.Regions
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class CognitoPoolDataSourceTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("mock")
    lateinit var poolDataSource: CognitoPoolDataSource

    @Before
    fun init(){
        hiltRule.inject()
    }

    @Test
    fun canInject(){
        assertNotNull(poolDataSource)
    }

    @Test
    fun mockDataIsAsExpected(){
        assertEquals(poolDataSource.getPoolID(), "GET YOUR POOL ID IN AWS CONSOLE")
        assertEquals(poolDataSource.getAwsRegion(), Regions.SA_EAST_1)
        assertEquals(poolDataSource.getClientID(), "GET YOUR CLIENT ID IN AWS CONSOLE")
        assertEquals(poolDataSource.getClientSecret(), "GET YOUR SECRET IN AWS CONSOLE")
    }
}