package com.alcheringa.alcheringa2022

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alcheringa.alcheringa2022.ui.theme.aileron
import com.alcheringa.alcheringa2022.ui.theme.creamWhite
import com.alcheringa.alcheringa2022.ui.theme.darkBar
import com.alcheringa.alcheringa2022.ui.theme.darkGrey
import com.alcheringa.alcheringa2022.ui.theme.darkTealGreen
import com.alcheringa.alcheringa2022.ui.theme.darkerGreen
import com.alcheringa.alcheringa2022.ui.theme.futura
import com.alcheringa.alcheringa2022.ui.theme.grey
import com.alcheringa.alcheringa2022.ui.theme.heartRed
import com.alcheringa.alcheringa2022.ui.theme.lightBar
import java.util.regex.Pattern


@Composable
fun SignUpScreen(
    onLoginHereClick: () ->Unit,
    loginWithGoogle: () -> Unit,
    loginWithOutlook: () -> Unit,
    signUp:(name: String , email: String , password: String) -> Unit,
    onEmailChange: (String) -> Unit ,
    onPasswordChange: (String) -> Unit,
    onNameChange: (String) -> Unit
) {
    val name = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val emailErrorState = remember { mutableStateOf<String?>(null) }
    val passwordErrorState = remember { mutableStateOf<String?>(null) }
    val nameErrorState = remember { mutableStateOf<String?>(null) }
    val isPasswordVisible = remember { mutableStateOf(false) }

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val nameFocusRequester = remember { FocusRequester() }


        if(isSystemInDarkTheme()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.onBackground),
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 20.sp,
                    letterSpacing = 0.03.sp,
                    color = darkTealGreen
                    // Add other text styles
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Other Input Fields (Name, Email)
                Box(modifier = Modifier.padding(bottom = 16.dp).height(75.dp)) {
                    TextField(
                        value = name.value, // Bind to a state
                        onValueChange = {
                            name.value = it
                            onEmailChange(it)
                            nameErrorState.value = null
                        },
                        isError = nameErrorState.value != null,
                        label = { Text("Name" , color = grey) },
                        modifier = Modifier
                            .padding(end = 16.dp , start = 16.dp)
                            .border(1.dp, creamWhite, shape = RoundedCornerShape(2.dp))
                            .fillMaxWidth()
                            .focusRequester(nameFocusRequester),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        textStyle = TextStyle(color = creamWhite)
                    )
                    nameErrorState.value?.let { error ->
                        Text(text = error , color = heartRed , modifier = Modifier.align(Alignment.BottomEnd).padding(end = 20.dp,top = 5.dp))

                    }
                }

                Box(modifier = Modifier.padding(bottom = 16.dp).height(75.dp)) {
                    TextField(
                        value = emailState.value,
                        onValueChange = {
                            emailState.value = it
                            onEmailChange(it)
                            emailErrorState.value = null
                        },
                        isError = emailErrorState.value != null,
                        label = { Text("Email" , color = grey) },
                        modifier = Modifier
                            .padding(end = 16.dp , start = 16.dp)
                            .border(1.dp, creamWhite, shape = RoundedCornerShape(2.dp))
                            .fillMaxWidth()
                            .focusRequester(emailFocusRequester),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        textStyle = TextStyle(color = creamWhite)

                    )
                    emailErrorState.value?.let { error ->
                        Text(text = error, color = heartRed , modifier = Modifier.align(Alignment.BottomEnd).padding(end = 20.dp,top = 5.dp))
                    }
                }
                val passwordVisualTransformation =
                    if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
                Box(modifier = Modifier.padding(bottom = 16.dp).height(75.dp)) {
                    TextField(
                        value = passwordState.value, // Bind to a state
                        onValueChange = {
                            passwordState.value = it
                            onPasswordChange(it)
                            passwordErrorState.value = null
                        },
                        isError = passwordErrorState.value != null,
                        label = { Text("Password" , color = grey) },
                        modifier = Modifier
                            .padding(end = 16.dp , start = 16.dp)
                            .border(1.dp, creamWhite, shape = RoundedCornerShape(2.dp))
                            .fillMaxWidth()
                            .focusRequester(passwordFocusRequester),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        visualTransformation = passwordVisualTransformation,
                        textStyle = TextStyle(color = creamWhite)

                    )
                    passwordErrorState.value?.let {error ->
                        Text(text = error , color = heartRed , modifier = Modifier.align(Alignment.BottomEnd).padding(end = 20.dp,top = 5.dp))

                    }
                }



//            Row(
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Divider(
//                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f),
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 16.dp)
//                )
//                Text(text = "or", modifier = Modifier.padding(16.dp))
//                Divider(
//                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f),
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 16.dp)
//                )
//            }

                // Outlook and Google Sign-in Buttons
                // ...

                // Create an Account Button
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
                                0f to darkTealGreen,
                                1f to darkerGreen
                            ),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            val emailRegex = ("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
                            val isEmailValid = Pattern.compile(emailRegex)
                                .matcher(emailState.value)
                                .matches()

                            if (name.value.isEmpty()) {
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
                                signUp(name.value, emailState.value, passwordState.value)
                            }
                        }




                ) {
                    Row(
                        modifier = Modifier
                            .align(
                                Alignment.Center
                            )
                            .width(150.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Sign Up",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = aileron,
                            color = creamWhite,
                        )

                    }
                }

                // Already have an account?
                Row() {
                    Text(
                        "Already have an account?",
                        color = lightBar
                    )

                    // Login Here
                    Text("Login here",
                        color = darkTealGreen,
                        modifier = Modifier.clickable { onLoginHereClick() }
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = { loginWithOutlook() },
                    modifier = Modifier
                        .padding(end = 16.dp, start = 16.dp, bottom = 10.dp, top = 10.dp)
                        .fillMaxWidth()
                        .border(1.dp, lightBar, RoundedCornerShape(2.dp)),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onBackground)
                ) {
                    Text(
                        text = "Login with Outlook",
                        color = grey,
                        fontSize = 20.sp
                    )
                }
                Button(
                    onClick = { loginWithGoogle() },
                    modifier = Modifier
                        .padding(end = 16.dp, start = 16.dp, top = 15.dp)
                        .fillMaxWidth()
                        .border(1.dp, lightBar, RoundedCornerShape(2.dp)),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onBackground)
                ) {
                    Text(
                        text = "Login with Google",
                        color = grey,
                        fontSize = 20.sp
                    )
                }
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = creamWhite),
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 20.sp,
                    letterSpacing = 0.03.sp,
                    color = darkTealGreen
                    // Add other text styles
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Other Input Fields (Name, Email)
                Box(modifier = Modifier.padding(bottom = 16.dp).height(75.dp)) {
                    TextField(
                        value = name.value, // Bind to a state
                        onValueChange = {
                            name.value = it
                            onEmailChange(it)
                            nameErrorState.value = null
                        },
                        isError = nameErrorState.value != null,
                        label = { Text("Name" , color = grey) },
                        modifier = Modifier
                            .padding(end = 16.dp , start = 16.dp)
                            .border(1.dp, darkBar, shape = RoundedCornerShape(2.dp))
                            .fillMaxWidth()
                            .focusRequester(nameFocusRequester),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        textStyle = TextStyle(color = darkBar),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = creamWhite
                        )
                    )
                    nameErrorState.value?.let { error ->
                        Text(text = error , color = heartRed , modifier = Modifier.align(Alignment.BottomEnd).padding(end = 20.dp,top = 5.dp))

                    }
                }

                Box(modifier = Modifier.padding(bottom = 16.dp).height(75.dp)) {
                    TextField(
                        value = emailState.value,
                        onValueChange = {
                            emailState.value = it
                            onEmailChange(it)
                            emailErrorState.value = null
                        },
                        isError = emailErrorState.value != null,
                        label = { Text("Email" , color = grey) },
                        modifier = Modifier
                            .padding(end = 16.dp , start = 16.dp)
                            .border(1.dp, darkBar, shape = RoundedCornerShape(2.dp))
                            .fillMaxWidth()
                            .focusRequester(emailFocusRequester),
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
                        Text(text = error, color = heartRed , modifier = Modifier.align(Alignment.BottomEnd).padding(end = 20.dp,top = 5.dp))
                    }
                }
                val passwordVisualTransformation =
                    if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
                Box(modifier = Modifier.padding(bottom = 16.dp).height(75.dp)) {
                    TextField(
                        value = passwordState.value, // Bind to a state
                        onValueChange = {
                            passwordState.value = it
                            onPasswordChange(it)
                            passwordErrorState.value = null
                        },
                        isError = passwordErrorState.value != null,
                        label = { Text("Password" , color = grey) },
                        modifier = Modifier
                            .padding(end = 16.dp , start = 16.dp)
                            .border(1.dp, darkBar, shape = RoundedCornerShape(2.dp))
                            .fillMaxWidth()
                            .focusRequester(passwordFocusRequester),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        visualTransformation = passwordVisualTransformation,
                        textStyle = TextStyle(color = darkBar),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = creamWhite
                        )

                    )
                    passwordErrorState.value?.let {error ->
                        Text(text = error , color = heartRed , modifier = Modifier.align(Alignment.BottomEnd).padding(end = 20.dp,top = 5.dp))

                    }
                }



