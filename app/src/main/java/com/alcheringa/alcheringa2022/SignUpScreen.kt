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
import androidx.compose.foundation.layout.width
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
fun SignUpScreen2025(
    onLoginHereClick: () -> Unit,
    loginWithGoogle: () -> Unit,
    loginWithOutlook: () -> Unit,
    loginWithApple: () -> Unit,
    signUp: (name: String, email: String, password: String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onBack: () -> Unit,
    openPrivacyPolicy: () -> Unit
) {
    val nameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val emailErrorState = remember { mutableStateOf<String?>(null) }
    val passwordErrorState = remember { mutableStateOf<String?>(null) }
    val nameErrorState = remember { mutableStateOf<String?>(null) }
    val isPasswordVisible = remember { mutableStateOf(false) }

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val nameFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.sign_up_page),
                contentScale = ContentScale.FillBounds
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { keyboardController?.hide() }
                )
            }
    ) {
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 128.dp, start = 40.dp, end = 40.dp, bottom = 8.dp),
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
                value = nameState.value,
                onValueChange = {
                    nameState.value = it
                    onNameChange(it)
                    nameErrorState.value = null
                },
                isError = nameErrorState.value != null,
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
                        text = "Name",
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
                    .focusRequester(nameFocusRequester)

            )
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
                    .padding(start = 24.dp, end = 24.dp, top = 12.dp)
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
        }

        Button(
            modifier = Modifier
                .align(Alignment.Center)
                .width(132.dp)
                .paint(
                    painter = painterResource(id = R.drawable.sign_in_button),
                    contentScale = ContentScale.FillBounds
                ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            onClick = {
                val emailRegex = ("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
                val isEmailValid = Pattern
                    .compile(emailRegex)
                    .matcher(emailState.value)
                    .matches()

                if (nameState.value.isEmpty()) {
                    nameErrorState.value = "Please enter your name"
                    nameFocusRequester.requestFocus()
                } else if (emailState.value.isEmpty()) {
                    emailErrorState.value = "Please fill your email"
                    emailFocusRequester.requestFocus()
                } else if (!isEmailValid) {
                    emailErrorState.value = "Please enter a valid email"
                    emailFocusRequester.requestFocus()
                } else if (passwordState.value.isEmpty()) {
                    passwordErrorState.value = "Please fill your password"
                    passwordFocusRequester.requestFocus()
                } else if (passwordState.value.length <= 7) {
                    passwordErrorState.value = "Password must be greater than 7 characters"
                    passwordFocusRequester.requestFocus()
                } else {
                    signUp(nameState.value, emailState.value, passwordState.value)
                }
            }
        ) {
            Text(
                text = "Sign up",
                lineHeight = 16.sp,
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
                .align(Alignment.Center)
                .padding(top = 196.dp, start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { loginWithGoogle() },
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
                onClick = { loginWithApple() },
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
                onClick = { loginWithOutlook() },
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 18.dp, end = 18.dp, bottom = 76.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onLoginHereClick() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier
                    .paint(
                        painter = painterResource(id = R.drawable.sign_in_button_bg_green),
                        contentScale = ContentScale.FillBounds
                    )
            ) {
                Text(
                    text = "Login",
                    lineHeight = 19.sp,
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



    }
}

@Preview
@Composable
fun SignUpScreen2025Preview() {
    SignUpScreen2025(
        onLoginHereClick = {},
        loginWithGoogle = {},
        loginWithOutlook = {},
        loginWithApple = {},
        signUp = { _, _, _ -> },
        onEmailChange = {},
        onPasswordChange = {},
        onNameChange = {},
        onBack = {},
        openPrivacyPolicy = {}
    )
}


//@Composable
//fun SignUpScreen(
//    onLoginHereClick: () -> Unit,
//    loginWithGoogle: () -> Unit,
//    loginWithOutlook: () -> Unit,
//    signUp: (name: String, email: String, password: String) -> Unit,
//    onEmailChange: (String) -> Unit,
//    onPasswordChange: (String) -> Unit,
//    onNameChange: (String) -> Unit
//) {
//    val name = remember { mutableStateOf("") }
//    val emailState = remember { mutableStateOf("") }
//    val passwordState = remember { mutableStateOf("") }
//    val emailErrorState = remember { mutableStateOf<String?>(null) }
//    val passwordErrorState = remember { mutableStateOf<String?>(null) }
//    val nameErrorState = remember { mutableStateOf<String?>(null) }
//    val isPasswordVisible = remember { mutableStateOf(false) }
//
//    val emailFocusRequester = remember { FocusRequester() }
//    val passwordFocusRequester = remember { FocusRequester() }
//    val nameFocusRequester = remember { FocusRequester() }
//    val keyboardController = LocalSoftwareKeyboardController.current
//
//
//    if (isSystemInDarkTheme()) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier
//                .fillMaxSize()
////                    .background(color = MaterialTheme.colors.onBackground),
//                .paint(
//                    painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
//                    contentScale = ContentScale.Crop
//                )
//                .pointerInput(Unit) {
//                    detectTapGestures(
//                        onPress = { keyboardController?.hide() }
//                    )
//                }
//        ) {
//            Text(
//                text = "Sign Up",
//                fontSize = 20.sp,
//                letterSpacing = 0.03.sp,
//                color = darkTealGreen
//                // Add other text styles
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            // Other Input Fields (Name, Email)
//            Box(modifier = Modifier
//                .padding(bottom = 16.dp)
//                .height(75.dp)) {
//                TextField(
//                    value = name.value, // Bind to a state
//                    onValueChange = {
//                        name.value = it
//                        onNameChange(it)
//                        nameErrorState.value = null
//                    },
//                    isError = nameErrorState.value != null,
//                    label = { Text("Name", color = grey) },
//                    modifier = Modifier
//                        .padding(end = 16.dp, start = 16.dp)
//                        .border(1.dp, creamWhite, shape = RoundedCornerShape(2.dp))
//                        .fillMaxWidth()
//                        .focusRequester(nameFocusRequester),
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Text,
//                        imeAction = ImeAction.Next
//                    ),
//                    textStyle = TextStyle(color = creamWhite)
//                )
//                nameErrorState.value?.let { error ->
//                    Text(
//                        text = error,
//                        color = heartRed,
//                        modifier = Modifier
//                            .align(Alignment.BottomEnd)
//                            .padding(end = 20.dp, top = 5.dp)
//                    )
//
//                }
//            }
//
//            Box(modifier = Modifier
//                .padding(bottom = 16.dp)
//                .height(75.dp)) {
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
//                        .padding(end = 16.dp, start = 16.dp)
//                        .border(1.dp, creamWhite, shape = RoundedCornerShape(2.dp))
//                        .fillMaxWidth()
//                        .focusRequester(emailFocusRequester),
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Email,
//                        imeAction = ImeAction.Next
//                    ),
//                    textStyle = TextStyle(color = creamWhite)
//
//                )
//                emailErrorState.value?.let { error ->
//                    Text(
//                        text = error,
//                        color = heartRed,
//                        modifier = Modifier
//                            .align(Alignment.BottomEnd)
//                            .padding(end = 20.dp, top = 5.dp)
//                    )
//                }
//            }
//            val passwordVisualTransformation =
//                if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
//            Box(modifier = Modifier
//                .padding(bottom = 16.dp)
//                .height(75.dp)) {
//                TextField(
//                    value = passwordState.value, // Bind to a state
//                    onValueChange = {
//                        passwordState.value = it
//                        onPasswordChange(it)
//                        passwordErrorState.value = null
//                    },
//                    isError = passwordErrorState.value != null,
//                    label = { Text("Password", color = grey) },
//                    modifier = Modifier
//                        .padding(end = 16.dp, start = 16.dp)
//                        .border(1.dp, creamWhite, shape = RoundedCornerShape(2.dp))
//                        .fillMaxWidth()
//                        .focusRequester(passwordFocusRequester),
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Password,
//                        imeAction = ImeAction.Next
//                    ),
//                    visualTransformation = passwordVisualTransformation,
//                    textStyle = TextStyle(color = creamWhite)
//
//                )
//                passwordErrorState.value?.let { error ->
//                    Text(
//                        text = error,
//                        color = heartRed,
//                        modifier = Modifier
//                            .align(Alignment.BottomEnd)
//                            .padding(end = 20.dp, top = 5.dp)
//                    )
//
//                }
//            }
//
//
////            Row(
////                horizontalArrangement = Arrangement.Center,
////                modifier = Modifier.fillMaxWidth()
////            ) {
////                Divider(
////                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f),
////                    modifier = Modifier
////                        .weight(1f)
////                        .padding(horizontal = 16.dp)
////                )
////                Text(text = "or", modifier = Modifier.padding(16.dp))
////                Divider(
////                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f),
////                    modifier = Modifier
////                        .weight(1f)
////                        .padding(horizontal = 16.dp)
////                )
////            }
//
//            // Outlook and Google Sign-in Buttons
//            // ...
//
//            // Create an Account Button
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
//                            0f to darkTealGreen,
//                            1f to darkerGreen
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
//                        if (name.value.isEmpty()) {
//                            nameErrorState.value = "Please enter your name"
//                            nameFocusRequester.requestFocus()
//                        } else if (emailState.value.isEmpty()) {
//                            emailErrorState.value = "Please fill your email"
//                            emailFocusRequester.requestFocus()
//                        } else if (!isEmailValid) {
//                            emailErrorState.value = "Please enter a valid email"
//                            emailFocusRequester.requestFocus()
//                        } else if (passwordState.value.isEmpty()) {
//                            passwordErrorState.value = "Please fill your password"
//                            passwordFocusRequester.requestFocus()
//                        } else if (passwordState.value.length <= 7) {
//                            passwordErrorState.value = "Password must be greater than 7 characters"
//                            passwordFocusRequester.requestFocus()
//                        } else {
//                            signUp(name.value, emailState.value, passwordState.value)
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
//                        .width(150.dp),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Text(
//                        text = "Sign Up",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        fontFamily = aileron,
//                        color = creamWhite,
//                    )
//
//                }
//            }
//
//            // Already have an account?
//            Row() {
//                Text(
//                    "Already have an account?",
//                    color = lightBar
//                )
//
//                // Login Here
//                Text("Login here",
//                    color = darkTealGreen,
//                    modifier = Modifier.clickable { onLoginHereClick() }
//                )
//            }
//            Spacer(modifier = Modifier.height(40.dp))
//
//            Button(
//                onClick = { loginWithOutlook() },
//                modifier = Modifier
//                    .padding(end = 16.dp, start = 16.dp, bottom = 10.dp, top = 10.dp)
//                    .fillMaxWidth()
//                    .border(1.dp, lightBar, RoundedCornerShape(2.dp)),
//                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onBackground)
//            ) {
//                Text(
//                    text = "Login with Outlook",
//                    color = grey,
//                    fontSize = 20.sp
//                )
//            }
//            Button(
//                onClick = { loginWithGoogle() },
//                modifier = Modifier
//                    .padding(end = 16.dp, start = 16.dp, top = 15.dp)
//                    .fillMaxWidth()
//                    .border(1.dp, lightBar, RoundedCornerShape(2.dp)),
//                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onBackground)
//            ) {
//                Text(
//                    text = "Login with Google",
//                    color = grey,
//                    fontSize = 20.sp
//                )
//            }
//        }
//    } else {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier
//                .fillMaxSize()
//                .background(color = creamWhite)
//                .paint(
//                    painterResource(id = if (isSystemInDarkTheme()) R.drawable.background_texture_dark else R.drawable.background_texture_light),
//                    contentScale = ContentScale.Crop
//                )
//                .pointerInput(Unit) {
//                    detectTapGestures(
//                        onPress = { keyboardController?.hide() }
//                    )
//                },
//        ) {
//            Text(
//                text = "Sign Up",
//                fontSize = 20.sp,
//                letterSpacing = 0.03.sp,
//                color = darkTealGreen
//                // Add other text styles
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            // Other Input Fields (Name, Email)
//            Box(modifier = Modifier
//                .padding(bottom = 16.dp)
//                .height(75.dp)) {
//                TextField(
//                    value = name.value, // Bind to a state
//                    onValueChange = {
//                        name.value = it
//                        onEmailChange(it)
//                        nameErrorState.value = null
//                    },
//                    isError = nameErrorState.value != null,
//                    label = { Text("Name", color = grey) },
//                    modifier = Modifier
//                        .padding(end = 16.dp, start = 16.dp)
//                        .border(1.dp, darkBar, shape = RoundedCornerShape(2.dp))
//                        .fillMaxWidth()
//                        .focusRequester(nameFocusRequester),
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Text,
//                        imeAction = ImeAction.Next
//                    ),
//                    textStyle = TextStyle(color = darkBar),
//                    colors = TextFieldDefaults.textFieldColors(
//                        backgroundColor = creamWhite
//                    )
//                )
//                nameErrorState.value?.let { error ->
//                    Text(
//                        text = error,
//                        color = heartRed,
//                        modifier = Modifier
//                            .align(Alignment.BottomEnd)
//                            .padding(end = 20.dp, top = 5.dp)
//                    )
//
//                }
//            }
//
//            Box(modifier = Modifier
//                .padding(bottom = 16.dp)
//                .height(75.dp)) {
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
//                        .padding(end = 16.dp, start = 16.dp)
//                        .border(1.dp, darkBar, shape = RoundedCornerShape(2.dp))
//                        .fillMaxWidth()
//                        .focusRequester(emailFocusRequester),
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Email,
//                        imeAction = ImeAction.Next
//                    ),
//                    textStyle = TextStyle(color = darkBar),
//                    colors = TextFieldDefaults.textFieldColors(
//                        backgroundColor = creamWhite
//                    )
//
//                )
//                emailErrorState.value?.let { error ->
//                    Text(
//                        text = error,
//                        color = heartRed,
//                        modifier = Modifier
//                            .align(Alignment.BottomEnd)
//                            .padding(end = 20.dp, top = 5.dp)
//                    )
//                }
//            }
//            val passwordVisualTransformation =
//                if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
//            Box(modifier = Modifier
//                .padding(bottom = 16.dp)
//                .height(75.dp)) {
//                TextField(
//                    value = passwordState.value, // Bind to a state
//                    onValueChange = {
//                        passwordState.value = it
//                        onPasswordChange(it)
//                        passwordErrorState.value = null
//                    },
//                    isError = passwordErrorState.value != null,
//                    label = { Text("Password", color = grey) },
//                    modifier = Modifier
//                        .padding(end = 16.dp, start = 16.dp)
//                        .border(1.dp, darkBar, shape = RoundedCornerShape(2.dp))
//                        .fillMaxWidth()
//                        .focusRequester(passwordFocusRequester),
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Password,
//                        imeAction = ImeAction.Next
//                    ),
//                    visualTransformation = passwordVisualTransformation,
//                    textStyle = TextStyle(color = darkBar),
//                    colors = TextFieldDefaults.textFieldColors(
//                        backgroundColor = creamWhite
//                    )
//
//                )
//                passwordErrorState.value?.let { error ->
//                    Text(
//                        text = error,
//                        color = heartRed,
//                        modifier = Modifier
//                            .align(Alignment.BottomEnd)
//                            .padding(end = 20.dp, top = 5.dp)
//                    )
//
//                }
//            }
//
//
////            Row(
////                horizontalArrangement = Arrangement.Center,
////                modifier = Modifier.fillMaxWidth()
////            ) {
////                Divider(
////                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f),
////                    modifier = Modifier
////                        .weight(1f)
////                        .padding(horizontal = 16.dp)
////                )
////                Text(text = "or", modifier = Modifier.padding(16.dp))
////                Divider(
////                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f),
////                    modifier = Modifier
////                        .weight(1f)
////                        .padding(horizontal = 16.dp)
////                )
////            }
//
//            // Outlook and Google Sign-in Buttons
//            // ...
//
//            // Create an Account Button
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
//                            0f to darkTealGreen,
//                            1f to darkerGreen
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
//                        if (name.value.isEmpty()) {
//                            nameErrorState.value = "Please enter your name"
//                            nameFocusRequester.requestFocus()
//                        } else if (emailState.value.isEmpty()) {
//                            emailErrorState.value = "Please fill your email"
//                            emailFocusRequester.requestFocus()
//                        } else if (!isEmailValid) {
//                            emailErrorState.value = "Please enter a valid email"
//                            emailFocusRequester.requestFocus()
//                        } else if (passwordState.value.isEmpty()) {
//                            passwordErrorState.value = "Please fill your password"
//                            passwordFocusRequester.requestFocus()
//                        } else if (passwordState.value.length <= 7) {
//                            passwordErrorState.value = "Password must be greater than 7 characters"
//                            passwordFocusRequester.requestFocus()
//                        } else {
//                            signUp(name.value, emailState.value, passwordState.value)
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
//                        .width(150.dp),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Text(
//                        text = "Sign Up",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        fontFamily = aileron,
//                        color = creamWhite,
//                    )
//
//                }
//            }
//
//            // Already have an account?
//            Row() {
//                Text(
//                    "Already have an account?",
//                    color = darkBar,
//                    fontSize = 13.sp,
//                    fontWeight = FontWeight.Normal,
//                    modifier = Modifier.padding(top = 8.dp)
//                )
//
//                // Login Here
//                Text(
//                    "Login here",
//                    color = darkTealGreen,
//                    fontSize = 13.sp,
//                    modifier = Modifier
//                        .clickable { onLoginHereClick() }
//                        .padding(top = 8.dp)
//                )
//            }
//            Spacer(modifier = Modifier.height(40.dp))
//
//            Button(
//                onClick = { loginWithOutlook() },
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
//                onClick = { loginWithGoogle() },
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
//                    fontFamily = futura
//                )
//            }
//        }
//    }
//
//
//}

//@Preview
//@Composable
//fun SignUpViewPreview() {
//    //SignUpScreen({} , {} , {})
//}
