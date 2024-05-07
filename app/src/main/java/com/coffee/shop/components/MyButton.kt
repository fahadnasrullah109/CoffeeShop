package com.coffee.shop.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coffee.shop.theme.CofeeShopTheme
import com.coffee.shop.theme.soraFamily

@Composable
fun CofeeButton(
    modifier: Modifier = Modifier, buttonTitle: String, onTap: () -> Unit, isEnabled: Boolean = true
) {
    Button(
        enabled = isEnabled,
        onClick = onTap,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(12.dp))
    ) {
        Text(
            text = buttonTitle,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                fontFamily = soraFamily
            ),
        )
    }
}

@Preview
@Composable
private fun CofeeButtonPreview() {
    CofeeShopTheme {
        CofeeButton(
            buttonTitle = "GetStarted",
            onTap = {},
        )
    }
}