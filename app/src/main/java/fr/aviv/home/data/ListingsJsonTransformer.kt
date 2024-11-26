package fr.aviv.home.data

import fr.aviv.home.domain.ListingsResult
import javax.inject.Inject

class ListingsJsonTransformer @Inject constructor() {
    companion object {
        private const val PROPERTY_HOME = "Maison - Villa"
    }

    internal fun transformJsonToEntity(body: ListingsJsonResponse): ListingsResult.Success =
        ListingsResult.Success(
            items = body.items.map { item ->
                ListingsResult.Success.Listing(
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
            },
        )

    private fun buildPropertyType(
        propertyType: String,
    ): ListingsResult.Success.PropertyType =
        if (propertyType == PROPERTY_HOME) {
            ListingsResult.Success.PropertyType.HOUSE
        } else {
            ListingsResult.Success.PropertyType.APPARTMENT
        }
}
