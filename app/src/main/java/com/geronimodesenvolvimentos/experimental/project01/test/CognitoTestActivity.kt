package com.geronimodesenvolvimentos.experimental.project01.test

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.amazonaws.regions.Regions
import com.geronimodesenvolvimentos.experimental.project01.infra.cognitoPool.CognitoPoolDataSource
import com.geronimodesenvolvimentos.experimental.project01.test.ui.theme.Project01Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class CognitoTestActivity : ComponentActivity() {
    private lateinit var cognito: Cognito
    @Inject
    @Named("real")
    lateinit var cognitoPoolConfigs: CognitoPoolDataSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Project01Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting2("Android")
                }
            }
        }
        cognito = Cognito(appContext = applicationContext, poolID = cognitoPoolConfigs.getPoolID(),
            clientID = cognitoPoolConfigs.getClientID(), clientSecret = cognitoPoolConfigs.getClientSecret(),
            awsRegion = cognitoPoolConfigs.getAwsRegion())
        cognito.addAttribute("email", "luciano.geronimo.fnord@gmail.com")
        lifecycleScope.launch {
            val signInResult = cognito.signUpInBackground("ABLE", "Babilonia#1")
            Log.d("GEGE", "signInResult = $signInResult")
        }

    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    Project01Theme {
        Greeting2("Android")
    }
}