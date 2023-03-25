package com.geronimodesenvolvimentos.experimental.project01.test

import javax.inject.Inject

class HelloWorldRealImpl @Inject constructor() : HelloWorld {
    override fun foobar(): String {
        return "IM ALIVE"
    }
}