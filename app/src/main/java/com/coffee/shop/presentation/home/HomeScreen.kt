package com.coffee.shop.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cofee.shop.R
import com.coffee.shop.components.CoffeeButton
import com.coffee.shop.components.Loading
import com.coffee.shop.components.WhiteButton
import com.coffee.shop.domain.models.DomainCoffee
import com.coffee.shop.domain.models.DomainCoffeeCategory
import com.coffee.shop.domain.models.DomainUser
import com.coffee.shop.theme.CoffeeShopTheme
import com.coffee.shop.theme.homeBlackBgColor
import com.coffee.shop.theme.soraFamily
import com.coffee.shop.theme.textHomeGrayColor
import com.coffee.shop.theme.textHomeLocationColor
import com.coffee.shop.theme.textSearchPlaceholderColor
import com.coffee.shop.theme.textTitleColor
import com.coffee.shop.utils.AppConstant.GET_IMAGES_URL

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onCoffeeSelected: (DomainCoffee) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var searchQuery by remember {
        mutableStateOf("")
    }
    if (uiState.loading) {
        Loading(modifier = modifier.fillMaxSize())
    } else {
        Column(
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(.4f)
                    .background(color = homeBlackBgColor)
                    .padding(20.dp)
            ) {

                ProfileItem(modifier = Modifier.fillMaxWidth(), user = uiState.data?.user)
                Spacer(modifier = Modifier.height(16.dp))
                SearchItem(searchQuery = searchQuery) {
                    searchQuery = it
                }
                Spacer(modifier = Modifier.height(16.dp))
                PromoView(
                    modifier = Modifier.fillMaxWidth(), promoUrl = uiState.data?.promoUrl
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(.6f)
            ) {
                CoffeeTabs(
                    modifier = Modifier.fillMaxSize(),
                    coffeeCategories = uiState.data?.coffeeCategories,
                    onCoffeeSelected = onCoffeeSelected
                )
            }
        }
    }
}

@Composable
private fun ProfileItem(modifier: Modifier, user: DomainUser?) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.label_home_location),
                style = TextStyle(color = textHomeGrayColor, fontSize = 15.sp),
                fontFamily = soraFamily,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = user?.location ?: "",
                    style = TextStyle(color = textHomeLocationColor, fontSize = 18.sp),
                    fontFamily = soraFamily,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    tint = Color.White,
                    imageVector = Icons.Filled.MyLocation,
                    contentDescription = stringResource(id = R.string.location_content_description)
                )
            }
        }
        user?.profilePicture?.let {
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = ImageRequest.Builder(LocalContext.current).data("${GET_IMAGES_URL}$it")
                    .crossfade(true).build(),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun ProfileItemPreview() {
    CoffeeShopTheme {
        ProfileItem(
            modifier = Modifier.fillMaxWidth(), user = DomainUser(
                name = "Fahad Nasrullah",
                profilePicture = "http://picture.com",
                location = "Lahore, Pakistan"
            )
        )
    }
}

@Composable
private fun SearchItem(
    searchQuery: String, onSearchQueryChange: (String) -> Unit
) {
    val maxLength = 50

    BasicTextField(modifier = Modifier.fillMaxWidth(),
        value = searchQuery,
        singleLine = true,
        onValueChange = { newText ->
            if (newText.length <= maxLength) {
                onSearchQueryChange(newText)
            }
        },
        textStyle = TextStyle(
            color = Color.White, fontSize = 15.sp
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFF313131), shape = RoundedCornerShape(size = 10.dp)
                    ), verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_search),
                    tint = Color.White,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.label_search_cofee),
                                fontWeight = FontWeight.Normal,
                                fontFamily = soraFamily,
                                color = textSearchPlaceholderColor,
                                fontSize = 15.sp
                            )
                        }
                        innerTextField()
                    }
                }
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(corner = CornerSize(12.dp))
                        )
                        .clickable {

                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_furnitur),
                        contentDescription = null
                    )
                }
            }
        })
}

@Preview
@Composable
private fun SearchItemPreview() {
    CoffeeShopTheme {
        SearchItem(searchQuery = "") {}
    }
}

@Composable
private fun EmptyPromo(modifier: Modifier) {
    Card(modifier = modifier) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.label_promo_goes_here),
                style = TextStyle(color = Color.Black, fontSize = 16.sp),
                fontFamily = soraFamily,
            )
        }
    }
}

@Preview
@Composable
private fun EmptyPromoPreview() {
    CoffeeShopTheme {
        EmptyPromo(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
    }
}

@Composable
private fun PromoView(modifier: Modifier, promoUrl: String?) {
    promoUrl?.let {
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            model = ImageRequest.Builder(LocalContext.current).data("$GET_IMAGES_URL$it")
                .crossfade(true).build(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null
        )
    } ?: run {
        EmptyPromo(
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp)
        )
    }
}

@Preview
@Composable
private fun PromoViewPreview() {
    CoffeeShopTheme {
        PromoView(modifier = Modifier.fillMaxSize(), promoUrl = null)
    }
}

@Composable
private fun CoffeeTabs(
    modifier: Modifier,
    coffeeCategories: List<DomainCoffeeCategory>?,
    onCoffeeSelected: (DomainCoffee) -> Unit
) {
    coffeeCategories?.let { category ->
        val tabs = category.map { it.category }
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        Column(modifier = modifier) {
            ScrollableTabRow(containerColor = textHomeLocationColor,
                selectedTabIndex = selectedTabIndex,
                edgePadding = 16.dp,
                indicator = {}) {
                tabs.forEachIndexed { index, tab ->
                    if (selectedTabIndex == index) {
                        CoffeeButton(modifier = Modifier.padding(8.dp), buttonTitle = tab, onTap = {
                            selectedTabIndex = index
                        })
                    } else {
                        WhiteButton(modifier = Modifier.padding(8.dp), buttonTitle = tab, onTap = {
                            selectedTabIndex = index
                        })
                    }
                }
            }
            CoffeeGridView(
                modifier = Modifier.fillMaxSize(),
                data = category[selectedTabIndex].items,
                onCoffeeSelected = onCoffeeSelected
            )
        }
    } ?: run {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.label_empty_coffees),
                style = TextStyle(color = Color.Black, fontSize = 16.sp),
                fontFamily = soraFamily,
            )
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
            "${it.title}${it.price}"
        }) {
            CoffeeItem(modifier = Modifier.fillMaxSize(), coffee = it)
        }
    }
}


@Composable
private fun CoffeeItem(modifier: Modifier, coffee: DomainCoffee) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
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
                    .data("$GET_IMAGES_URL${coffee.image}").crossfade(true).build(),
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = "$ ${coffee.price}",
                    style = TextStyle(color = textTitleColor, fontSize = 18.sp),
                    fontFamily = soraFamily,
                    fontWeight = FontWeight.SemiBold
                )
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(corner = CornerSize(12.dp))
                        )
                        .clickable {

                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            }
        }
    }
}