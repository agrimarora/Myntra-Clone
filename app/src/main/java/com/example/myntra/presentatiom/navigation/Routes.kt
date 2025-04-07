package com.example.myntra.presentatiom.navigation
import com.example.myntra.comman.model.ProductModel
import kotlinx.serialization.Serializable
sealed class SubNavigation{
    @Serializable
    object LoginSignupScreen:SubNavigation()
    @Serializable
    object MainHomeScreen:SubNavigation()
}
sealed class Routes {

    @Serializable
    object LoginScreen : Routes()
    @Serializable
    object ForgotPasswordScreen : Routes()

    @Serializable
    object SignUpScreen : Routes()

    @Serializable
    object ProfileScreen : Routes()

    @Serializable
    object HomeScreen: Routes()

        @Serializable
        object WishListScreen: Routes()

        @Serializable
        object CartScreen: Routes()
    @Serializable
    object ProductDetailScreen: Routes()
    @Serializable
    object CheckoutScreen: Routes()
    @Serializable
    object allproductsScreen:Routes()

}
