package com.example.myntra.domain.repo

import com.example.myntra.comman.ResultState
import com.example.myntra.comman.model.CatergoryModel
import com.example.myntra.comman.model.ProductModel
import com.example.myntra.comman.model.SliderImage
import com.example.myntra.domain.model.UserDataParent
import com.example.myntra.domain.model.Userdata
import kotlinx.coroutines.flow.Flow

interface Repo {

    fun registeruserwithemailandpassword(userdata: Userdata):Flow<ResultState<String>>
    fun loginuserwithemailandpassword(userdata: Userdata):Flow<ResultState<String>>
fun getuserbyUID(UID:String):Flow<ResultState<UserDataParent>>
    fun updateUserData(userDataParent: UserDataParent): Flow<ResultState<String>>
    suspend fun getAllCategories(): Flow<ResultState<List<CatergoryModel>>>
      suspend fun getSliderpics(): Flow<ResultState<List<SliderImage>>>
      suspend fun getproducts(): Flow<ResultState<List<ProductModel>>>
    suspend  fun addToCart(product: ProductModel): Flow<ResultState<String>>
    suspend fun removeFromCart(productId: String): Flow<ResultState<String>>
    fun getallcartproduct():Flow<ResultState<List<ProductModel>>>
    suspend fun addTowhislist(product: ProductModel): Flow<ResultState<String>>
    suspend fun removeFromWishlist(productId: String): Flow<ResultState<String>>
    fun getallwishlistproduct():Flow<ResultState<List<ProductModel>>>
}