package fr.aviv.home.data

import java.io.IOException
import javax.inject.Inject

class ListingsRepository @Inject constructor(
    private val service: AvivService,
    private val transformer: ListingsJsonTransformer,
) {
    fun loadListings(): ListingsResponse {
        return try {
            val request = service.getListings()
            val result = request.execute()
            if (result.isSuccessful) {
                result.body()?.let { body ->
                    return transformer.transformJsonToEntity(body)
                } ?: ListingsResponse.Failure
            } else {
                ListingsResponse.Failure
            }
        } catch (_: IOException) {
            ListingsResponse.Failure
        }
    }
}
