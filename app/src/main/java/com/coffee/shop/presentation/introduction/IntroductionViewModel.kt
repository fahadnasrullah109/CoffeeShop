package com.coffee.shop.presentation.introduction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffee.shop.domain.usecases.MarkIntroductionShownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val markIntroductionShownUseCase: MarkIntroductionShownUseCase
) : ViewModel() {

    fun onEvent(event: IntroductionUIEvents) {
        viewModelScope.launch {
            when (event) {
                is IntroductionUIEvents.OnIntroductionShown -> {
                    markIntroductionShown()
                }
            }
        }
    }


    private fun markIntroductionShown() {
        viewModelScope.launch {
            markIntroductionShownUseCase.execute(Unit).collect {}
        }
    }
}