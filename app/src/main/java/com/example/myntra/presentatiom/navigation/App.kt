import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.myntra.comman.customColor
import com.example.myntra.presentatiom.navigation.Routes
import com.example.myntra.presentatiom.navigation.SubNavigation
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
fun App(firebaseAuth: FirebaseAuth) {
    val navController = rememberNavController()
    val viewmodel = viewModel<productviewmodel>()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        Pair(Routes.HomeScreen, "Home"),
        Pair(Routes.WishListScreen, "Wishlist"),
        Pair(Routes.CartScreen, "Cart"),
        Pair(Routes.ProfileScreen, "Profile")
    )

    val showBottomNav = currentRoute in bottomNavItems.map { it.first::class.qualifiedName!! }

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                BottomNavigation(modifier = Modifier.height(86.dp).fillMaxWidth().padding(5.dp), backgroundColor = Color.White) {
                    bottomNavItems.forEach { (screen, label) ->
                        val route = screen::class.qualifiedName!!
                        BottomNavigationItem(
                            selected = currentRoute == route,
                            onClick = {
                                navController.navigate(screen) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                when (label) {
                                    "Home" -> Icon(Icons.Default.Home, contentDescription = null)
                                    "Wishlist" -> Icon(Icons.Default.Favorite, contentDescription = null)
                                    "Cart" -> Icon(Icons.Default.ShoppingCart, contentDescription = null)
                                    "Profile" -> Icon(Icons.Default.Person, contentDescription = null)
                                }
                            },
                            label = { Text(label) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            val startScreen = if (firebaseAuth.currentUser == null) {
                SubNavigation.LoginSignupScreen
            } else {
                SubNavigation.MainHomeScreen
            }
            NavHost(navController = navController, startDestination = startScreen) {
                navigation<SubNavigation.LoginSignupScreen>(startDestination = Routes.LoginScreen) {
                    composable<Routes.LoginScreen> {
                        LoginScreenUI(navController, firebaseAuth = firebaseAuth)
                    }
                    composable<Routes.SignUpScreen> {
                        SignupScreenUI(navController)
                    }
                }
                navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.HomeScreen) {
                    composable<Routes.HomeScreen> {
                        HomeScreenUI(navController = navController, productdetailviewmodel = viewmodel, firebaseAuth = firebaseAuth)
                    }
                    composable<Routes.WishListScreen> {
                        WishlistScreenUI(navController = navController, productviewmodel = viewmodel)
                    }
                    composable<Routes.CartScreen> {
                        CartScreenUI(navController = navController, productviewmodel = viewmodel)
                    }
                    composable<Routes.ProfileScreen> {
                        ProfileSCreenUI(firebaseAuth = firebaseAuth)
                    }
                    composable<Routes.ForgotPasswordScreen> {
                        forgotpasswordScreenUI(navController = navController, firebaseAuth = firebaseAuth)
                    }
                    composable<Routes.ProductDetailScreen> {
                        ProductDetailScreenUI(navController = navController, productviewmodel = viewmodel)
                    }
                    composable<Routes.allproductsScreen> {
                        AllProductScreenUI(navController, viewmodel, viewmodel)
                    }
                }
            }
        }
    }
}