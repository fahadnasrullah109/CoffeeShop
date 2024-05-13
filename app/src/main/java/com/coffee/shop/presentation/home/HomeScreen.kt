package com.coffee.shop.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.coffee.shop.components.Loading
import com.coffee.shop.domain.models.DomainUser
import com.coffee.shop.theme.CofeeShopTheme
import com.coffee.shop.theme.homeBlackBgColor
import com.coffee.shop.theme.soraFamily
import com.coffee.shop.theme.textHomeGrayColor
import com.coffee.shop.theme.textHomeLocationColor
import com.coffee.shop.theme.textSearchPlaceholderColor
import com.coffee.shop.utils.AppConstant.GET_IMAGES_URL

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var searchQuery by remember {
        mutableStateOf("")
    }
    if (uiState.loading) {
        Loading(modifier = modifier.fillMaxSize())
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.White),
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
                    modifier = Modifier
                        .fillMaxWidth(),
                    promoUrl = uiState.data?.promoUrl
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(.6f)
            ) {

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
    CofeeShopTheme {
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
                Button(
                    onClick = { },
                    modifier = Modifier.size(55.dp),
                    shape = RoundedCornerShape(corner = CornerSize(12.dp))
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
    CofeeShopTheme {
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
    CofeeShopTheme {
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
                .crossfade(true)
                .build(),
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
    CofeeShopTheme {
        PromoView(modifier = Modifier.fillMaxSize(), promoUrl = null)
    }
}

