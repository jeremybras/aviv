package fr.aviv.home.presentation

import android.content.res.Resources
import fr.aviv.R
import fr.aviv.home.domain.ListingsResult
import fr.aviv.utils.CurrencyFormatter
import javax.inject.Inject

class ListingBuilder @Inject constructor(
    private val resources: Resources,
    private val currencyFormatter: CurrencyFormatter,
) {
    fun build(
        item: ListingsResult.Success.Listing,
    ): ListingsUiState.Ready.ListingDisplayModel = ListingsUiState.Ready.ListingDisplayModel(
        id = item.id,
        city = item.city,
        tags = listOf(
            ListingsUiState.Ready.Tag(
                image = R.drawable.ic_euro,
                value = currencyFormatter.format(item.price),
            ),
            ListingsUiState.Ready.Tag(
                image = R.drawable.ic_bedroom,
                value = item.bedrooms.toString(),
            ),
            ListingsUiState.Ready.Tag(
                image = R.drawable.ic_blueprint,
                value = item.rooms.toString(),
            ),
            ListingsUiState.Ready.Tag(
                image = R.drawable.ic_field,
                value = resources.getString(
                    R.string.area,
                    item.area.toString(),
                ),
            ),
            ListingsUiState.Ready.Tag(
                image = when (item.propertyType) {
                    ListingsResult.Success.PropertyType.HOUSE -> R.drawable.ic_home
                    ListingsResult.Success.PropertyType.APPARTMENT -> R.drawable.ic_appartment
                },
            ),
        ),
        shouldShowImage = item.url.isNullOrBlank().not(),
        imageUrl = item.url.orEmpty(),
        professional = item.professional,
    )
}