//            Row(
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Divider(
//                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f),
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 16.dp)
//                )
//                Text(text = "or", modifier = Modifier.padding(16.dp))
//                Divider(
//                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f),
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 16.dp)
//                )
//            }

                // Outlook and Google Sign-in Buttons
                // ...

                // Create an Account Button
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
                                0f to darkTealGreen,
                                1f to darkerGreen
                            ),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            val emailRegex = ("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
                            val isEmailValid = Pattern.compile(emailRegex)
                                .matcher(emailState.value)
                                .matches()

                            if (name.value.isEmpty()) {
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
                                signUp(name.value, emailState.value, passwordState.value)
                            }
                        }




                ) {
                    Row(
                        modifier = Modifier
                            .align(
                                Alignment.Center
                            )
                            .width(150.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Sign Up",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = aileron,
                            color = creamWhite,
                        )

                    }
                }

                // Already have an account?
                Row() {
                    Text(
                        "Already have an account?",
                        color = darkBar,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    // Login Here
                    Text("Login here",
                        color = darkTealGreen,
                        fontSize = 13.sp,
                        modifier = Modifier.clickable { onLoginHereClick() }.padding(top = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = { loginWithOutlook() },
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
                    onClick = { loginWithGoogle() },
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
                        fontFamily = futura
                    )
                }
            }
        }


}

@Preview
@Composable
fun SignUpViewPreview() {
    //SignUpScreen({} , {} , {})
}
