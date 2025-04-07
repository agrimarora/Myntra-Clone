package com.example.myntra.presentatiom.navigation


import androidx.compose.foundation.layout.Box
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.myntra.presentatiom.screens.AllProductScreenUI
import com.example.myntra.presentatiom.screens.CartScreenUI
import com.example.myntra.presentatiom.screens.HomeScreenUI

import com.example.myntra.presentatiom.screens.LoginScreenUI
import com.example.myntra.presentatiom.screens.ProductDetailScreenUI
import com.example.myntra.presentatiom.screens.ProfileSCreenUI
import com.example.myntra.presentatiom.screens.SignupScreenUI
import com.example.myntra.presentatiom.screens.WishlistScreenUI
import com.example.myntra.presentatiom.screens.forgotpasswordScreenUI
import com.example.myntra.presentatiom.viewmodel.productviewmodel
import com.google.firebase.auth.FirebaseAuth


@Composable


fun  App(firebaseAuth:FirebaseAuth){
    val navController= rememberNavController()
    val viewmodel= viewModel<productviewmodel>()
    Box(
        modifier = Modifier.fillMaxSize()
    )

    {
        var startScreen=if(firebaseAuth.currentUser==null){
            SubNavigation.LoginSignupScreen
        }else{
            SubNavigation.MainHomeScreen
        }
        NavHost(navController = navController, startDestination = startScreen){
            navigation<SubNavigation.LoginSignupScreen>(startDestination = Routes.LoginScreen){
                composable<Routes.LoginScreen> {
                    LoginScreenUI(navController, firebaseAuth = firebaseAuth)
                }
                composable<Routes.SignUpScreen> {
                    SignupScreenUI(navController)
                }
            }
            navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.WishListScreen){
                composable<Routes.HomeScreen> { HomeScreenUI(navController = navController, productdetailviewmodel =  viewmodel, firebaseAuth = firebaseAuth) }
                composable<Routes.WishListScreen> {  WishlistScreenUI(navController = navController, productviewmodel = viewmodel) }
                composable<Routes.CartScreen> {
                    CartScreenUI(navController = navController, productviewmodel = viewmodel)
                }
                composable<Routes.ProfileScreen> {
                    ProfileSCreenUI(firebaseAuth = firebaseAuth)
                }
                composable<Routes.ForgotPasswordScreen> {
                    forgotpasswordScreenUI(navController = navController,firebaseAuth = firebaseAuth)
                }

                composable<Routes.ProductDetailScreen> {
                    ProductDetailScreenUI(navController = navController, productviewmodel = viewmodel)
                }

                composable<Routes.allproductsScreen> { AllProductScreenUI(navController,viewmodel,viewmodel)  }

            }


        }
    }
}
