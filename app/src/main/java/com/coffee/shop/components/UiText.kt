package com.coffee.shop.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class Dynamic(val value: String) : UiText()
    class Resource(@StringRes val id: Int, vararg val args: Any) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is Dynamic -> value
            is Resource -> stringResource(id = id, formatArgs = args)
        }
    }
}