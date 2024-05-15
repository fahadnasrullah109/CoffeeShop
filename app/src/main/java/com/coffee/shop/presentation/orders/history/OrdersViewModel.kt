package com.coffee.shop.presentation.orders.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffee.shop.data.onError
import com.coffee.shop.data.onSuccess
import com.coffee.shop.domain.usecases.LoadOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val loadOrdersUseCase: LoadOrdersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrdersUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadOrdersHistory()
    }

    fun onEvent(event: OrdersUIEvents) {
        viewModelScope.launch {
            when (event) {
                is OrdersUIEvents.OnReorder -> {}
            }
        }
    }

    private fun loadOrdersHistory() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                loading = true
            )
            loadOrdersUseCase.execute(Unit).collect { dataResource ->
                dataResource.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        data = this.data,
                        loading = false
                    )
                }.onError {
                    _uiState.value =
                        _uiState.value.copy(error = this.exception.message, loading = false)
                }
            }
        }
    }
}