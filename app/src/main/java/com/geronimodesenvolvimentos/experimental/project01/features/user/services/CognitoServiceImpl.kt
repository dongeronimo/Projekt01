package com.geronimodesenvolvimentos.experimental.project01.features.user.services

import android.content.Context
import com.geronimodesenvolvimentos.experimental.project01.infra.cognitoPool.CognitoPoolDataSource

class CognitoServiceImpl(private val appContext:Context, private val cognitoPoolDataSource: CognitoPoolDataSource) : CognitoService {
}