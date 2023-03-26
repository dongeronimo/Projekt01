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
import com.geronimodesenvolvimentos.experimental.project01.test.ui.theme.Project01Theme
import kotlinx.coroutines.launch

class CognitoTestActivity : ComponentActivity() {
    private lateinit var cognito: Cognito
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
        cognito = Cognito(appContext = applicationContext, poolID = "projekt01-teste-user-pool",
        clientID = "2i0dcc3jkh3vh3jnd0511g28vq", clientSecret = "16eu2392s7egk118svec7honbnemal185vh1jb7dgodt9t6ca18v",
        awsRegion = Regions.SA_EAST_1)
        cognito.addAttribute("email", "luciano.geronimo.fnord@gmail.com")
        lifecycleScope.launch {
            Log.d("GEGE", "comecou a corotina")
            cognito.signUpInBackground("CHARLIE", "Babilonia#1")
            Log.d("GEGE", "esperou a corotina")
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