package com.coffee.shop.presentation.introduction

sealed interface IntroductionUIEvents {
    data object OnIntroductionShown : IntroductionUIEvents
}