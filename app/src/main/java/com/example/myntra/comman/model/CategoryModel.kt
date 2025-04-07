package com.example.myntra.comman.model

data class CatergoryModel(
    var name:String?="",
    val imagelink:String?="",

    val createdby:String?="",
    val date:Long=System.currentTimeMillis()
)
