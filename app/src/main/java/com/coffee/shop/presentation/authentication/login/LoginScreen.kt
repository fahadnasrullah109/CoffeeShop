package com.coffee.shop.presentation.authentication.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cofee.shop.R
import com.coffee.shop.components.AppErrorText
import com.coffee.shop.components.CofeeButton
import com.coffee.shop.components.ComposableLifecycle
import com.coffee.shop.theme.CofeeShopTheme
import com.coffee.shop.theme.soraFamily
import com.coffee.shop.theme.textGrayColor
import com.coffee.shop.theme.textTitleColor
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    onForgotPasswordTap: () -> Unit,
    onRegisterTap: () -> Unit
) {
    LoginScreenContent(
        modifier = modifier,
        onLoginSuccess = onLoginSuccess,
        onForgotPasswordTap = onForgotPasswordTap,
        onRegisterTap = onRegisterTap,
    )
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onForgotPasswordTap: () -> Unit,
    onRegisterTap: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = hostState)
    }) { contentPaddings ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current
        val context = LocalContext.current
        var showPassword by remember { mutableStateOf(value = false) }
        ComposableLifecycle { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    viewModel.onEvent(LoginUIEvents.ResetStates)
                }

                else -> {}
            }
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(contentPaddings)
                .padding(
                    horizontal = 20.dp, vertical = 16.dp
                ), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LaunchedEffect(key1 = uiState) {
                if (uiState.loginSuccess) {
                    onLoginSuccess.invoke()
                }
            }
            LaunchedEffect(key1 = uiState.error) {
                uiState.error?.let { error ->
                    scope.let {
                        it.launch {
                            hostState.showSnackbar(error)
                        }
                    }
                }
            }

            Text(
                text = stringResource(id = R.string.label_login_title),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                style = TextStyle(color = textTitleColor, fontSize = 25.sp),
                fontFamily = soraFamily,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.label_login_description),
                fontFamily = soraFamily,
                style = TextStyle(color = textGrayColor)
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = uiState.email, leadingIcon = {
                Icon(
                    Icons.Filled.Email,
                    contentDescription = stringResource(id = R.string.email_content_desc)
                )
            }, onValueChange = {
                viewModel.onEvent(LoginUIEvents.OnEmailChanged(email = it))
            }, label = {
                Text(text = stringResource(id = R.string.label_login_email_address))
            }, keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ), modifier = Modifier.fillMaxWidth(), isError = uiState.emailError != null
            )

            uiState.emailError?.let {
                AppErrorText(
                    error = it, modifier = Modifier.fillMaxWidth(), align = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = uiState.password, leadingIcon = {
                Icon(
                    Icons.Filled.Lock,
                    contentDescription = stringResource(id = R.string.email_content_desc)
                )
            }, onValueChange = {
                viewModel.onEvent(LoginUIEvents.OnPasswordChanged(password = it))
            }, label = {
                Text(text = stringResource(id = R.string.label_login_password))
            }, visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }, trailingIcon = {
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = stringResource(id = R.string.show_password_content_desc)
                        )
                    }
                } else {
                    IconButton(onClick = { showPassword = true }) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = stringResource(id = R.string.hide_password_content_desc)
                        )
                    }
                }
            }, keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ), modifier = Modifier.fillMaxWidth(), isError = uiState.passwordError != null
            )

            uiState.passwordError?.let {
                AppErrorText(
                    error = it, modifier = Modifier.fillMaxWidth(), align = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                Text(
                    fontFamily = soraFamily, text = buildAnnotatedString {
                        append(stringResource(id = R.string.label_login_forgot_password))
                        addStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                textDecoration = TextDecoration.Underline
                            ), start = 0, end = length
                        )
                    }, modifier = Modifier.clickable(onClick = onForgotPasswordTap)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                contentAlignment = Alignment.Center
            ) {
                CofeeButton(
                    modifier = modifier.fillMaxSize(),
                    isEnabled = uiState.loading.not(),
                    buttonTitle = stringResource(id = R.string.label_login_login),
                    onTap = {
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                        viewModel.onEvent(
                            LoginUIEvents.OnLogin(
                                emptyEmailError = context.getString(R.string.empty_email_error),
                                emailError = context.getString(R.string.email_error),
                                emptyPasswordError = context.getString(R.string.empty_password_error),
                                passwordError = context.getString(R.string.password_error),
                            )
                        )
                    },
                )
                if (uiState.loading) {
                    CircularProgressIndicator()
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.label_login_new_member),
                    style = TextStyle(color = textGrayColor),
                    fontFamily = soraFamily,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(id = R.string.label_login_register),
                    style = TextStyle(color = textTitleColor),
                    fontFamily = soraFamily,
                    modifier = Modifier.clickable(onClick = onRegisterTap)
                )
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    CofeeShopTheme {
        LoginScreen(
            modifier = Modifier.fillMaxSize(),
            onLoginSuccess = {}, onForgotPasswordTap = {},
            onRegisterTap = {},
        )
    }
}