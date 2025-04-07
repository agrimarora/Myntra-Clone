package com.example.myntra.presentatiom.screens

import android.annotation.SuppressLint
import android.util.Log

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import com.example.myntra.presentatiom.viewmodel.ViewModel
import com.example.myntra.presentatiom.viewmodel.productviewmodel

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreenUI(viewModel: ViewModel = hiltViewModel(), navController: NavHostController,productviewmodel:productviewmodel) {
    var product = productviewmodel.product.collectAsState().value
 val cartproducts = viewModel.getcartProducts.value.success ?: emptyList()
    Log.d("ToCheck dataflow", "ProductDetailScreenUI: $product")
    LaunchedEffect(product) {




        Log.d("ProductDetailScreen", "Loaded product: $product")

    }
    Column(modifier = Modifier.fillMaxSize()) {


        Box(modifier = Modifier.height(400.dp)) {
            AsyncImage(
                model = if (product.imagelink=="")
                    R.drawable.categoryplaceholder
                    else product.imagelink, // Replace with actual image resource
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.White)
                        )
                    )
            )
        }
        ProductDetails(product,viewModel,cartproducts)
    }
}

@Composable
fun ProductDetails(product: ProductModel, viewModel: ViewModel, cartproducts: List<ProductModel>) {
    var addtocartvariable= remember { mutableStateOf(product.cart) }
    var addtowishlistvariable= remember { mutableStateOf(product.wishlist) }
    LaunchedEffect(cartproducts) {
        addtocartvariable.value = cartproducts.any { it.id == product.id }
        addtowishlistvariable.value = cartproducts.any { it.id == product.id }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = product.name, fontSize = 20.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = product.price, fontSize = 18.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            listOf("UK 8", "UK 10", "UK 12").forEach { size ->
                Button(onClick = {}, modifier = Modifier.padding(end = 8.dp)) {
                    Text(text = size)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            listOf(Color(0xFFE57373), Color(0xFF4DB6AC), Color(0xFFFFF176)).forEach { color ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color, shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Buy Now", color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                addtocartvariable.value = !addtocartvariable.value

                if (addtocartvariable.value) {
                    product.cart = true
                    viewModel.addtocart(product)
                } else {
                    product.cart = false
                    viewModel.removefromcart(product.id)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (addtocartvariable.value) "Remove from Cart" else "Add to Cart", color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                addtowishlistvariable.value = !addtowishlistvariable.value

                if (addtowishlistvariable.value) {
                    product.wishlist = true
                    viewModel.addtowishlist(product)
                } else {
                    product.wishlist = false
                    viewModel.removewishlist(product)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Transparent)

        ) {
            Icon(
                painter = if (addtowishlistvariable.value) painterResource(R.drawable.baseline_bookmark_24)
                else painterResource(R.drawable.outline_bookmark_border_24),
                contentDescription = null,
                tint = if (addtowishlistvariable.value) Color(customColor.value) else Color.Gray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = if (addtowishlistvariable.value)"Remove from Wishlist" else "Add to wishlist", color=Color(
                customColor.value) )
        }
    }

}