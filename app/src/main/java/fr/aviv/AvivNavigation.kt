package fr.aviv

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.aviv.home.ui.HomeScreen

@Composable
internal fun AvivNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home",
    ) {
        composable("home") {
            HomeScreen(
                onListing = { listingId ->
                    // TODO Go to detail
                },
            )
        }
    }
}
