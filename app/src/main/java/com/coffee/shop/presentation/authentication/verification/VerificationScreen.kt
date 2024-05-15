package com.coffee.shop.presentation.authentication.verification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.composeuisuite.ohteepee.OhTeePeeInput
import com.composeuisuite.ohteepee.configuration.OhTeePeeCellConfiguration
import com.composeuisuite.ohteepee.configuration.OhTeePeeConfigurations
import kotlinx.coroutines.launch

@Composable
fun VerificationScreen(
    modifier: Modifier = Modifier, viewModel: VerificationViewModel, onOTPVerified: () -> Unit,
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
                if (uiState.verificationSuccess) {
                    onOTPVerified.invoke()
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
                text = stringResource(id = R.string.label_verification_title),
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
                text = stringResource(id = R.string.label_verification_description),
                fontFamily = soraFamily,
                style = TextStyle(color = textGrayColor)
            )
            Spacer(modifier = Modifier.height(20.dp))


            OhTeePeeInput(
                value = uiState.otp,
                onValueChange = { newValue, _ ->
                    viewModel.onEvent(VerificationUIEvents.OnOTPChanged(newValue))
                },
                configurations = OhTeePeeConfigurations.withDefaults(
                    cellsCount = 4,
                    emptyCellConfig = OhTeePeeCellConfiguration.withDefaults(
                        borderColor = textGrayColor,
                        borderWidth = 1.dp,
                        shape = RoundedCornerShape(16.dp),
                        textStyle = TextStyle(
                            color = Color.Black
                        )
                    ),
                    cellModifier = Modifier
                        .padding(horizontal = 4.dp)
                        .width(48.dp).height(60.dp),
                    activeCellConfig = OhTeePeeCellConfiguration.withDefaults(
                        borderColor = MaterialTheme.colorScheme.primary,
                        borderWidth = 1.dp,
                        shape = RoundedCornerShape(16.dp),
                        textStyle = TextStyle(
                            color = Color.Black
                        )
                    )
                ),
            )

            Spacer(modifier = Modifier.height(10.dp))
            
            uiState.otpError?.let {
                AppErrorText(
                    error = it, modifier = Modifier.fillMaxWidth(), align = TextAlign.Center
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
                    buttonTitle = stringResource(id = R.string.label_verify),
                    onTap = {
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                        viewModel.onEvent(
                            VerificationUIEvents.OnConfirmOTP(
                                emptyOTPError = context.getString(R.string.empty_otp_error),
                                otpError = context.getString(R.string.otp_error)
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
        VerificationScreen(
            modifier = Modifier.fillMaxSize(),
            viewModel = hiltViewModel(),
            onOTPVerified = {},
        )
    }
}