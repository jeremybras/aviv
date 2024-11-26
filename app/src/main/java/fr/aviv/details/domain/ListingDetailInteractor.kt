package fr.aviv.details.domain

import fr.aviv.details.data.ListingDetailRepository
import javax.inject.Inject

class ListingDetailInteractor @Inject constructor(
    private val repository: ListingDetailRepository,
) {
    fun loadListing(listingId: Int): ListingDetailResult = repository.loadListing(listingId)
}
