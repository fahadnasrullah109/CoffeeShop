package com.coffee.shop.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.cofee.shop.R
import com.coffee.shop.theme.CoffeeShopTheme
import com.coffee.shop.theme.soraFamily
import com.coffee.shop.theme.textHomeGrayColor

@Composable
fun EmptyView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.label_no_data),
            style = TextStyle(color = textHomeGrayColor, fontSize = 15.sp),
            fontFamily = soraFamily,
        )
    }
}


@Preview
@Composable
private fun LoadingPreview() {
    CoffeeShopTheme {
        EmptyView(modifier = Modifier.fillMaxSize())
    }
}