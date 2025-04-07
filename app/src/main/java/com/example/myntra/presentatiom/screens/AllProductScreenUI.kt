package com.example.myntra.presentatiom.screens

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField


import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.myntra.comman.customColor
import com.example.myntra.comman.model.ProductModel
import com.example.myntra.presentatiom.navigation.Routes
import com.example.myntra.presentatiom.viewmodel.productviewmodel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AllProductScreenUI(navController: NavHostController,viemodel: productviewmodel,productviewmodel: productviewmodel) {
    var searchQuery = remember { mutableStateOf("") }
    val allproducts = viemodel.productList.collectAsState().value
    val filteredProducts = allproducts.filter { it.name?.contains(searchQuery.value, ignoreCase = true) == true }


    Log.d("AllProducts", "AllProductScreenUI:${allproducts} ")
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
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search products...") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    focusedBorderColor = Color(0xFFFFAFAF),
                    unfocusedBorderColor = Color.Gray
                ),
                shape = RoundedCornerShape(16.dp)
            )
Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(filteredProducts.size){
                    AllProductSCreenCard(product = allproducts[it], navController,productviewmodel)
                }

            }



        }

    }
}

@Composable
fun AllProductSCreenCard(product: ProductModel, navController: NavHostController, viemodel: productviewmodel) {
    Card(modifier = Modifier.fillMaxWidth().height(110.dp).clickable {
        viemodel.product.value=product
        navController.navigate(Routes.ProductDetailScreen)
    },
        colors = CardDefaults.cardColors(
            Color.Transparent
        )
        ) {
        Row(modifier = Modifier.fillMaxWidth()){
            AsyncImage(
                model = product.imagelink,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(109.dp).width(80.dp)

            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.fillMaxHeight().padding(8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start) {
                Text(text = product.name,
                    fontSize = 20.sp
                , color = Color.Black)
                Text(text = "Rs${product.price}",
                    fontSize = 16.sp
                    , color = customColor)

            }

        }
    }

}
