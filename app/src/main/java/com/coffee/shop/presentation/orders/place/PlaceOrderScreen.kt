package com.coffee.shop.presentation.orders.place

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cofee.shop.R
import com.coffee.shop.components.CoffeeButton
import com.coffee.shop.domain.models.DomainCoffee
import com.coffee.shop.theme.CoffeeShopTheme
import com.coffee.shop.theme.appBgColor
import com.coffee.shop.theme.soraFamily
import com.coffee.shop.theme.textHomeGrayColor
import com.coffee.shop.theme.textTitleColor
import com.coffee.shop.utils.AppConstant
import com.coffee.shop.utils.getDummyDomainCoffee

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceOrderScreen(
    modifier: Modifier = Modifier,
    coffee: DomainCoffee,
    onBack: () -> Unit,
    viewModel: PlaceOrderViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(PlaceOrderUIEvents.OnCalculateTotal(coffee.priceSmall))
    }
    Scaffold(modifier = modifier, containerColor = appBgColor, topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
        ), title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.SemiBold,
                text = stringResource(id = R.string.label_order),
                style = TextStyle(color = textTitleColor, fontSize = 18.sp),
                fontFamily = soraFamily
            )
        }, navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
            }
        })
    }) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            DetailContent(modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
                coffee = coffee,
                quantity = uiState.selectedQuantity,
                address = uiState.deliveryAddress,
                deliveryFee = uiState.deliveryCharges,
                totalPayment = "%.2f".format(uiState.totalAmount),
                onQuantityIncreased = {
                    viewModel.onEvent(
                        PlaceOrderUIEvents.OnQuantityUpdated(
                            quantity = uiState.selectedQuantity + 1, unitPrice = coffee.priceSmall
                        )
                    )
                },
                onQuantityDecreased = {
                    viewModel.onEvent(
                        PlaceOrderUIEvents.OnQuantityUpdated(
                            quantity = uiState.selectedQuantity - 1, unitPrice = coffee.priceSmall
                        )
                    )
                })
            TotalRow(
                modifier = Modifier.fillMaxWidth(), amount = "%.2f".format(uiState.totalAmount)
            )
        }
    }
}

@Composable
private fun DetailContent(
    modifier: Modifier,
    coffee: DomainCoffee,
    quantity: Int,
    address: String,
    deliveryFee: Int,
    totalPayment: String,
    onQuantityIncreased: () -> Unit,
    onQuantityDecreased: () -> Unit
) {
    Column(
        modifier = modifier.padding(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.label_address),
            style = TextStyle(color = textTitleColor, fontSize = 18.sp),
            fontFamily = soraFamily,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = address,
            style = TextStyle(color = textHomeGrayColor, fontSize = 14.sp),
            fontFamily = soraFamily
        )
        Spacer(modifier = Modifier.height(10.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Color(0xFFEAEAEA))
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${AppConstant.GET_IMAGES_URL}${coffee.image}").crossfade(true).build(),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Text(
                    text = coffee.title,
                    style = TextStyle(color = textTitleColor, fontSize = 14.sp),
                    fontFamily = soraFamily,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = coffee.description,
                    style = TextStyle(color = textHomeGrayColor, fontSize = 12.sp),
                    fontFamily = soraFamily
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(onClick = onQuantityDecreased) {
                    Text(text = "-")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "$quantity",
                    style = TextStyle(color = textTitleColor, fontSize = 20.sp),
                    fontFamily = soraFamily,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedButton(onClick = onQuantityIncreased) {
                    Text(text = "+")
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Color(0xFFEAEAEA))
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.label_summary),
            style = TextStyle(color = textTitleColor, fontSize = 18.sp),
            fontFamily = soraFamily,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(id = R.string.label_price),
                style = TextStyle(color = textTitleColor, fontSize = 14.sp),
                fontFamily = soraFamily
            )
            Text(
                text = "$ ${coffee.priceSmall}",
                style = TextStyle(color = textTitleColor, fontSize = 14.sp),
                fontFamily = soraFamily,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(id = R.string.label_delivery_fee),
                style = TextStyle(color = textTitleColor, fontSize = 14.sp),
                fontFamily = soraFamily
            )
            Text(
                text = "$ $deliveryFee",
                style = TextStyle(color = textTitleColor, fontSize = 14.sp),
                fontFamily = soraFamily,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Color(0xFFEAEAEA))
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(id = R.string.label_total_payment),
                style = TextStyle(color = textTitleColor, fontSize = 14.sp),
                fontFamily = soraFamily
            )
            Text(
                text = "$ $totalPayment",
                style = TextStyle(color = textTitleColor, fontSize = 14.sp),
                fontFamily = soraFamily,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview
@Composable
private fun DetailContentPreview() {
    CoffeeShopTheme {
        DetailContent(modifier = Modifier.fillMaxSize(),
            coffee = getDummyDomainCoffee(),
            quantity = 1,
            address = "My Address goes here",
            deliveryFee = 10,
            totalPayment = "50.0",
            onQuantityIncreased = {},
            onQuantityDecreased = {})
    }
}

@Composable
private fun TotalRow(modifier: Modifier, amount: String) {
    Column(
        modifier = modifier
            .background(
                color = Color.White, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(20.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "$ $amount",
            fontSize = 30.sp,
            style = TextStyle(color = MaterialTheme.colorScheme.primary),
            fontWeight = FontWeight.SemiBold,
            fontFamily = soraFamily
        )
        Spacer(modifier = Modifier.height(20.dp))

        CoffeeButton(modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
            buttonTitle = stringResource(id = R.string.label_order),
            onTap = { })
    }
}

@Preview
@Composable
private fun TotalRowPreview() {
    CoffeeShopTheme {
        TotalRow(modifier = Modifier.fillMaxWidth(), amount = "50.3")
    }
}