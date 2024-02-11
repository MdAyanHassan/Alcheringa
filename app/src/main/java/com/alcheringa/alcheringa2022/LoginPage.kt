package com.alcheringa.alcheringa2022

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alcheringa.alcheringa2022.ui.theme.aileron
import com.alcheringa.alcheringa2022.ui.theme.borderdarkpurple
import com.alcheringa.alcheringa2022.ui.theme.containerPurple
import com.alcheringa.alcheringa2022.ui.theme.creamWhite
import com.alcheringa.alcheringa2022.ui.theme.darkBar
import com.alcheringa.alcheringa2022.ui.theme.darkGrey
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.alcheringa.alcheringa2022.ui.theme.grey
import com.alcheringa.alcheringa2022.ui.theme.heartRed
import com.alcheringa.alcheringa2022.ui.theme.lightBar
import com.alcheringa.alcheringa2022.ui.theme.lighterPurple
import com.alcheringa.alcheringa2022.ui.theme.white
import java.util.regex.Pattern

@Composable
fun LoginScreen(forgetPassword: ()->Unit , signUpHere:() -> Unit ,  microsoftLogin: () -> Unit , googleLogin: () -> Unit , login: (String? , String?) -> Unit , onEmailChange: (String) -> Unit , onPasswordChange: (String) -> Unit) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val isPasswordVisible = remember { mutableStateOf(false) }
    val isProgressVisible = remember { mutableStateOf(false) }
    val emailErrorState = remember { mutableStateOf<String?>(null) }
    val passwordErrorState = remember { mutableStateOf<String?>(null) }

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    if(isSystemInDarkTheme()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
//                .background(MaterialTheme.colors.onBackground)
                //.padding(16.dp)
                .paint(
                    painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
                    contentScale = ContentScale.Crop
                ).
                    pointerInput(Unit) {
                                       detectTapGestures (
                                           onPress = {keyboardController?.hide()}
                                       )
                    }
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_union),
//            contentDescription = "Alcheringa Logo",
//            modifier = Modifier.size(101.dp)
//        )
            Text(
                text = "Login",
                color = lighterPurple,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            text = "If you already have an account",
//            color = Color.Black,
//            fontSize = 14.sp,
//            modifier = Modifier.padding(top = 8.dp)
//        )
            Box(modifier = Modifier
                .padding(bottom = 12.dp)
                .height(75.dp)) {
                TextField(
                    value = emailState.value,
                    onValueChange = {
                        emailState.value = it
                        onEmailChange(it)
                        emailErrorState.value = null
                    },
                    isError = emailErrorState.value != null,
                    label =   {Text("Email" , color = grey)} ,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .border(1.dp, creamWhite, shape = RoundedCornerShape(2.dp))
                        .fillMaxWidth()
                        .focusRequester(emailFocusRequester)
                        .align(Alignment.TopCenter),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    textStyle = TextStyle(color = creamWhite)
                )
                emailErrorState.value?.let { error ->
                    Text(text = error, color = heartRed , modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 20.dp, top = 5.dp))
                }
            }
            val passwordVisualTransformation =
                if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
            Box(modifier = Modifier
                .padding(bottom = 16.dp)
                .height(75.dp)) {
                TextField(
                    value = passwordState.value,
                    onValueChange = {
                        passwordState.value = it
                        onPasswordChange(it)
                        passwordErrorState.value = null
                    },
                    isError = passwordErrorState.value != null,
                    label = { Text("Password", color = grey)  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, start = 16.dp)
                        .border(1.dp, creamWhite, shape = RoundedCornerShape(2.dp))
                        .focusRequester(passwordFocusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = passwordVisualTransformation,
                    textStyle = TextStyle(color = creamWhite)
                )
                passwordErrorState.value?.let {error ->
                    Text(text = error , color = heartRed, modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 20.dp, top = 5.dp))

                }
            }

            Text(
                text = "Forgot Your Password?",
                color = heartRed,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(end = 16.dp, top = 2.dp)
                    .clickable { forgetPassword() }
            )

            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp , end = 16.dp , bottom = 5.dp)
                    .height(50.dp)
                    .border(
                        1.dp,
                        creamWhite,
                        shape = RoundedCornerShape(5.dp),
                    )
                    .background(
                        brush = Brush.verticalGradient(
                            0f to containerPurple,
                            1f to borderdarkpurple
                        ),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .clickable {
                        val emailRegex = ("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
                        val isEmailValid = Pattern
                            .compile(emailRegex)
                            .matcher(emailState.value)
                            .matches()

                        if (emailState.value.isEmpty()) {
                            emailErrorState.value = "Please fill your email"
                            emailFocusRequester.requestFocus()
                        } else if (!isEmailValid) {
                            emailErrorState.value = "Please enter a valid email"
                            emailFocusRequester.requestFocus()
                        } else if (passwordState.value.isEmpty()) {
                            passwordErrorState.value = "Please fill your password"
                            passwordFocusRequester.requestFocus()
                        } else {
                            login(emailState.value, passwordState.value)
                        }
                    }



            ) {
                Row(
                    modifier = Modifier
                        .align(
                            Alignment.Center
                        )
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Login",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = aileron,
                        color = creamWhite,
                    )

                }


            }
