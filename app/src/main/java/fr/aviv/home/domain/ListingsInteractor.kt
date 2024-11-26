package fr.aviv.home.domain

import fr.aviv.home.data.ListingsRepository
import javax.inject.Inject

class ListingsInteractor @Inject constructor(
    private val repository: ListingsRepository,
) {
    fun loadListings(): ListingsResult = repository.loadListings()
}
