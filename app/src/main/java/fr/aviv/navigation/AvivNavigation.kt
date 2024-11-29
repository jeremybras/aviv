package fr.aviv.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.aviv.details.ui.DetailScreen
import fr.aviv.home.ui.HomeScreen
import fr.aviv.navigation.AvivRoutes.Companion.format

@Composable
internal fun AvivNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AvivRoutes.ListingsRoute.route,
    ) {
        composable(route = AvivRoutes.ListingsRoute.route) {
            HomeScreen(
                onListing = { listingId ->
                    navController.navigate(
                        route = AvivRoutes.ListingDetailRoute.format(
                            args = mapOf(
                                AvivRoutes.PARAM_LISTING_ID to listingId.toString(),
                            )
                        )
                    )
                },
            )
        }

        composable(
            route = AvivRoutes.ListingDetailRoute.route,
            arguments = listOf(
                navArgument(AvivRoutes.PARAM_LISTING_ID) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val args = backStackEntry.arguments
            val listingId = args?.getString(AvivRoutes.PARAM_LISTING_ID)?.toInt() ?: 0
            DetailScreen(
                listingId = listingId,
                onBack = {
                    navController.navigateUp()
                },
            )
        }
    }
}
