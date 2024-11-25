package fr.aviv.home.data

data class ListingsJsonResponse(
    val items: List<ListingResponse>,
) {
    data class ListingResponse(
        val bedrooms: Int,
        val city: String,
        val id: Int,
        val area: Int,
        val url: String?,
        val price: Int,
        val professional: String,
        val propertyType: String,
        val offerType: Int,
        val rooms: Int,
    )
}

open class RepositoryException : Exception()

class ListingsException : RepositoryException()
