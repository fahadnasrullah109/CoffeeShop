package com.coffee.shop.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffee.shop.data.onError
import com.coffee.shop.data.onSuccess
import com.coffee.shop.domain.usecases.HomeDataLoadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeDataLoadUseCase: HomeDataLoadUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadHomeScreenData()
    }

    fun onEvent(event: HomeUIEvents) {
        viewModelScope.launch {
            when (event) {
                is HomeUIEvents.OnSearchTapped -> {}

                is HomeUIEvents.OnItemAddedToCart -> {}
            }
        }
    }

    private fun loadHomeScreenData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                loading = true
            )
            homeDataLoadUseCase.execute(Unit).collect { dataResource ->
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