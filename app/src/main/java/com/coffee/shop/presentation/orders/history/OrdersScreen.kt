package com.coffee.shop.presentation.orders.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cofee.shop.R
import com.coffee.shop.components.EmptyView
import com.coffee.shop.components.Loading
import com.coffee.shop.domain.models.DomainOrder
import com.coffee.shop.theme.CoffeeShopTheme
import com.coffee.shop.theme.appBgColor
import com.coffee.shop.theme.ratingColor
import com.coffee.shop.theme.soraFamily
import com.coffee.shop.theme.textHomeGrayColor
import com.coffee.shop.theme.textTitleColor
import com.coffee.shop.utils.AppConstant
import com.coffee.shop.utils.getDummyDomainOrder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(modifier: Modifier, viewModel: OrdersViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(modifier = modifier, containerColor = appBgColor, topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
            ),
            title = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.SemiBold,
                    text = stringResource(id = R.string.label_order_history),
                    style = TextStyle(color = textTitleColor, fontSize = 18.sp),
                    fontFamily = soraFamily
                )
            })
    }) { contentPadding ->
        if (uiState.loading) {
            Loading(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            )
        } else {
            uiState.data?.let { orders ->
                LazyColumn(
                    modifier = Modifier
                        .padding(contentPadding)
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    itemsIndexed(items = orders, key = { index, order ->
                        "${order.title}$index}"
                    }) { index, order ->
                        OrderItem(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp), order = order, onReOrder = {
                            viewModel.onEvent(OrdersUIEvents.OnReorder)
                        })
                    }
                }
            } ?: run {
                EmptyView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                )
            }
        }
    }
}

@Composable
private fun OrderItem(modifier: Modifier, order: DomainOrder, onReOrder: () -> Unit) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = {}
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${AppConstant.GET_IMAGES_URL}${order.image}").crossfade(true).build(),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        minLines = 1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = order.title,
                        style = TextStyle(color = textTitleColor, fontSize = 18.sp),
                        fontFamily = soraFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = order.date,
                        style = TextStyle(color = textHomeGrayColor, fontSize = 14.sp),
                        fontFamily = soraFamily,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    minLines = 1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = order.description,
                    style = TextStyle(color = textHomeGrayColor, fontSize = 16.sp),
                    fontFamily = soraFamily,
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
                        text = "${order.rating}",
                        style = TextStyle(color = textTitleColor, fontSize = 18.sp),
                        fontFamily = soraFamily,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        text = "$ ${order.price} / ${order.quantity}",
                        style = TextStyle(color = MaterialTheme.colorScheme.primary, fontSize = 18.sp),
                        fontFamily = soraFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                    OutlinedButton(
                        onClick = onReOrder
                    ) {
                        Text(
                            text = stringResource(id = R.string.label_reorder),
                            style = TextStyle(color = MaterialTheme.colorScheme.primary, fontSize = 18.sp),
                            fontFamily = soraFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun OrderItemPreview() {
    CoffeeShopTheme {
        OrderItem(modifier = Modifier.fillMaxWidth(), order = getDummyDomainOrder(), onReOrder = {})
    }
}