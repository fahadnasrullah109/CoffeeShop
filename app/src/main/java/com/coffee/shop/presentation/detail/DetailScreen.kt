package com.coffee.shop.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cofee.shop.R
import com.coffee.shop.components.CoffeeButton
import com.coffee.shop.domain.models.DomainCoffee
import com.coffee.shop.theme.CoffeeShopTheme
import com.coffee.shop.theme.appBgColor
import com.coffee.shop.theme.ratingColor
import com.coffee.shop.theme.soraFamily
import com.coffee.shop.theme.textGrayColor
import com.coffee.shop.theme.textHomeGrayColor
import com.coffee.shop.theme.textTitleColor
import com.coffee.shop.utils.AppConstant
import com.coffee.shop.utils.getDummyDomainCoffee

@Composable
fun DetailScreen(modifier: Modifier = Modifier, coffee: DomainCoffee, onBack: () -> Unit) {
    Scaffold(modifier = modifier,
        containerColor = appBgColor, topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    fontWeight = FontWeight.SemiBold,
                    text = stringResource(id = R.string.label_detail),
                    style = TextStyle(color = textTitleColor, fontSize = 18.sp),
                    fontFamily = soraFamily
                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_heart),
                        contentDescription = null
                    )
                }
            }
        }) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            DetailContent(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()), coffee = coffee
            )
            BuyNowRow(modifier = Modifier.fillMaxWidth(), coffee = coffee)
        }
    }
}

@Composable
private fun DetailContent(modifier: Modifier, coffee: DomainCoffee) {
    Column(
        modifier = modifier.padding(20.dp)
    ) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2.0f)
                .clip(RoundedCornerShape(16.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data("${AppConstant.GET_IMAGES_URL}${coffee.image}")
                .crossfade(true).build(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = coffee.title,
            style = TextStyle(color = textTitleColor, fontSize = 18.sp),
            fontFamily = soraFamily,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = coffee.description,
            style = TextStyle(color = textHomeGrayColor, fontSize = 14.sp),
            fontFamily = soraFamily
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                tint = ratingColor,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_rating),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${coffee.rating}",
                style = TextStyle(color = textTitleColor, fontSize = 18.sp),
                fontFamily = soraFamily,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(all = 20.dp)
            .height(1.dp)
            .background(color = Color(0xFFEAEAEA)))
        Text(
            text = stringResource(id = R.string.label_description),
            style = TextStyle(color = textTitleColor, fontSize = 18.sp),
            fontFamily = soraFamily,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = coffee.detail,
            style = TextStyle(color = textHomeGrayColor, fontSize = 14.sp),
            fontFamily = soraFamily
        )
    }
}

@Composable
private fun BuyNowRow(modifier: Modifier, coffee: DomainCoffee) {
    Row(
        modifier = modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .weight(.4f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.label_price),
                style = TextStyle(color = textGrayColor),
                fontFamily = soraFamily
            )
            Text(
                text = "$ ${coffee.priceSmall}",
                fontSize = 25.sp,
                style = TextStyle(color = MaterialTheme.colorScheme.primary),
                fontWeight = FontWeight.SemiBold,
                fontFamily = soraFamily
            )
        }

        CoffeeButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .weight(.6f),
            buttonTitle = stringResource(id = R.string.label_buy_now), onTap = { })
        Spacer(modifier = Modifier.width(20.dp))
    }
}

@Preview
@Composable
private fun DetailScreenPreview() {
    CoffeeShopTheme {
        DetailScreen(
            modifier = Modifier.fillMaxSize(),
            coffee = getDummyDomainCoffee(),
            onBack = {})
    }
}
