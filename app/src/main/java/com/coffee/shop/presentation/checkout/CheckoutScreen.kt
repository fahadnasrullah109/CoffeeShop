package com.coffee.shop.presentation.checkout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cofee.shop.R
import com.coffee.shop.components.CoffeeButton
import com.coffee.shop.domain.models.DomainOrderForPlacement
import com.coffee.shop.theme.CoffeeShopTheme
import com.coffee.shop.theme.appBgColor
import com.coffee.shop.theme.soraFamily
import com.coffee.shop.theme.textGrayColor
import com.coffee.shop.theme.textTitleColor
import com.coffee.shop.utils.getDummyOrderForPlacement

data class PaymentOption(val resourceId: Int, val accountNumber: String = "*********2109")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    order: DomainOrderForPlacement,
    onBack: () -> Unit,
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(modifier = modifier, containerColor = appBgColor, topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
        ), title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.SemiBold,
                text = stringResource(id = R.string.label_checkout),
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
            DetailContent(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                order = order
            )
            ContinueRow(
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun DetailContent(
    modifier: Modifier, order: DomainOrderForPlacement
) {
    val options = listOf(
        PaymentOption(resourceId = R.drawable.ic_visa),
        PaymentOption(resourceId = R.drawable.ic_paypal),
        PaymentOption(resourceId = R.drawable.ic_mastercard),
        PaymentOption(resourceId = R.drawable.ic_g_pay),
        PaymentOption(resourceId = R.drawable.ic_apple_pay)
    )
    var selectedPaymentOption by rememberSaveable {
        mutableIntStateOf(0)
    }
    Column(
        modifier = modifier.padding(20.dp)
    ) {
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
                text = "$ ${order.orderPrice}",
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
                text = "$ ${order.shipping}",
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
                text = stringResource(id = R.string.label_total_payment),
                style = TextStyle(color = textTitleColor, fontSize = 14.sp),
                fontFamily = soraFamily
            )
            Text(
                text = "$ ${order.totalPrice}",
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

        Text(
            text = stringResource(id = R.string.label_payment),
            style = TextStyle(color = textTitleColor, fontSize = 18.sp),
            fontFamily = soraFamily,
            fontWeight = FontWeight.SemiBold
        )

        options.forEachIndexed { index, paymentOption ->
            Spacer(modifier = Modifier.height(10.dp))
            PaymentOptionRow(
                modifier = Modifier.fillMaxWidth(),
                isSelected = index == selectedPaymentOption,
                option = paymentOption,
                optionId = index
            ) {
                if (selectedPaymentOption != it) {
                    selectedPaymentOption = it
                }
            }
        }


    }
}

@Preview
@Composable
private fun DetailContentPreview() {
    CoffeeShopTheme {
        DetailContent(
            modifier = Modifier.fillMaxSize(),
            order = getDummyOrderForPlacement(),
        )
    }
}

@Composable
private fun ContinueRow(modifier: Modifier) {
    Column(
        modifier = modifier
            .background(
                color = Color.White, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(20.dp)
    ) {
        CoffeeButton(modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
            buttonTitle = stringResource(id = R.string.label_continue),
            onTap = { })
    }
}

@Preview
@Composable
private fun ContinueRowPreview() {
    CoffeeShopTheme {
        ContinueRow(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun PaymentOptionRow(
    modifier: Modifier,
    isSelected: Boolean,
    option: PaymentOption,
    optionId: Int,
    onOptionSelected: (Int) -> Unit
) {
    Card(
        modifier = modifier, onClick = {
            onOptionSelected.invoke(optionId)
        }, colors = CardDefaults.cardColors(
            containerColor = Color.White
        ), border = if (isSelected) BorderStroke(
            width = 1.dp, color = MaterialTheme.colorScheme.primary
        ) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(50.dp),
                imageVector = ImageVector.vectorResource(id = option.resourceId),
                contentDescription = null
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Text(
                text = option.accountNumber,
                style = TextStyle(color = textGrayColor, fontSize = 18.sp),
                fontFamily = soraFamily
            )
        }
    }
}

@Preview
@Composable
private fun PaymentOptionRowPreview() {
    CoffeeShopTheme {
        PaymentOptionRow(modifier = Modifier.fillMaxWidth(),
            isSelected = true,
            optionId = 0,
            option = PaymentOption(R.drawable.ic_heart_selected),
            onOptionSelected = {})
    }
}