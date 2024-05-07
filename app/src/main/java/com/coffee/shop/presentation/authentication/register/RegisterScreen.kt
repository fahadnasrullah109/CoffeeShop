package com.coffee.shop.presentation.authentication.register

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
import androidx.compose.material.icons.filled.Person
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cofee.shop.R
import com.coffee.shop.components.AppErrorText
import com.coffee.shop.components.CofeeButton
import com.coffee.shop.theme.CofeeShopTheme
import com.coffee.shop.theme.soraFamily
import com.coffee.shop.theme.textGrayColor
import com.coffee.shop.theme.textTitleColor
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit,
    onLoginTap: () -> Unit,
    onTermsTap: () -> Unit
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
        var showConfirmPassword by remember { mutableStateOf(value = false) }
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
                if (uiState.registerSuccess) {
                    onRegisterSuccess.invoke()
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
                text = stringResource(id = R.string.label_register_title),
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
                text = stringResource(id = R.string.label_register_description),
                fontFamily = soraFamily,
                style = TextStyle(color = textGrayColor)
            )

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = uiState.email, leadingIcon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = stringResource(id = R.string.username_content_desc)
                )
            }, onValueChange = {
                viewModel.onEvent(RegisterUIEvents.OnEmailChanged(email = it))
            }, label = {
                Text(text = stringResource(id = R.string.label_register_username))
            }, keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ), modifier = Modifier.fillMaxWidth(), isError = uiState.usernameError != null
            )

            uiState.usernameError?.let {
                AppErrorText(
                    error = it, modifier = Modifier.fillMaxWidth(), align = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = uiState.email, leadingIcon = {
                Icon(
                    Icons.Filled.Email,
                    contentDescription = stringResource(id = R.string.email_content_desc)
                )
            }, onValueChange = {
                viewModel.onEvent(RegisterUIEvents.OnEmailChanged(email = it))
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
                viewModel.onEvent(RegisterUIEvents.OnPasswordChanged(password = it))
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

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.password,
                leadingIcon = {
                    Icon(
                        Icons.Filled.Lock,
                        contentDescription = stringResource(id = R.string.email_content_desc)
                    )
                },
                onValueChange = {
                    viewModel.onEvent(RegisterUIEvents.OnPasswordChanged(password = it))
                },
                label = {
                    Text(text = stringResource(id = R.string.label_register_confirm_password))
                },
                visualTransformation = if (showConfirmPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    if (showConfirmPassword) {
                        IconButton(onClick = { showConfirmPassword = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = stringResource(id = R.string.show_password_content_desc)
                            )
                        }
                    } else {
                        IconButton(onClick = { showConfirmPassword = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = stringResource(id = R.string.hide_password_content_desc)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.confirmPasswordError != null
            )

            uiState.confirmPasswordError?.let {
                AppErrorText(
                    error = it, modifier = Modifier.fillMaxWidth(), align = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                textAlign = TextAlign.Center,
                fontFamily = soraFamily,
                color = MaterialTheme.colorScheme.primary,
                text = stringResource(id = R.string.label_register_terms_prefix),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                textAlign = TextAlign.Center, fontFamily = soraFamily, text = buildAnnotatedString {
                    append(stringResource(id = R.string.label_register_terms))
                    addStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        ), start = 0, end = length
                    )
                }, modifier = Modifier
                    .clickable(onClick = onTermsTap)
            )

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
                    buttonTitle = stringResource(id = R.string.label_register_register),
                    onTap = {
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                        viewModel.onEvent(
                            RegisterUIEvents.OnRegister(
                                emptyUsernameError = context.getString(R.string.empty_username_error),
                                usernameError = context.getString(R.string.username_error),
                                emptyEmailError = context.getString(R.string.empty_email_error),
                                emailError = context.getString(R.string.email_error),
                                emptyPasswordError = context.getString(R.string.empty_password_error),
                                passwordError = context.getString(R.string.password_error),
                                emptyConfirmPasswordError = context.getString(R.string.empty_confirm_password_error),
                                confirmPasswordError = context.getString(R.string.confirm_password_error),
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
                    text = stringResource(id = R.string.label_register_already_member),
                    style = TextStyle(color = textGrayColor),
                    fontFamily = soraFamily,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(id = R.string.label_register_login),
                    style = TextStyle(color = textTitleColor),
                    fontFamily = soraFamily,
                    modifier = Modifier.clickable(onClick = onLoginTap)
                )
            }
        }
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    CofeeShopTheme {
        RegisterScreen(modifier = Modifier.fillMaxSize(),
            viewModel = hiltViewModel(),
            onRegisterSuccess = {},
            onLoginTap = {},
            onTermsTap = {})
    }
}