//        if (isProgressVisible.value) {
//            LoaderView()
//        }
            Row() {
                Text(
                    text = "Don't have an account?",
                    color = lightBar,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "Signup here",
                    color = darkTealGreen,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable { signUpHere() }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { microsoftLogin() },
                modifier = Modifier
                    .padding(end = 16.dp, start = 16.dp, bottom = 10.dp, top = 10.dp)
                    .fillMaxWidth()
                    .border(1.dp, lightBar, RoundedCornerShape(2.dp)),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onBackground)
            ) {
                Text(
                    text = "Login with Outlook",
                    color = grey,
                    fontSize = 20.sp,
                    fontFamily = futura
                )
            }
            Button(
                onClick = { googleLogin() },
                modifier = Modifier
                    .padding(end = 16.dp, start = 16.dp, top = 15.dp)
                    .fillMaxWidth()
                    .border(1.dp, lightBar, RoundedCornerShape(2.dp)),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onBackground)
            ) {
                Text(
                    text = "Login with Google",
                    color = grey,
                    fontSize = 20.sp,
                    fontFamily = futura
                )
            }

        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(creamWhite)
                .paint(
                    painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
                    contentScale = ContentScale.Crop
                )
                .padding(16.dp)
                .pointerInput(Unit) {
                detectTapGestures (
                    onPress = {keyboardController?.hide()}
                )
            }

            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_union),
//            contentDescription = "Alcheringa Logo",
//            modifier = Modifier.size(101.dp)
//        )
            Text(
                text = "Login",
                color = lighterPurple,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            text = "If you already have an account",
//            color = Color.Black,
//            fontSize = 14.sp,
//            modifier = Modifier.padding(top = 8.dp)
//        )
            Box(modifier = Modifier
                .padding(bottom = 16.dp)
                .height(75.dp)) {
                TextField(
                    value = emailState.value,
                    onValueChange = {
                        emailState.value = it
                        onEmailChange(it)
                        emailErrorState.value = null
                    },
                    isError = emailErrorState.value != null,
                    label =   {Text("Email" , color = grey)} ,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .border(1.dp, darkBar, shape = RoundedCornerShape(2.dp))
                        .fillMaxWidth()
                        .focusRequester(emailFocusRequester)
                        .align(Alignment.TopCenter),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    textStyle = TextStyle(color = darkBar),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = creamWhite
                    )
                )
                emailErrorState.value?.let { error ->
                    Text(text = error, color = heartRed , modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 20.dp, top = 5.dp))
                }
            }
            val passwordVisualTransformation =
                if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
            Box(modifier = Modifier
                .padding(bottom = 16.dp)
                .height(75.dp)) {
                TextField(
                    value = passwordState.value,
                    onValueChange = {
                        passwordState.value = it
                        onPasswordChange(it)
                        passwordErrorState.value = null
                    },
                    isError = passwordErrorState.value != null,
                    label = { Text("Password", color = grey)  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, start = 16.dp)
                        .border(1.dp, darkBar, shape = RoundedCornerShape(2.dp))
                        .focusRequester(passwordFocusRequester)
                        .background(color = creamWhite),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = passwordVisualTransformation,
                    textStyle = TextStyle(color = darkBar),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = creamWhite
                    )
                )
                passwordErrorState.value?.let {error ->
                    Text(text = error , color = heartRed, modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 20.dp, top = 5.dp))

                }
            }

            Text(
                text = "Forgot Your Password?",
                color = heartRed,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(end = 16.dp, top = 2.dp)
                    .clickable { forgetPassword() }
            )

            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp , end = 16.dp , bottom = 5.dp)
                    .height(50.dp)
                    .border(
                        1.dp,
                        MaterialTheme.colors.onBackground,
                        shape = RoundedCornerShape(5.dp),
                    )
                    .background(
                        brush = Brush.verticalGradient(
                            0f to containerPurple,
                            1f to borderdarkpurple
                        ),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .clickable {
                        val emailRegex = ("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
                        val isEmailValid = Pattern
                            .compile(emailRegex)
                            .matcher(emailState.value)
                            .matches()

                        if (emailState.value.isEmpty()) {
                            emailErrorState.value = "Please fill your email"
                            emailFocusRequester.requestFocus()
                        } else if (!isEmailValid) {
                            emailErrorState.value = "Please enter a valid email"
                            emailFocusRequester.requestFocus()
                        } else if (passwordState.value.isEmpty()) {
                            passwordErrorState.value = "Please fill your password"
                            passwordFocusRequester.requestFocus()
                        } else {
                            login(emailState.value, passwordState.value)
                        }
                    }



            ) {
                Row(
                    modifier = Modifier
                        .align(
                            Alignment.Center
                        )
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Login",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = aileron,
                        color = creamWhite,
                    )

                }


            }
//        if (isProgressVisible.value) {
//            LoaderView()
//        }
            Row() {
                Text(
                    text = "Don't have an account?",
                    color = darkBar,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "Signup here",
                    color = darkTealGreen,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable { signUpHere() }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { microsoftLogin() },
                modifier = Modifier
                    .padding(end = 16.dp, start = 16.dp, bottom = 10.dp, top = 10.dp)
                    .fillMaxWidth()
                    .border(1.dp, darkBar, RoundedCornerShape(2.dp)),
                colors = ButtonDefaults.buttonColors(backgroundColor = creamWhite)
            ) {
                Text(
                    text = "Login with Outlook",
                    color = darkGrey,
                    fontSize = 20.sp,
                    fontFamily = futura
                )
            }
            Button(
                onClick = { googleLogin() },
                modifier = Modifier
                    .padding(end = 16.dp, start = 16.dp, top = 15.dp)
                    .fillMaxWidth()
                    .border(1.dp, darkBar, RoundedCornerShape(2.dp)),
                colors = ButtonDefaults.buttonColors(backgroundColor = creamWhite)
            ) {
                Text(
                    text = "Login with Google",
                    color = darkGrey,
                    fontSize = 20.sp,
                    fontFamily = futura,

                )
            }

        }
    }


}



@Preview
@Composable
fun LoginViewPreview() {
    //LoginScreen({} , {} , {} , {} , {})
}