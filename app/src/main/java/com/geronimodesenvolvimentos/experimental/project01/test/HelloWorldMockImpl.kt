package com.geronimodesenvolvimentos.experimental.project01.test

import javax.inject.Inject

class HelloWorldMockImpl @Inject constructor(): HelloWorld {
    override fun foobar(): String {
        return "IM MOCK"
    }
}