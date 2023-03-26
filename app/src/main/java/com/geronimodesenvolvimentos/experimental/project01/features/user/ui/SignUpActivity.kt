package com.geronimodesenvolvimentos.experimental.project01.features.user.ui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

import com.geronimodesenvolvimentos.experimental.project01.features.user.presentation.SignUpViewModel
import com.geronimodesenvolvimentos.experimental.project01.features.user.ui.ui.theme.Project01Theme
import com.geronimodesenvolvimentos.experimental.project01.features.user.ui.ui.theme.Purple200
import com.geronimodesenvolvimentos.experimental.project01.features.user.ui.ui.theme.Purple500
import com.geronimodesenvolvimentos.experimental.project01.features.user.ui.ui.theme.Purple700
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : ComponentActivity() {
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Project01Theme {
                Root()
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun Root(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .background(Purple200)){
            val (title, signUpForm, bottomButton) = createRefs()
            Title(Modifier.constrainAs(title){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
            SignUpForm(modifier = Modifier.constrainAs(signUpForm) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottomButton.top)
                height= Dimension.fillToConstraints
            })
            FixedButton(modifier=Modifier.constrainAs(bottomButton){
                top.linkTo(signUpForm.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        }
    }
}

@Composable
fun Title(modifier:Modifier){
    Column(
        modifier
            .background(Purple500)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 24.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Welcome!", fontSize = 30.sp)
    }

}

@Composable
fun SignUpForm(modifier: Modifier){
    Column(modifier.fillMaxWidth().background(Color.Yellow),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Please provide the necessary data to create your user account")
        SimpleOutlinedTextFieldSample(label="User Name") //will be name
        SimpleOutlinedTextFieldSample(label="Email") //will be email
        SimpleOutlinedTextFieldSample(label="Password") //will be password
    }
}
@Composable
fun SimpleOutlinedTextFieldSample(label:String) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label) },
        maxLines = 1,
        textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(20.dp)
    )
}
@Composable
fun FixedButton(modifier:Modifier){
    Column(modifier = modifier.fillMaxWidth().background(Color.Red),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Button(onClick = {}, colors=ButtonDefaults
            .buttonColors(backgroundColor = Purple700)){
            Text("Sign In")
        }
    }
}