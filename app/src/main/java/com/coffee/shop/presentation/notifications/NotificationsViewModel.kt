package com.coffee.shop.presentation.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffee.shop.data.onError
import com.coffee.shop.data.onSuccess
import com.coffee.shop.domain.usecases.LoadNotificationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val loadNotificationsUseCase: LoadNotificationsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                loading = true
            )
            loadNotificationsUseCase.execute(Unit).collect { dataResource ->
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