package fr.aviv.home.presentation

import android.content.res.Resources
import fr.aviv.R
import fr.aviv.home.domain.Listing
import fr.aviv.home.domain.PropertyType
import fr.aviv.utils.CurrencyFormatter
import javax.inject.Inject

class ListingBuilder @Inject constructor(
    private val resources: Resources,
    private val currencyFormatter: CurrencyFormatter,
) {
    fun build(
        item: Listing,
    ): ListingDisplayModel = ListingDisplayModel(
        id = item.id,
        city = item.city,
        tags = listOf(
            TagDisplayModel(
                title = resources.getString(R.string.tag_price_title),
                image = R.drawable.ic_euro,
                value = currencyFormatter.format(item.price),
            ),
            TagDisplayModel(
                title = resources.getString(R.string.tag_bedrooms_title),
                image = R.drawable.ic_bedroom,
                value = item.bedrooms.toString(),
            ),
            TagDisplayModel(
                title = resources.getString(R.string.tag_rooms_title),
                image = R.drawable.ic_blueprint,
                value = item.rooms.toString(),
            ),
            TagDisplayModel(
                title = resources.getString(R.string.tag_area_title),
                image = R.drawable.ic_field,
                value = resources.getString(
                    R.string.area,
                    item.area.toString(),
                ),
            ),
            TagDisplayModel(
                title = when (item.propertyType) {
                    PropertyType.HOUSE -> resources.getString(R.string.tag_type_title_house)
                    PropertyType.APPARTMENT -> resources.getString(R.string.tag_type_title_appartment)
                },
                image = when (item.propertyType) {
                    PropertyType.HOUSE -> R.drawable.ic_home
                    PropertyType.APPARTMENT -> R.drawable.ic_appartment
                },
            ),
        ),
        shouldShowImage = item.url.isNullOrBlank().not(),
        imageUrl = item.url.orEmpty(),
        professional = item.professional,
    )
}
