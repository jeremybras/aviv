package fr.aviv.home.domain

sealed interface ListingsResult {
    data class Success(
        val items: List<Listing>,
    ) : ListingsResult

    data object Error : ListingsResult
}

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