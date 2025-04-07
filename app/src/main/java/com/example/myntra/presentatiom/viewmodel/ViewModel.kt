package com.example.myntra.presentatiom.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.comman.ResultState
import com.example.myntra.comman.model.CatergoryModel
import com.example.myntra.comman.model.ProductModel
import com.example.myntra.comman.model.SliderImage
import com.example.myntra.domain.Usecase.AddtoCart
import com.example.myntra.domain.Usecase.Addtowishlist
import com.example.myntra.domain.Usecase.CreateuserUseCase
import com.example.myntra.domain.Usecase.LogInUser
import com.example.myntra.domain.Usecase.RemovefromCart
import com.example.myntra.domain.Usecase.Removefromwishlist
import com.example.myntra.domain.Usecase.UpdateUserDetails
import com.example.myntra.domain.Usecase.getUserBYIDusecase
import com.example.myntra.domain.Usecase.getallcartproducts
import com.example.myntra.domain.Usecase.getallcategories
import com.example.myntra.domain.Usecase.getallproducts
import com.example.myntra.domain.Usecase.getallwishlistproducts
import com.example.myntra.domain.Usecase.getsliderimages
import com.example.myntra.domain.model.UserDataParent
import com.example.myntra.domain.model.Userdata
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    val createUserUseCase: CreateuserUseCase,
    val loginuserUseCase: LogInUser,
    val getUserBYIDusecase: getUserBYIDusecase,
    val updateUserDetails: UpdateUserDetails,
    val getallcategories: getallcategories,
    val getsliderimages: getsliderimages,
    val getallproducts: getallproducts,
    val addtoCart: AddtoCart,
    val removefromCart: RemovefromCart,
    val getallcartproducts: getallcartproducts,
    val addtowishlist: Addtowishlist,
    val removefromWishlist: Removefromwishlist,
    val getallwishlistproducts: getallwishlistproducts
) : ViewModel() {
    private val _signupScreenstate = mutableStateOf(SignUpScreenSate())
    val signupScreenstate = _signupScreenstate
    private var _addtocartScreenstate = mutableStateOf(addtocart())
    val addtoCartScreenstate = _addtocartScreenstate
    private var _addtowishlistScreenstate = mutableStateOf(addtowishlist())
    val addtowishlistScreenstate = _addtowishlistScreenstate
    private val _loginScreenstate = mutableStateOf(LogINScreenSate())
    val loginScreenstate = _loginScreenstate
    private val _ProfileScreenstate = mutableStateOf(ProfileScreenSate())
    val ProfileScreenState = _ProfileScreenstate
    private val _getcategoryState = mutableStateOf(GetCategoryState())
    val getcategoryState = _getcategoryState
    private val _getsliderimagesstate = mutableStateOf(GetSliderImage())
    val getsliderimageState = _getsliderimagesstate
    private val _getproducts = mutableStateOf(GetProducts())
    val getProducts = _getproducts
    private val _getcartproducts = mutableStateOf(GetcartProducts())
    val getcartProducts = _getcartproducts
    private val _getwishlistproducts = mutableStateOf(GetwishlistProducts())
    val getwishlistProducts = _getwishlistproducts

    fun LoginUser(userdata: Userdata) {
        viewModelScope.launch {
            loginuserUseCase.loginuser(userdata).collect { result ->
                when (result) {
                    ResultState.Loading -> LogINScreenSate(isLoading = true)
                    is ResultState.Succes -> {
                        _loginScreenstate.value = LogINScreenSate(
                            success = true,
                            userdata = result.data
                        )


                    }

                    is ResultState.error -> {
                        _loginScreenstate.value = LogINScreenSate(error = result.message)
                    }
                }


            }
        }
    }

    init {
        getCategories()
        getSliderImage()
        getProducts()
        getcartProducts()
        getwishlistProducts()
    }

    fun createUser(userdata: Userdata) {
        viewModelScope.launch {
            createUserUseCase.Createuser(userdata).collect { result ->
                when (result) {
                    ResultState.Loading -> {
                        _signupScreenstate.value = SignUpScreenSate(isLoading = true)
                    }

                    is ResultState.Succes -> {
                        _signupScreenstate.value = SignUpScreenSate(
                            success = true,
                            userdata = result.data
                        ) // Update success correctly
                    }

                    is ResultState.error -> {
                        _signupScreenstate.value = SignUpScreenSate(error = result.message)
                    }
                }
            }
        }
    }

    fun getUserbyuid(uid: String) {
        viewModelScope.launch {
            getUserBYIDusecase.getUserBYUid(uid).collectLatest {
                when (it) {
                    ResultState.Loading -> {
                        _ProfileScreenstate.value = ProfileScreenSate(isLoading = true)
                    }

                    is ResultState.Succes -> {
                        _ProfileScreenstate.value =
                            ProfileScreenSate(userdata = it.data, isLoading = false)


                    }

                    is ResultState.error -> _ProfileScreenstate.value =
                        ProfileScreenSate(error = it.message, isLoading = false)
                }
            }

        }


    }

    fun updateUserData(userdataparent: UserDataParent) {
        viewModelScope.launch {
            updateUserDetails.UpdateUserDetails(userdataparent).collectLatest {
                when (it) {
                    ResultState.Loading -> {
                        _ProfileScreenstate.value = ProfileScreenSate(isLoading = true)
                    }

                    is ResultState.Succes -> {
                        _ProfileScreenstate.value = ProfileScreenSate(isLoading = false)


                    }

                    is ResultState.error -> _ProfileScreenstate.value =
                        ProfileScreenSate(error = it.message, isLoading = false)
                }


            }


        }
    }

    fun getCategories() {
        viewModelScope.launch {
            getallcategories.getalllcategories().collectLatest { result ->
                when (result) {
                    is ResultState.Succes -> {
                        _getcategoryState.value = GetCategoryState(
                            success = result.data // Store actual List<CategoryModel>
                        )
                    }

                    is ResultState.error -> {
                        _getcategoryState.value = GetCategoryState(error = result.message)
                    }

                    ResultState.Loading -> {
                        _getcategoryState.value = GetCategoryState(isLoading = true)
                    }
                }
            }
        }

    }

    fun getSliderImage() {
        viewModelScope.launch {
            getsliderimages.getsliderimages().collectLatest { result ->
                when (result) {
                    is ResultState.Succes -> {
                        _getsliderimagesstate.value = GetSliderImage(
                            success = result.data // Store actual List<CategoryModel>
                        )

                    }

                    is ResultState.error -> {
                        _getsliderimagesstate.value = GetSliderImage(error = result.message)
                    }

                    ResultState.Loading -> {
                        _getsliderimagesstate.value = GetSliderImage(isLoading = true)
                    }
                }
            }
        }

    }

    fun getProducts() {
        viewModelScope.launch {
            getallproducts.getalllproducts().collectLatest { result ->
                when (result) {
                    is ResultState.Succes -> {
                        _getproducts.value = GetProducts(
                            success = result.data // Store actual List<CategoryModel>
                        )

                    }

                    is ResultState.error -> {
                        _getproducts.value = GetProducts(error = result.message)
                    }

                    ResultState.Loading -> {
                        _getproducts.value = GetProducts(isLoading = true)
                    }
                }
            }
        }

    }
    fun getcartProducts() {
        viewModelScope.launch {
            getallcartproducts.getalllcartproducts().collectLatest { result ->
                when (result) {
                    is ResultState.Succes -> {
                        _getcartproducts.value = GetcartProducts(
                            success = result.data // Store actual List<CategoryModel>
                        )

                    }

                    is ResultState.error -> {
                        _getcartproducts.value = GetcartProducts(error = result.message)
                    }

                    ResultState.Loading -> {
                        _getcartproducts.value = GetcartProducts(isLoading = true)
                    }
                }
            }
        }

    }
    fun getwishlistProducts() {
        viewModelScope.launch {
           getallwishlistproducts.getalllwishlistproducts().collectLatest { result ->
                when (result) {
                    is ResultState.Succes -> {
                        _getwishlistproducts.value = GetwishlistProducts(
                            success = result.data // Store actual List<CategoryModel>
                        )

                    }

                    is ResultState.error -> {
                        _getwishlistproducts.value = GetwishlistProducts(error = result.message)
                    }

                    ResultState.Loading -> {
                        _getwishlistproducts.value = GetwishlistProducts(isLoading = true)
                    }
                }
            }
        }

    }



    fun addtocart(productModel: ProductModel) {
        viewModelScope.launch {
            addtoCart.AddtoCart(productModel).collectLatest { result ->
                when (result) {
                    ResultState.Loading -> _addtocartScreenstate.value = addtocart(isLoading = true)
                    is ResultState.Succes -> {
                        _addtocartScreenstate.value = addtocart(
                            success = true,
                            userdata = result.data
                        )


                    }

                    is ResultState.error -> {
                        _addtocartScreenstate.value = addtocart(error = result.message)
                    }
                }

            }

        }

    }
    fun addtowishlist(productModel: ProductModel) {
        viewModelScope.launch {
            addtowishlist.Addtowishlist(productModel).collectLatest { result ->
                when (result) {
                    ResultState.Loading -> _addtowishlistScreenstate.value = addtowishlist(isLoading = true)
                    is ResultState.Succes -> {
                        _addtowishlistScreenstate.value = addtowishlist(
                            success = true,
                            userdata = result.data
                        )


                    }

                    is ResultState.error -> {
                        _addtowishlistScreenstate.value = addtowishlist(error = result.message)
                    }
                }

            }

        }

    }
    fun removewishlist(product: ProductModel) {
        viewModelScope.launch {
            removefromWishlist.removefrowishlist(product.id).collectLatest { result ->
                when (result) {
                    ResultState.Loading -> _addtowishlistScreenstate.value = addtowishlist(isLoading = true)
                    is ResultState.Succes -> {
                        _addtowishlistScreenstate.value = addtowishlist(
                            success = true,
                            userdata = result.data
                        )


                    }

                    is ResultState.error -> {
                        _addtowishlistScreenstate.value = addtowishlist(error = result.message)
                    }
                }

            }

        }

    }

    fun removefromcart(productid: String) {
        viewModelScope.launch {
            removefromCart.removefromcart(productid).collectLatest { result ->
                when(result){
                    ResultState.Loading -> _addtocartScreenstate.value=addtocart(true)
                    is ResultState.Succes -> {
                        _addtocartScreenstate.value = addtocart(
                            success = true,
                            userdata = result.data
                        )


                    }

                    is ResultState.error -> {
                        _addtocartScreenstate.value = addtocart(error = result.message)
                    }
                }                }


        }

    }
}

data class ProfileScreenSate(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userdata: UserDataParent? = null,
    val success: Boolean? = false
)

data class SignUpScreenSate(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userdata: String? = null,
    val success: Boolean? = false
)

data class addtocart(
    var isLoading: Boolean = false,
    val error: String? = null,
    val userdata: String? = null,
    val success: Boolean? = false
)
data class addtowishlist(
    var isLoading: Boolean = false,
    val error: String? = null,
    val userdata: String? = null,
    val success: Boolean? = false
)

data class LogINScreenSate(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userdata: String? = null,
    val success: Boolean? = false
)

data class GetCategoryState(
    val success: List<CatergoryModel>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class GetSliderImage(
    val success: List<SliderImage>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class GetProducts(
    val success: List<ProductModel>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
data class GetcartProducts(
    val success: List<ProductModel>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)


data class GetwishlistProducts(
    val success: List<ProductModel>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

