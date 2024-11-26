package fr.aviv.details.data

import fr.aviv.details.domain.ListingDetailResult
import fr.aviv.home.data.AvivService
import fr.aviv.home.data.ListingsJsonTransformer
import java.io.IOException
import javax.inject.Inject

class ListingDetailRepository @Inject constructor(
    private val service: AvivService,
    private val transformer: ListingsJsonTransformer,
) {

    fun loadListing(listingId: Int): ListingDetailResult {
        return try {
            val request = service.getListing(listingId)
            val result = request.execute()
            if (result.isSuccessful) {
                result.body()?.let { body ->
                    return ListingDetailResult.Success(
                        item = transformer.transformJsonToEntity(body)
                    )
                } ?: ListingDetailResult.Error
            } else {
                ListingDetailResult.Error
            }
        } catch (_: IOException) {
            ListingDetailResult.Error
        }
    }
}
