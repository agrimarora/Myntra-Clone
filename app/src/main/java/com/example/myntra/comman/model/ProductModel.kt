package com.example.myntra.comman.model

data class ProductModel(
    val name:String="",
    val description:String="",
    val imagelink:String="",
    val price:String="",
    val category:String="",
    val createdby:String="",
    val date:Long=System.currentTimeMillis(),
    var cart:Boolean=false,
    var wishlist:Boolean=false,
    val id:String=""
)

