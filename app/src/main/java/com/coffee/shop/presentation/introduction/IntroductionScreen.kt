package com.coffee.shop.presentation.introduction

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cofee.shop.R
import com.coffee.shop.components.CofeeButton
import com.coffee.shop.theme.CofeeShopTheme
import com.coffee.shop.theme.soraFamily
import com.coffee.shop.theme.textGrayColor

@Composable
fun IntroductionScreen(
    modifier: Modifier = Modifier,
    viewModel: IntroductionViewModel,
    onGetStarted: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(IntroductionUIEvents.OnIntroductionShown)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Image(
            modifier = modifier
                .fillMaxSize()
                .weight(.6f),
            painter = painterResource(id = R.drawable.ic_cofee_onboarding),
            contentDescription = "Coffee Main Image"
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .weight(.4f)
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.Bottom
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                fontSize = 30.sp,
                style = TextStyle(
                    color = Color.White, fontFamily = soraFamily, fontWeight = FontWeight.SemiBold
                ),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.label_introduction_title),
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.White, fontFamily = soraFamily
                ),
                text = stringResource(id = R.string.label_introduction_description),
                color = textGrayColor
            )

            Spacer(modifier = Modifier.height(20.dp))

            CofeeButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                buttonTitle = stringResource(id = R.string.label_get_started),
                onTap = onGetStarted
            )
        }
    }
}

@Preview
@Composable
private fun IntroductionScreenPreview() {
    CofeeShopTheme {
        IntroductionScreen(viewModel = hiltViewModel()) {}
    }
}