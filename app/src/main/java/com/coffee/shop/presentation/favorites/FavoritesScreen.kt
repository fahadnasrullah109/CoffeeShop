package com.coffee.shop.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
fun FavoritesScreen(
    modifier: Modifier,
    onCoffeeSelected: (DomainCoffee) -> Unit,
    viewModel: FavouritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(modifier = modifier, containerColor = appBgColor, topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
        ), title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.SemiBold,
                text = stringResource(id = R.string.label_favourites),
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
            uiState.data?.let { favourites ->
                CoffeeGridView(
                    modifier = Modifier
                        .padding(contentPadding)
                        .padding(vertical = 16.dp),
                    data = favourites,
                    onCoffeeSelected = onCoffeeSelected
                )
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
private fun CoffeeGridView(
    modifier: Modifier, data: List<DomainCoffee>, onCoffeeSelected: (DomainCoffee) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        columns = GridCells.Fixed(count = 2)
    ) {
        items(items = data, key = {
            "${it.title}${it.priceSmall}"
        }) {
            CoffeeItem(
                modifier = Modifier.fillMaxSize(), coffee = it, onCoffeeSelected = onCoffeeSelected
            )
        }
    }
}


@Composable
private fun CoffeeItem(
    modifier: Modifier, coffee: DomainCoffee, onCoffeeSelected: (DomainCoffee) -> Unit
) {
    Card(
        onClick = {
            onCoffeeSelected.invoke(coffee)
        }, modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${AppConstant.GET_IMAGES_URL}${coffee.image}").crossfade(true).build(),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                text = coffee.title,
                style = TextStyle(color = textTitleColor, fontSize = 18.sp),
                fontFamily = soraFamily,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = coffee.description,
                style = TextStyle(color = textHomeGrayColor, fontSize = 14.sp),
                fontFamily = soraFamily
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "$ ${coffee.priceSmall}",
                style = TextStyle(color = textTitleColor, fontSize = 18.sp),
                fontFamily = soraFamily,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview
@Composable
private fun CoffeeItemPreview() {
    CoffeeShopTheme {
        CoffeeItem(modifier = Modifier.fillMaxSize(),
            coffee = getDummyDomainCoffee(),
            onCoffeeSelected = {})
    }
}