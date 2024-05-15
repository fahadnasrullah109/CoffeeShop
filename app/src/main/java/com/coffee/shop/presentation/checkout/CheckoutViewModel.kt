package com.coffee.shop.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutUiState)
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: CheckoutUIEvents) {
        viewModelScope.launch {
            when (event) {
                is CheckoutUIEvents.OnCheckout -> {}
            }
        }
    }
}