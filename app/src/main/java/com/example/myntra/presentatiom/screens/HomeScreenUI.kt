package com.example.myntra.presentatiom.screens

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.myntra.R
import com.example.myntra.comman.customColor
import com.example.myntra.comman.model.ProductModel
import com.example.myntra.domain.model.UserDataParent
import com.example.myntra.presentatiom.navigation.Routes
import com.example.myntra.presentatiom.viewmodel.ViewModel
import com.example.myntra.presentatiom.viewmodel.productviewmodel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun HomeScreenUI(
    viemodel: ViewModel = hiltViewModel(),
    navController: NavHostController,
    productdetailviewmodel: productviewmodel,
    firebaseAuth: FirebaseAuth


) {
    val scope = rememberCoroutineScope()
    val categories = viemodel.getcategoryState.value.success ?: emptyList()
    val sliderImage = viemodel.getsliderimageState.value.success ?: emptyList()
    val products = viemodel.getProducts.value.success ?: emptyList()
    val pagerState = rememberPagerState(
        pageCount =
        { sliderImage.size }
    )
    LaunchedEffect(Unit) {

        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
//        viemodel.getUserbyuid(firebaseAuth.currentUser!!.uid.toString())
//        viemodel.updateUserData(userdataparent = UserDataParent(nodeID = ))
    }

    Column(
        modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(70.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Categories",
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif,
                fontSize = 22.sp
            )
            Text(
                text = "See more",
                fontStyle = FontStyle.Normal,
                color = Color(customColor.value),
                fontSize = 16.sp,
                modifier = Modifier.clickable {

                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(categories.size) {
                Column {
                    AsyncImage(
                        model = if (categories[it].imagelink == "") R.drawable.categoryplaceholder else categories[it].imagelink,


                        contentDescription = null,
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    categories[it].name?.let { it1 -> Text(text = it1, fontSize = 13.sp) }


                }
                Spacer(modifier = Modifier.width(10.dp))
            }

        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier.wrapContentSize()) {
            HorizontalPager(
                state = pagerState,
                Modifier
                    .wrapContentSize()

            ) { currentPage ->

                Card(
                    Modifier
                        .wrapContentSize(),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    AsyncImage(
                        model = if (sliderImage[currentPage].Imagelink == "") R.drawable.categoryplaceholder else sliderImage[currentPage].Imagelink,


                        contentDescription = null,
                        modifier = Modifier
                            .height(250.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillBounds


                    )

                }
            }
            IconButton(
                onClick = {
                    val nextPage = pagerState.currentPage + 1
                    if (nextPage < sliderImage.size) {
                        scope.launch {
                            pagerState.scrollToPage(nextPage)
                        }
                    }
                },
                Modifier
                    .padding(10.dp)
                    .size(18.dp)
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0x52373737)
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "",
                    Modifier.fillMaxSize(),
                    tint = Color.LightGray
                )
            }
            IconButton(
                onClick = {
                    val prevPage = pagerState.currentPage - 1
                    if (prevPage >= 0) {
                        scope.launch {
                            pagerState.scrollToPage(prevPage)
                        }
                    }
                },
                Modifier
                    .padding(10.dp)
                    .size(18.dp)
                    .align(Alignment.CenterStart)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0x52373737)
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "",
                    Modifier.fillMaxSize(),
                    tint = Color.LightGray
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Text("Flash Sale", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Text(
                "See more",
                color = Color(customColor.value),
                fontSize = 16.sp,
                modifier = Modifier.clickable {
                    productdetailviewmodel.productList.value=products
                    navController.navigate(Routes.allproductsScreen)

                })
        }
        Spacer(modifier = Modifier.height(7.dp))
        LazyRow(

            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products.size) { product ->
                ProductCard(products[product], navController, productdetailviewmodel)

            }


        }


    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage, modifier = modifier)
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(
        modifier = modifier
            .padding(2.dp)
            .size(size.value)
            .clip(CircleShape)
            .background(if (isSelected) Color(0xff373737) else Color(0xA8373737))
    )
}

@Composable
fun ProductCard(
    product: ProductModel,
    navController: NavHostController,
    viemodel: productviewmodel
) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp)
            .clickable {
                viemodel.product.value = product




                Log.d("product", "ProductCard:${product} ")
                navController.navigate(Routes.ProductDetailScreen)

            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = if (product.imagelink == "") R.drawable.categoryplaceholder else product.imagelink,
                contentDescription = product.name,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Rs: ${product.price}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(customColor.value),
            )

        }
    }
}