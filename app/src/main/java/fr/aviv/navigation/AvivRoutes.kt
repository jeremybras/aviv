package fr.aviv.navigation

sealed class AvivRoutes(val route: String) {

    companion object {
        internal const val PARAM_LISTING_ID = "LISTING_ID"
        private const val ROUTE_LISTINGS = "ROUTE_LISTINGS"
        private const val ROUTE_LISTING_DETAIL = "ROUTE_LISTING_DETAIL/{$PARAM_LISTING_ID}"

        fun AvivRoutes.format(
            args: Map<String, String> = emptyMap(),
        ): String {
            var formattedRoute = this.route
            args.forEach { (key, value) ->
                formattedRoute = formattedRoute.replace("{$key}", value)
            }
            return formattedRoute
        }
    }

    data object ListingsRoute : AvivRoutes(route = ROUTE_LISTINGS)
    data object ListingDetailRoute : AvivRoutes(route = ROUTE_LISTING_DETAIL)
}
