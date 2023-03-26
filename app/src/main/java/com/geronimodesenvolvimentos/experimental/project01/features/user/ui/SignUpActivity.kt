package com.geronimodesenvolvimentos.experimental.project01.features.user.ui


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel

import com.geronimodesenvolvimentos.experimental.project01.features.user.presentation.SignUpViewModel
import com.geronimodesenvolvimentos.experimental.project01.features.user.ui.ui.theme.Project01Theme
import com.geronimodesenvolvimentos.experimental.project01.features.user.ui.ui.theme.Purple200
import com.geronimodesenvolvimentos.experimental.project01.features.user.ui.ui.theme.Purple500
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Project01Theme {
                Root()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Root(){
        val viewModel:SignUpViewModel = hiltViewModel()
        fun onSignUpClick(){
            Log.d("GEGE", "TO-DO, validações")
            Log.d("GEGE", "TO-DO, enviar")
            Log.d("GEGE", "TO-DO, ir pra tela de por o código")
            Log.d("GEGE", "TO-DO, ir pra tela de erro")
        }
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
                SignUpForm(viewModel=viewModel,modifier = Modifier.constrainAs(signUpForm) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomButton.top)
                    height= Dimension.fillToConstraints
                })
                SignUpBottomButton(onSignUpClick={onSignUpClick()}, modifier=Modifier.constrainAs(bottomButton){
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
    fun SignUpForm(modifier: Modifier, viewModel: SignUpViewModel){
        Column(modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Please provide the necessary data to create your user account")
            SimpleUsername(label="User Name", viewModel = viewModel) //will be name
            SimpleEmail(label="Email", viewModel = viewModel) //will be email
            SimplePassword(label="Password", viewModel = viewModel) //will be password
        }
    }
    @Composable
    fun SimpleUsername(label:String, viewModel: SignUpViewModel) {
        TextField(
            value = viewModel.username,
            onValueChange = { value -> viewModel.updateUsername(value) },
            label = { Text(label) },
            maxLines = 1,
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
    }

    @Composable
    fun SimpleEmail(label:String, viewModel: SignUpViewModel) {
        TextField(
            value = viewModel.email,
            onValueChange = { value -> viewModel.updateEmail(value) },
            label = { Text(label) },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next),
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp)
        )
    }

    @Composable
    fun SimplePassword(label:String, viewModel: SignUpViewModel) {
        var text by remember { mutableStateOf("") }

        TextField(
            value = viewModel.password,
            onValueChange = { value->viewModel.updatePassword(value) },
            label = { Text(label) },
            maxLines = 1,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Send),
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp)
        )
    }
    @Composable
    fun SignUpBottomButton(modifier:Modifier, onSignUpClick:()->Unit={}){
        Column(modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Button(onClick = onSignUpClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Purple500)
            ){
                Text("Sign Up")
            }
        }
    }
}