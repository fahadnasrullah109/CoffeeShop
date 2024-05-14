package com.coffee.shop.presentation.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.coffee.shop.components.EmptyView
import com.coffee.shop.components.Loading
import com.coffee.shop.domain.models.DomainNotification
import com.coffee.shop.theme.CoffeeShopTheme
import com.coffee.shop.theme.appBgColor
import com.coffee.shop.theme.soraFamily
import com.coffee.shop.theme.textGrayColor
import com.coffee.shop.theme.textHomeGrayColor
import com.coffee.shop.theme.textTitleColor
import com.coffee.shop.utils.getDummyDomainNotification

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(modifier: Modifier, viewModel: NotificationsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(modifier = modifier, containerColor = appBgColor, topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
        ), title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.SemiBold,
                text = stringResource(id = R.string.label_notifications),
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
            uiState.data?.let { notifications ->
                LazyColumn(
                    modifier = Modifier
                        .padding(contentPadding)
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    itemsIndexed(items = notifications, key = { index, notification ->
                        "${notification.title}$index}"
                    }) { _, notification ->
                        NotificationItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            notification = notification
                        )
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
private fun NotificationItem(modifier: Modifier, notification: DomainNotification) {
    Card(modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = {}) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    tint = Color.White,
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_notification),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Text(
                    text = notification.title,
                    style = TextStyle(color = textTitleColor, fontSize = 18.sp),
                    fontFamily = soraFamily,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = notification.date,
                    style = TextStyle(color = textHomeGrayColor, fontSize = 14.sp),
                    fontFamily = soraFamily,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = notification.message,
                    style = TextStyle(color = textGrayColor, fontSize = 16.sp),
                    fontFamily = soraFamily,
                )
            }
        }
    }
}

@Preview
@Composable
private fun NotificationItemPreview() {
    CoffeeShopTheme {
        NotificationItem(
            modifier = Modifier.fillMaxWidth(), notification = getDummyDomainNotification()
        )
    }
}