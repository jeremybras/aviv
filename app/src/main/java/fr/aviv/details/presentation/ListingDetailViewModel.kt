package fr.aviv.details.presentation

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class ListingDetailViewModel @Inject constructor(
    @Named("ioDispatcher") private val dispatcher: CoroutineDispatcher,
) {

}