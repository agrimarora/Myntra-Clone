package com.example.myntra.presentatiom.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myntra.comman.model.ProductModel
import kotlinx.coroutines.flow.MutableStateFlow

class productviewmodel :ViewModel(){
    var product = MutableStateFlow( ProductModel())
    var productList =MutableStateFlow(listOf<ProductModel>())





}