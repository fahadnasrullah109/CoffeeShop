package com.coffee.shop.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.coffee.shop.theme.soraFamily
import com.coffee.shop.theme.textTitleColor

@Composable
fun AppTextField(error: UiText, modifier: Modifier, align: TextAlign) {
    Text(
        text = error.asString(),
        style = TextStyle(color = textTitleColor),
        fontFamily = soraFamily,
        fontWeight = FontWeight.Normal,
        modifier = modifier.padding(top = 4.dp),
        textAlign = align
    )
}