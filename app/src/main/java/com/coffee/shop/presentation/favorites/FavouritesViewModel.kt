package com.coffee.shop.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffee.shop.data.onError
import com.coffee.shop.data.onSuccess
import com.coffee.shop.domain.usecases.LoadFavouritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val loadFavouritesUseCase: LoadFavouritesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavouritesUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: FavouritesUIEvents)  = when(event) {
        is FavouritesUIEvents.LoadData -> {
            loadFavourites()
        }
    }

    private fun loadFavourites() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                loading = true
            )
            loadFavouritesUseCase.execute(Unit).collect { dataResource ->
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