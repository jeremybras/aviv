package fr.aviv.home.domain

import fr.aviv.home.data.ListingsRepository
import fr.aviv.home.data.ListingsResponse
import javax.inject.Inject

class ListingsInteractor @Inject constructor(
    private val repository: ListingsRepository,
) {
    fun loadListings(): ListingsResult = when (val result = repository.loadListings()) {
        is ListingsResponse.Success -> ListingsResult.Success(
            items = result.items.map { item ->
                ListingsResult.Success.Listing(
                    bedrooms = item.bedrooms,
                    city = item.city,
                    id = item.id,
                    area = item.area,
                    url = item.url,
                    price = item.price,
                    professional = item.professional,
                    propertyType = when (item.propertyType) {
                        ListingsResponse.Success.PropertyType.HOUSE -> ListingsResult.Success.PropertyType.HOUSE
                        ListingsResponse.Success.PropertyType.APPARTMENT -> ListingsResult.Success.PropertyType.APPARTMENT
                    },
                    offerType = item.offerType,
                    rooms = item.rooms,
                )
            },
        )

        ListingsResponse.Failure -> ListingsResult.Error
    }
}
