package com.alcheringa.alcheringa2022

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.regex.Pattern

@Composable
fun LoginScreen2025(
    forgetPassword: () -> Unit,
    microsoftLogin: () -> Unit,
    googleLogin: () -> Unit,
    appleLogin: () -> Unit,
    openPrivacyPolicy: () -> Unit,
    login: (String?, String?) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onBack: () -> Unit
) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val isPasswordVisible = remember { mutableStateOf(false) }
    val emailErrorState = remember { mutableStateOf<String?>(null) }
    val passwordErrorState = remember { mutableStateOf<String?>(null) }

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current


    Box(modifier = Modifier
        .fillMaxSize()
        .paint(
            painter = painterResource(R.drawable.login_page_2025_bg),
            contentScale = ContentScale.FillBounds
        )
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = { keyboardController?.hide() }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(top = 24.dp, start = 40.dp, end = 40.dp, bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,

                ),
                singleLine = true,
                value = emailState.value,
                onValueChange = {
                    emailState.value = it
                    onEmailChange(it)
                    emailErrorState.value = null
                },
                isError = emailErrorState.value != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                textStyle = TextStyle(
                    fontSize = 22.sp,
                    color = Color(0xFFFF77A8),
                    fontFamily = FontFamily(
                        Font(R.font.alcher_pixel, FontWeight.Normal),
                        Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                    ),
                    letterSpacing = 1.sp
                ),
                placeholder = {
                    Text(
                        text = "Email",
                        modifier = Modifier
                            .alpha(0.2f),
                        style = TextStyle(
                            fontSize = 22.sp,
                            color = Color(0xFFFF77A8),
                            fontFamily = FontFamily(
                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                            ),
                            letterSpacing = 1.sp
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp)
                    .paint(
                        painterResource(id = R.drawable.textfield_back),
                        contentScale = ContentScale.FillBounds
                    )
                    .focusRequester(emailFocusRequester)

            )
            val passwordVisualTransformation =
                if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                value = passwordState.value,
                onValueChange = {
                    passwordState.value = it
                    onPasswordChange(it)
                    passwordErrorState.value = null
                },
                isError = passwordErrorState.value != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                textStyle = TextStyle(
                    fontSize = 22.sp,
                    color = Color(0xFFFF77A8),
                    fontFamily = FontFamily(
                        Font(R.font.alcher_pixel, FontWeight.Normal),
                        Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                    ),
                    letterSpacing = 1.sp
                ),
                placeholder = {
                    Text(
                        text = "Password",
                        modifier = Modifier
                            .alpha(0.2f),
                        style = TextStyle(
                            fontSize = 22.sp,
                            color = Color(0xFFFF77A8),
                            fontFamily = FontFamily(
                                Font(R.font.alcher_pixel, FontWeight.Normal),
                                Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                            ),
                            letterSpacing = 1.sp
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 12.dp)
                    .paint(
                        painterResource(id = R.drawable.textfield_back),
                        contentScale = ContentScale.FillBounds
                    )
                    .focusRequester(passwordFocusRequester),
                visualTransformation = passwordVisualTransformation

            )

            TextButton(onClick = { forgetPassword() }) {
                Text(
                    text = "Forgot Password",
                    style = TextStyle(
                        fontSize = 20.sp,
                        //fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF77A8),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.alcher_pixel, FontWeight.Normal),
                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                        ),
                        shadow = Shadow(
                            color = Color(0xFF7E2953),
                            offset = Offset(6f, 5f)
                        )
                    )
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 18.dp, end = 18.dp, bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Button(
                onClick = {
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
                },
                modifier = Modifier
                    .paint(
                        painter = painterResource(id = R.drawable.sign_in_button),
                        contentScale = ContentScale.FillBounds
                    ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                )
            ) {
                Text(
                    text = "Login",
                    style = TextStyle(
                        fontSize = 24.sp,
                        //fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFF1E8),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.alcher_pixel, FontWeight.Normal),
                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                        ),
                        shadow = Shadow(
                            color = Color(0xFF000000),
                            offset = Offset(3f, 2f)
                        )
                    ),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 4.dp, start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { googleLogin() },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(60.dp)
                        .paint(
                            painterResource(id = R.drawable.google_button_bg)
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.symbol_svg),
                        contentDescription = "Google Log in",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                IconButton(
                    onClick = { appleLogin() },
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(60.dp)
                        .paint(
                            painterResource(id = R.drawable.google_button_bg)
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logo_svg),
                        contentDescription = "apple Log in",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                IconButton(
                    onClick = { microsoftLogin() },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(60.dp)
                        .paint(
                            painterResource(id = R.drawable.outlook_button_bg)
                        )

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.microsoft_outlook_logo_svg),
                        contentDescription = "outlook Log in",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            }

            TextButton(onClick = { openPrivacyPolicy() }) {
                Text(
                    text = "Privacy Policy",
                    style = TextStyle(
                        fontSize = 18.sp,
                        //fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF77A8),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.alcher_pixel, FontWeight.Normal),
                            Font(R.font.alcher_pixel_bold, FontWeight.Bold),
                        ),
                        shadow = Shadow(
                            color = Color(0xFF7E2953),
                            offset = Offset(6f, 5f)
                        )
                    )
                )
            }


        }

        IconButton(
            onClick = {
                onBack()
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {

            Icon(
                painter = painterResource(id = R.drawable.back_button_art),
                contentDescription = "Back Button",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(48.dp)
            )

        }
    }

}

@Preview
@Composable
fun LoginScreen2025Preview() {
    LoginScreen2025(
        forgetPassword = { },
        microsoftLogin = { },
        googleLogin = { },
        appleLogin = { },
        login = { _, _ -> },
        onEmailChange = { },
        onPasswordChange = { },
        onBack = { },
        openPrivacyPolicy = { }
    )
}
//
//@Composable
//fun LoginScreen(
//    forgetPassword: () -> Unit,
//    signUpHere: () -> Unit,
//    microsoftLogin: () -> Unit,
//    googleLogin: () -> Unit,
//    login: (String?, String?) -> Unit,
//    onEmailChange: (String) -> Unit,
//    onPasswordChange: (String) -> Unit
//) {
//    val emailState = remember { mutableStateOf("") }
//    val passwordState = remember { mutableStateOf("") }
//    val isPasswordVisible = remember { mutableStateOf(false) }
//    val isProgressVisible = remember { mutableStateOf(false) }
//    val emailErrorState = remember { mutableStateOf<String?>(null) }
//    val passwordErrorState = remember { mutableStateOf<String?>(null) }
//
//    val emailFocusRequester = remember { FocusRequester() }
//    val passwordFocusRequester = remember { FocusRequester() }
//    val keyboardController = LocalSoftwareKeyboardController.current
//
//    if (isSystemInDarkTheme()) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
////                .background(MaterialTheme.colors.onBackground)
//                //.padding(16.dp)
//                .paint(
//                    painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
//                    contentScale = ContentScale.Crop
//                )
//                .pointerInput(Unit) {
//                    detectTapGestures(
//                        onPress = { keyboardController?.hide() }
//                    )
//                },
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
////        Image(
////            painter = painterResource(id = R.drawable.ic_union),
////            contentDescription = "Alcheringa Logo",
////            modifier = Modifier.size(101.dp)
////        )
//            Text(
//                text = "Login",
//                color = lighterPurple,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(top = 16.dp)
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
////        Text(
////            text = "If you already have an account",
////            color = Color.Black,
////            fontSize = 14.sp,
////            modifier = Modifier.padding(top = 8.dp)
////        )
//            Box(
//                modifier = Modifier
//                    .padding(bottom = 12.dp)
//                    .height(75.dp)
//            ) {
//                TextField(
//                    value = emailState.value,
//                    onValueChange = {
//                        emailState.value = it
//                        onEmailChange(it)
//                        emailErrorState.value = null
//                    },
//                    isError = emailErrorState.value != null,
//                    label = { Text("Email", color = grey) },
//                    modifier = Modifier
//                        .padding(start = 16.dp, end = 16.dp)
//                        .border(1.dp, creamWhite, shape = RoundedCornerShape(2.dp))
//                        .fillMaxWidth()
//                        .focusRequester(emailFocusRequester)
//                        .align(Alignment.TopCenter),
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Email,
//                        imeAction = ImeAction.Next
//                    ),
//                    textStyle = TextStyle(color = creamWhite)
//                )
//                emailErrorState.value?.let { error ->
//                    Text(
//                        text = error, color = heartRed, modifier = Modifier
//                            .align(Alignment.BottomEnd)
//                            .padding(end = 20.dp, top = 5.dp)
//                    )
//                }
//            }
//            val passwordVisualTransformation =
//                if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
//            Box(
//                modifier = Modifier
//                    .padding(bottom = 16.dp)
//                    .height(75.dp)
//            ) {
//                TextField(
//                    value = passwordState.value,
//                    onValueChange = {
//                        passwordState.value = it
//                        onPasswordChange(it)
//                        passwordErrorState.value = null
//                    },
//                    isError = passwordErrorState.value != null,
//                    label = { Text("Password", color = grey) },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(end = 16.dp, start = 16.dp)
//                        .border(1.dp, creamWhite, shape = RoundedCornerShape(2.dp))
//                        .focusRequester(passwordFocusRequester),
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Password,
//                        imeAction = ImeAction.Done
//                    ),
//                    visualTransformation = passwordVisualTransformation,
//                    textStyle = TextStyle(color = creamWhite)
//                )
//                passwordErrorState.value?.let { error ->
//                    Text(
//                        text = error, color = heartRed, modifier = Modifier
//                            .align(Alignment.BottomEnd)
//                            .padding(end = 20.dp, top = 5.dp)
//                    )
//
//                }
//            }
//
//            Text(
//                text = "Forgot Your Password?",
//                color = heartRed,
//                fontSize = 14.sp,
//                modifier = Modifier
//                    .align(alignment = Alignment.End)
//                    .padding(end = 16.dp, top = 2.dp)
//                    .clickable { forgetPassword() }
//            )
//
//            Spacer(modifier = Modifier.height(50.dp))
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 16.dp, end = 16.dp, bottom = 5.dp)
//                    .height(50.dp)
//                    .border(
//                        1.dp,
//                        creamWhite,
//                        shape = RoundedCornerShape(5.dp),
//                    )
//                    .background(
//                        brush = Brush.verticalGradient(
//                            0f to containerPurple,
//                            1f to borderdarkpurple
//                        ),
//                        shape = RoundedCornerShape(5.dp)
//                    )
//                    .clickable {
//                        val emailRegex = ("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
//                                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
//                        val isEmailValid = Pattern
//                            .compile(emailRegex)
//                            .matcher(emailState.value)
//                            .matches()
//
//                        if (emailState.value.isEmpty()) {
//                            emailErrorState.value = "Please fill your email"
//                            emailFocusRequester.requestFocus()
//                        } else if (!isEmailValid) {
//                            emailErrorState.value = "Please enter a valid email"
//                            emailFocusRequester.requestFocus()
//                        } else if (passwordState.value.isEmpty()) {
//                            passwordErrorState.value = "Please fill your password"
//                            passwordFocusRequester.requestFocus()
//                        } else {
//                            login(emailState.value, passwordState.value)
//                        }
//                    }
//
//
//            ) {
//                Row(
//                    modifier = Modifier
//                        .align(
//                            Alignment.Center
//                        )
//                        .fillMaxHeight(),
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Login",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        fontFamily = aileron,
//                        color = creamWhite,
//                    )
//
//                }
//
//
//            }
////        if (isProgressVisible.value) {
////            LoaderView()
////        }
//            Row() {
//                Text(
//                    text = "Don't have an account?",
//                    color = lightBar,
//                    fontSize = 13.sp,
//                    fontWeight = FontWeight.Normal,
//                    modifier = Modifier.padding(top = 8.dp)
//                )
//                Text(
//                    text = "Signup here",
//                    color = darkTealGreen,
//                    fontSize = 13.sp,
//                    modifier = Modifier
//                        .padding(top = 8.dp)
//                        .clickable { signUpHere() }
//                )
//            }
//
//            Spacer(modifier = Modifier.height(40.dp))
//
//            Button(
//                onClick = { microsoftLogin() },
//                modifier = Modifier
//                    .padding(end = 16.dp, start = 16.dp, bottom = 10.dp, top = 10.dp)
//                    .fillMaxWidth()
//                    .border(1.dp, lightBar, RoundedCornerShape(2.dp)),
//                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onBackground)
//            ) {
//                Text(
//                    text = "Login with Outlook",
//                    color = grey,
//                    fontSize = 20.sp,
//                    fontFamily = futura
//                )
//            }
//            Button(
//                onClick = { googleLogin() },
//                modifier = Modifier
//                    .padding(end = 16.dp, start = 16.dp, top = 15.dp)
//                    .fillMaxWidth()
//                    .border(1.dp, lightBar, RoundedCornerShape(2.dp)),
//                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onBackground)
//            ) {
//                Text(
//                    text = "Login with Google",
//                    color = grey,
//                    fontSize = 20.sp,
//                    fontFamily = futura
//                )
//            }
//
//        }
//    } else {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(creamWhite)
//                .paint(
//                    painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
//                    contentScale = ContentScale.Crop
//                )
//                .padding(16.dp)
//                .pointerInput(Unit) {
//                    detectTapGestures(
//                        onPress = { keyboardController?.hide() }
//                    )
//                },
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
////        Image(
////            painter = painterResource(id = R.drawable.ic_union),
////            contentDescription = "Alcheringa Logo",
////            modifier = Modifier.size(101.dp)
////        )
//            Text(
//                text = "Login",
//                color = lighterPurple,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(top = 16.dp)
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
////        Text(
////            text = "If you already have an account",
////            color = Color.Black,
////            fontSize = 14.sp,
////            modifier = Modifier.padding(top = 8.dp)
////        )
//            Box(
//                modifier = Modifier
//                    .padding(bottom = 16.dp)
//                    .height(75.dp)
//            ) {
//                TextField(
//                    value = emailState.value,
//                    onValueChange = {
//                        emailState.value = it
//                        onEmailChange(it)
//                        emailErrorState.value = null
//                    },
//                    isError = emailErrorState.value != null,
//                    label = { Text("Email", color = grey) },
//                    modifier = Modifier
//                        .padding(start = 16.dp, end = 16.dp)
//                        .border(1.dp, darkBar, shape = RoundedCornerShape(2.dp))
//                        .fillMaxWidth()
//                        .focusRequester(emailFocusRequester)
//                        .align(Alignment.TopCenter),
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Email,
//                        imeAction = ImeAction.Next
//                    ),
//                    textStyle = TextStyle(color = darkBar),
//                    colors = TextFieldDefaults.textFieldColors(
//                        backgroundColor = creamWhite
//                    )
//                )
//                emailErrorState.value?.let { error ->
//                    Text(
//                        text = error, color = heartRed, modifier = Modifier
//                            .align(Alignment.BottomEnd)
//                            .padding(end = 20.dp, top = 5.dp)
//                    )
//                }
//            }
//            val passwordVisualTransformation =
//                if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
//            Box(
//                modifier = Modifier
//                    .padding(bottom = 16.dp)
//                    .height(75.dp)
//            ) {
//                TextField(
//                    value = passwordState.value,
//                    onValueChange = {
//                        passwordState.value = it
//                        onPasswordChange(it)
//                        passwordErrorState.value = null
//                    },
//                    isError = passwordErrorState.value != null,
//                    label = { Text("Password", color = grey) },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(end = 16.dp, start = 16.dp)
//                        .border(1.dp, darkBar, shape = RoundedCornerShape(2.dp))
//                        .focusRequester(passwordFocusRequester)
//                        .background(color = creamWhite),
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Password,
//                        imeAction = ImeAction.Done
//                    ),
//                    visualTransformation = passwordVisualTransformation,
//                    textStyle = TextStyle(color = darkBar),
//                    colors = TextFieldDefaults.textFieldColors(
//                        backgroundColor = creamWhite
//                    )
//                )
//                passwordErrorState.value?.let { error ->
//                    Text(
//                        text = error, color = heartRed, modifier = Modifier
//                            .align(Alignment.BottomEnd)
//                            .padding(end = 20.dp, top = 5.dp)
//                    )
//
//                }
//            }
//
//            Text(
//                text = "Forgot Your Password?",
//                color = heartRed,
//                fontSize = 14.sp,
//                modifier = Modifier
//                    .align(alignment = Alignment.End)
//                    .padding(end = 16.dp, top = 2.dp)
//                    .clickable { forgetPassword() }
//            )
//
//            Spacer(modifier = Modifier.height(50.dp))
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 16.dp, end = 16.dp, bottom = 5.dp)
//                    .height(50.dp)
//                    .border(
//                        1.dp,
//                        MaterialTheme.colors.onBackground,
//                        shape = RoundedCornerShape(5.dp),
//                    )
//                    .background(
//                        brush = Brush.verticalGradient(
//                            0f to containerPurple,
//                            1f to borderdarkpurple
//                        ),
//                        shape = RoundedCornerShape(5.dp)
//                    )
//                    .clickable {
//                        val emailRegex = ("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
//                                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
//                        val isEmailValid = Pattern
//                            .compile(emailRegex)
//                            .matcher(emailState.value)
//                            .matches()
//
//                        if (emailState.value.isEmpty()) {
//                            emailErrorState.value = "Please fill your email"
//                            emailFocusRequester.requestFocus()
//                        } else if (!isEmailValid) {
//                            emailErrorState.value = "Please enter a valid email"
//                            emailFocusRequester.requestFocus()
//                        } else if (passwordState.value.isEmpty()) {
//                            passwordErrorState.value = "Please fill your password"
//                            passwordFocusRequester.requestFocus()
//                        } else {
//                            login(emailState.value, passwordState.value)
//                        }
//                    }
//
//
//            ) {
//                Row(
//                    modifier = Modifier
//                        .align(
//                            Alignment.Center
//                        )
//                        .fillMaxHeight(),
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Login",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        fontFamily = aileron,
//                        color = creamWhite,
//                    )
//
//                }
//
//
//            }
////        if (isProgressVisible.value) {
////            LoaderView()
////        }
//            Row() {
//                Text(
//                    text = "Don't have an account?",
//                    color = darkBar,
//                    fontSize = 13.sp,
//                    fontWeight = FontWeight.Normal,
//                    modifier = Modifier.padding(top = 8.dp)
//                )
//                Text(
//                    text = "Signup here",
//                    color = darkTealGreen,
//                    fontSize = 13.sp,
//                    modifier = Modifier
//                        .padding(top = 8.dp)
//                        .clickable { signUpHere() }
//                )
//            }
//
//            Spacer(modifier = Modifier.height(40.dp))
//
//            Button(
//                onClick = { microsoftLogin() },
//                modifier = Modifier
//                    .padding(end = 16.dp, start = 16.dp, bottom = 10.dp, top = 10.dp)
//                    .fillMaxWidth()
//                    .border(1.dp, darkBar, RoundedCornerShape(2.dp)),
//                colors = ButtonDefaults.buttonColors(backgroundColor = creamWhite)
//            ) {
//                Text(
//                    text = "Login with Outlook",
//                    color = darkGrey,
//                    fontSize = 20.sp,
//                    fontFamily = futura
//                )
//            }
//            Button(
//                onClick = { googleLogin() },
//                modifier = Modifier
//                    .padding(end = 16.dp, start = 16.dp, top = 15.dp)
//                    .fillMaxWidth()
//                    .border(1.dp, darkBar, RoundedCornerShape(2.dp)),
//                colors = ButtonDefaults.buttonColors(backgroundColor = creamWhite)
//            ) {
//                Text(
//                    text = "Login with Google",
//                    color = darkGrey,
//                    fontSize = 20.sp,
//                    fontFamily = futura,
//
//                    )
//            }
//
//        }
//    }
//
//
//}


//@Preview
//@Composable
//fun LoginViewPreview() {
//    LoginScreen({} , {} , {} , {} , {
//            _: String?, _: String? ->
//            println("Login Clicked")
//    }, {
//    println("Email Changed")
//    }, {
//
//    println("Password Changed")
//    })
//}