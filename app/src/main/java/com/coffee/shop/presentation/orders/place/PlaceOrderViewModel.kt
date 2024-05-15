package com.coffee.shop.presentation.orders.place

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceOrderViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlaceOrderUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: PlaceOrderUIEvents) {
        viewModelScope.launch {
            when (event) {
                is PlaceOrderUIEvents.OnCalculateTotal -> {
                    calculateTotal(unitPrice = event.unitPrice)
                }

                is PlaceOrderUIEvents.OnQuantityUpdated -> {
                    if (event.quantity in 1..10) {
                        _uiState.value = _uiState.value.copy(
                            selectedQuantity = event.quantity
                        )
                    }
                    calculateTotal(unitPrice = event.unitPrice)
                }
            }
        }
    }

    private fun calculateTotal(unitPrice: Double) {
        var total = unitPrice * _uiState.value.selectedQuantity
        total += _uiState.value.deliveryCharges
        _uiState.value = _uiState.value.copy(
            totalAmount = total
        )
    }
}