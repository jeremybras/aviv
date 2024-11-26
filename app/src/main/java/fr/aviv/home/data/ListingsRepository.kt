package fr.aviv.home.data

import fr.aviv.home.domain.ListingsResult
import java.io.IOException
import javax.inject.Inject

class ListingsRepository @Inject constructor(
    private val service: AvivService,
    private val transformer: ListingsJsonTransformer,
) {
    fun loadListings(): ListingsResult {
        return try {
            val request = service.getListings()
            val result = request.execute()
            if (result.isSuccessful) {
                result.body()?.let { body ->
                    return transformer.transformJsonToEntity(body)
                } ?: ListingsResult.Error
            } else {
                ListingsResult.Error
            }
        } catch (_: IOException) {
            ListingsResult.Error
        }
    }
}
