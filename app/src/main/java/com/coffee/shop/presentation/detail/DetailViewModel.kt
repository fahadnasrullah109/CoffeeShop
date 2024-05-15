package com.coffee.shop.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffee.shop.data.onError
import com.coffee.shop.data.onSuccess
import com.coffee.shop.domain.models.DomainCoffee
import com.coffee.shop.domain.usecases.DeleteFavouriteUseCase
import com.coffee.shop.domain.usecases.IsFavouriteUseCase
import com.coffee.shop.domain.usecases.SaveFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val saveFavouriteUseCase: SaveFavouriteUseCase,
    private val deleteFavouriteUseCase: DeleteFavouriteUseCase,
    private val isFavouriteUseCase: IsFavouriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: DetailUIEvents) {
        viewModelScope.launch {
            when (event) {
                is DetailUIEvents.IsFavourite -> {
                    isFavourite(event.coffeeId)
                }

                is DetailUIEvents.OnFavouriteCoffee -> {
                    favouriteCoffee(event.coffee)
                }

                is DetailUIEvents.OnUnFavouriteCoffee -> {
                    unFavouriteCoffee(event.coffeeId)
                }

                is DetailUIEvents.Reset -> {
                    resetStates()
                }
            }
        }
    }

    private fun resetStates() {
        _uiState.value = _uiState.value.copy(
            favouriteEventSuccess = false, unFavouriteEventSuccess = false, error = null
        )
    }

    private fun isFavourite(coffeeId: String) {
        viewModelScope.launch {
            isFavouriteUseCase.execute(coffeeId).collect { dataResource ->
                dataResource.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isFavourite = this.data
                    )
                }.onError {
                    _uiState.value = _uiState.value.copy(error = this.exception.message)
                }
            }
        }
    }

    private fun favouriteCoffee(coffee: DomainCoffee) {
        viewModelScope.launch {
            saveFavouriteUseCase.execute(coffee).collect { dataResource ->
                dataResource.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        favouriteEventSuccess = this.data,
                        isFavourite = true
                    )
                }.onError {
                    _uiState.value = _uiState.value.copy(error = this.exception.message)
                }
            }
        }
    }

    private fun unFavouriteCoffee(coffeeId: String) {
        viewModelScope.launch {
            deleteFavouriteUseCase.execute(coffeeId).collect { dataResource ->
                dataResource.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        unFavouriteEventSuccess = this.data,
                        isFavourite = false
                    )
                }.onError {
                    _uiState.value = _uiState.value.copy(error = this.exception.message)
                }
            }
        }
    }
}