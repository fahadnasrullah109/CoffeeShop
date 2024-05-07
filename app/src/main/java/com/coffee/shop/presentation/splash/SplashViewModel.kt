package com.coffee.shop.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffee.shop.data.onSuccess
import com.coffee.shop.domain.usecases.GetLoggedInUserUseCase
import com.coffee.shop.domain.usecases.ShowIntroductionDecisionUseCase
import com.coffee.shop.navigation.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val introductionDecisionUseCase: ShowIntroductionDecisionUseCase,
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState = _uiState.asStateFlow()

    private var shouldShowIntroduction = false
    private var isUserLoggedIn = false

    init {
        handleSplashStarted()
    }

    private fun handleSplashStarted() {
        viewModelScope.launch {
            viewModelScope.launch {
                val splashTimeoutDeferred = async { startSplashTimeout() }
                val introScreenDeferred = async { verifyIntroScreen() }
                val loggedInUserDeferred = async { verifyLoggedInUser() }

                splashTimeoutDeferred.await()
                introScreenDeferred.await()
                loggedInUserDeferred.await()

                if (shouldShowIntroduction) {
                    _uiState.value = _uiState.value.copy(
                        route = Destinations.Introduction.route
                    )
                } else if (isUserLoggedIn) {
                    _uiState.value = _uiState.value.copy(
                        route = Destinations.Dashboard.route
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        route = Destinations.Login.route
                    )
                }
            }
        }
    }

    private suspend fun startSplashTimeout() {
        // Simulate a 3-second timeout
        delay(3000)
    }

    private suspend fun verifyIntroScreen() {
        viewModelScope.launch {
            introductionDecisionUseCase.execute(Unit).collect { dataResource ->
                dataResource.onSuccess {
                    shouldShowIntroduction = this.data
                }
            }
        }
    }

    private suspend fun verifyLoggedInUser() {
        viewModelScope.launch {
            getLoggedInUserUseCase.execute(Unit).collect { dataResource ->
                dataResource.onSuccess {
                    this.data?.let {
                        isUserLoggedIn = false
                    }
                }
            }
        }
    }
}