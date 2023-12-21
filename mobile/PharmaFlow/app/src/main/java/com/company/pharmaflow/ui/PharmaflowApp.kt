package com.company.pharmaflow.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.company.pharmaflow.R
import com.company.pharmaflow.R.string
import com.company.pharmaflow.data.rememberFirebaseAuthLauncher
import com.company.pharmaflow.ui.item.ItemDetailsDestination
import com.company.pharmaflow.ui.item.ItemDetailsScreen
import com.company.pharmaflow.ui.item.ItemEditDestination
import com.company.pharmaflow.ui.item.ItemEditScreen
import com.company.pharmaflow.ui.item.ItemEntryDestination
import com.company.pharmaflow.ui.item.ItemEntryScreen
import com.company.pharmaflow.ui.login.LoginScreen
import com.company.pharmaflow.ui.navigation.NavigationItem
import com.company.pharmaflow.ui.navigation.Screen
import com.company.pharmaflow.ui.screen.cart.CartScreen
import com.company.pharmaflow.ui.screen.history.HistoryScreen
import com.company.pharmaflow.ui.screen.home.HomeScreen
import com.company.pharmaflow.ui.screen.product.ProductScreen
import com.company.pharmaflow.ui.theme.PharmaFlowTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import org.koin.androidx.compose.koinViewModel

@Composable
fun PharmaflowApp (
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel = koinViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var user by mainViewModel.user
    var isAuthenticated by mainViewModel.isAuthenticated

    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            user = result.user
            isAuthenticated = true
            navController.popBackStack()
            navController.navigate(Screen.Home.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        onAuthError = {
            isAuthenticated = false
            user = null
        }
    )

    val token = stringResource(R.string.web_client_id)
    val context = LocalContext.current
    val initialRoute = if (isAuthenticated) Screen.Home.route else Screen.Login.route


    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },

        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = initialRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            //Login
            composable(Screen.Login.route) {
                LoginScreen(
                    onSignInCLick = {
                        val gso =
                            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(token)
                                .requestEmail()
                                .build()
                        val googleSignInClient = GoogleSignIn.getClient(context, gso)
                        launcher.launch(googleSignInClient.signInIntent)
                    }
                )
            }

            //Bottom Nav
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Cart.route) {
                CartScreen()
            }
            composable(Screen.Product.route) {
                ProductScreen(
                    navigateToItemEntry = { navController.navigate(ItemEntryDestination.route) },
                    navigateToItemUpdate = {
                        navController.navigate("${ItemDetailsDestination.route}/${it}")
                    }
                )
            }
            composable(Screen.History.route) {
                HistoryScreen()
            }

            // Product
            composable(route = ItemEntryDestination.route) {
                ItemEntryScreen(
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() }
                )
            }
            composable(
                route = ItemDetailsDestination.routeWithArgs,
                arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
                    type = NavType.IntType
                })
            ) {
                ItemDetailsScreen(
                    navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") },
                    navigateBack = { navController.navigateUp() }
                )
            }
            composable(
                route = ItemEditDestination.routeWithArgs,
                arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
                    type = NavType.IntType
                })
            ) {
                ItemEditScreen(
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() }
                )
            }
        }
    }
}


@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Check if the current screen is the Login screen
    val isLoginScreen = currentRoute == Screen.Login.route

    if (!isLoginScreen) {
        NavigationBar(
            modifier = modifier,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val navigationItems = listOf(
                NavigationItem(
                    title = stringResource(R.string.menu_home),
                    icon = Icons.Outlined.Home,
                    screen = Screen.Home
                ),
                NavigationItem(
                    title = stringResource(R.string.menu_cart),
                    icon = Icons.Outlined.ShoppingCart,
                    screen = Screen.Cart
                ),
                NavigationItem(
                    title = stringResource(R.string.menu_product),
                    icon = ImageVector.vectorResource(id = R.drawable.baseline_storefront_24),
                    screen = Screen.Product
                ),
                NavigationItem(
                    title = stringResource(R.string.menu_history),
                    icon = ImageVector.vectorResource(id = R.drawable.baseline_history_24),
                    screen = Screen.History
                ),
            )
            navigationItems.map { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
//                selected = false,
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }

}

/**
 * App bar to display title and conditionally display the back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(string.back_button)
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PharmaflowAppPreview() {
    PharmaFlowTheme {
        PharmaflowApp()
    }
}