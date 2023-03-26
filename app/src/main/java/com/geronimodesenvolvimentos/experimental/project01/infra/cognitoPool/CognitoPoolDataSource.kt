package com.geronimodesenvolvimentos.experimental.project01.infra.cognitoPool

import com.amazonaws.regions.Regions
/***
 * Remember that the version with real data is in gitignore and won't be pushed to git, so ask for
 * the credentials or rewrite them.
 */

interface CognitoPoolDataSource {
    fun getPoolID():String
    fun getClientID():String
    fun getClientSecret():String
    fun getAwsRegion():Regions
}