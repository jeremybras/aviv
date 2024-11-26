package fr.aviv.home.data

import fr.aviv.home.domain.Listing
import fr.aviv.home.domain.PropertyType
import javax.inject.Inject

class ListingsJsonTransformer @Inject constructor() {
    companion object {
        private const val PROPERTY_HOME = "Maison - Villa"
    }

    internal fun transformJsonToEntity(
        item: ListingsJsonResponse.ListingResponse,
    ): Listing = Listing(
        bedrooms = item.bedrooms,
        city = item.city,
        id = item.id,
        area = item.area,
        url = item.url,
        price = item.price,
        professional = item.professional,
        propertyType = buildPropertyType(item.propertyType),
        offerType = item.offerType,
        rooms = item.rooms,
    )

    private fun buildPropertyType(
        propertyType: String,
    ): PropertyType =
        if (propertyType == PROPERTY_HOME) {
            PropertyType.HOUSE
        } else {
            PropertyType.APPARTMENT
        }
}
