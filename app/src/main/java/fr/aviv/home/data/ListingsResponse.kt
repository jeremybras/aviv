package fr.aviv.home.data

sealed interface ListingsResponse {
    data class Success(
        val items: List<Listing>,
    ) : ListingsResponse {
        data class Listing(
            val bedrooms: Int,
            val city: String,
            val id: Int,
            val area: Int,
            val url: String?,
            val price: Int,
            val professional: String,
            val propertyType: PropertyType,
            val offerType: Int,
            val rooms: Int,
        )

        enum class PropertyType {
            HOUSE,
            APPARTMENT,
        }
    }

    data object Failure : ListingsResponse
}
