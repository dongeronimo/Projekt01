package com.geronimodesenvolvimentos.experimental.project01

import com.geronimodesenvolvimentos.experimental.project01.test.HelloWorld
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class HelloWorldTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @Inject
    @Named("real")
    lateinit var helloReal: HelloWorld

    @Inject
    @Named("mock")
    lateinit var helloMock: HelloWorld

    @Before
    fun init(){
        hiltRule.inject()
    }

    @Test
    fun mockIsOk(){
        assertTrue("IM MOCK" == helloMock.foobar())
    }
    @Test
    fun realIsOk(){
        assertTrue("IM ALIVE"==helloReal.foobar())
    }
}