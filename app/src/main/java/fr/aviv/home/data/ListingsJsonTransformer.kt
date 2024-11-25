package fr.aviv.home.data

import javax.inject.Inject

class ListingsJsonTransformer
@Inject
constructor() {
    companion object {
        private const val PROPERTY_HOME = "Maison - Villa"
    }

    internal fun transformJsonToEntity(body: ListingsJsonResponse): ListingsResponse.Success =
        ListingsResponse.Success(
            items = body.items.map { item ->
                ListingsResponse.Success.Listing(
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
    ): ListingsResponse.Success.PropertyType =
        if (propertyType == PROPERTY_HOME) {
            ListingsResponse.Success.PropertyType.HOUSE
        } else {
            ListingsResponse.Success.PropertyType.APPARTMENT
        }
}
