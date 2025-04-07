package com.example.myntra.domain.model

data class Userdata(
    var name: String?="",
    var email: String?="",
    var phonenumber: String?="",
    val password: String?="",
)
data class UserDataParent(val nodeID: String?="", val Userdata: Userdata?=Userdata())