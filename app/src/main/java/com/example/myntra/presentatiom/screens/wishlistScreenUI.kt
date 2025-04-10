package com.example.myntra.presentatiom.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.myntra.R
import com.example.myntra.comman.customColor
import com.example.myntra.comman.model.ProductModel
import com.example.myntra.presentatiom.navigation.Routes
import com.example.myntra.presentatiom.viewmodel.ViewModel
import com.example.myntra.presentatiom.viewmodel.productviewmodel

@Composable
fun WishlistScreenUI(viewModel: ViewModel = hiltViewModel(),navController: NavHostController,productviewmodel: productviewmodel) {
    val state = viewModel.getwishlistProducts.value
    val products = state.success
    var totalprice= remember { mutableStateOf(0) }
//   for(product in products!!){
//       totalprice.value+=product.price.toInt()
//   }

    LaunchedEffect(key1 = true) {
        viewModel.getwishlistProducts()
    }
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = customColor)
        }
    } else if (state.error != null) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Something went wrong")
        }

    }
    else{
//        var totalPriceValue = 0
//        if (products != null) {
//            for (product in products) {
//                if (product.price != "") {
//                    totalPriceValue += product.price.toInt()
//                }
//            }
//        }
//        totalprice.value = totalPriceValue






        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Background Circles
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = Color(0xFFFFAFAF), // Light pink color
                    radius = size.width * 0.52f, // Adjust the size
                    center = Offset(
                        size.width * 1.1f,
                        size.height * -0.1f
                    ) // Upper right corner
                )

            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Spacer(modifier = Modifier.height(16.dp))
                Text("Wishlist Products")
                LazyColumn(
                    modifier = Modifier.height(700.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ){




                    if (!products.isNullOrEmpty()) {
                        items(products.size){

                            AllProductWishlistSCreenCard(product = products[it], navController,productviewmodel)
                        }

                    }
                    else{
                        item {

                            Text(text = "No products found")
                            Spacer(modifier = Modifier.height(60.dp))
                            Button(
                                onClick = { navController.navigate(Routes.HomeScreen) },
                                modifier = Modifier.width(160.dp).height(60.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = customColor),
                            ) {
                                Text(text = "Click to browse product")
                            }

                        }

                    }


                }
                Button(modifier = Modifier.fillMaxWidth(), onClick = {navController.navigate(Routes.CartScreen)}, colors = ButtonDefaults.buttonColors(containerColor = customColor)) {
                    Text(text = "Go to Cart", color = Color.White)
                }





            }

        }


    }
}
@Composable
fun AllProductWishlistSCreenCard(product: ProductModel, navController: NavHostController, productviemodel: productviewmodel,viewModel: ViewModel= hiltViewModel()) {

    Card(modifier = Modifier.fillMaxWidth().height(110.dp).clickable {
        productviemodel.product.value=product
        navController.navigate(Routes.ProductDetailScreen)
    },
        colors = CardDefaults.cardColors(
            Color.Transparent
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = product.imagelink,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(109.dp).width(80.dp)

            )
            Spacer(modifier = Modifier.width(10.dp))
            Row(modifier = Modifier.fillMaxWidth().height(110.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(
                    modifier = Modifier.fillMaxHeight().padding(8.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = product.name,
                        fontSize = 20.sp, color = Color.Black
                    )
                    Text(
                        text = "Rs${product.price}",
                        fontSize = 16.sp, color = customColor
                    )

                }
                Spacer(modifier = Modifier.width(30.dp))
                Column(
                    modifier = Modifier.height(110.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    Icon(
                        painter = painterResource(R.drawable.baseline_favorite_24),
                        modifier = Modifier.clickable {
                            viewModel.removewishlist(product)
                            navController.navigate(Routes.WishListScreen)
                        },
                        contentDescription = null,
                        tint = Color.Red
                    )
                    Icon(
                        painter = painterResource(R.drawable.outline_shopping_cart_24),
                        modifier = Modifier.clickable {
                            viewModel.addtocart(product)

                        },
                        contentDescription = null,
                        tint = Color.Green
                    )


                }
            }

        }
    }
}
