package com.coffee.shop.presentation.authentication.forgotpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cofee.shop.R
import com.coffee.shop.components.AppErrorText
import com.coffee.shop.components.CoffeeButton
import com.coffee.shop.theme.CoffeeShopTheme
import com.coffee.shop.theme.soraFamily
import com.coffee.shop.theme.textGrayColor
import com.coffee.shop.theme.textTitleColor
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier, viewModel: ForgotPasswordViewModel, onEmailSent: () -> Unit,
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
                if (uiState.emailSentSuccess) {
                    onEmailSent.invoke()
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
                text = stringResource(id = R.string.label_forgot_password_title),
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
                text = stringResource(id = R.string.label_forgot_password_description),
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
                viewModel.onEvent(ForgotPasswordUIEvents.OnEmailChanged(email = it))
            }, label = {
                Text(text = stringResource(id = R.string.label_login_email_address))
            }, keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
            ), modifier = Modifier.fillMaxWidth(), isError = uiState.emailError != null
            )

            uiState.emailError?.let {
                AppErrorText(
                    error = it, modifier = Modifier.fillMaxWidth(), align = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                contentAlignment = Alignment.Center
            ) {
                CoffeeButton(
                    modifier = modifier.fillMaxSize(),
                    isEnabled = uiState.loading.not(),
                    buttonTitle = stringResource(id = R.string.label_done),
                    onTap = {
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                        viewModel.onEvent(
                            ForgotPasswordUIEvents.OnForgotPassword(
                                emptyEmailError = context.getString(R.string.empty_email_error),
                                emailError = context.getString(R.string.email_error)
                            )
                        )
                    },
                )
                if (uiState.loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Preview
@Composable
private fun ForgotPasswordScreenPreview() {
    CoffeeShopTheme {
        ForgotPasswordScreen(
            modifier = Modifier.fillMaxSize(),
            viewModel = hiltViewModel(),
            onEmailSent = {},
        )
    }
}