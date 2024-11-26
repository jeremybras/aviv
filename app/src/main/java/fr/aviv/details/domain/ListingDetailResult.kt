package fr.aviv.details.domain

import fr.aviv.home.domain.Listing

sealed interface ListingDetailResult {
    data class Success(
        val item: Listing,
    ) : ListingDetailResult

    data object Error : ListingDetailResult
}
