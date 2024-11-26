package fr.aviv.home.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AvivService {
    @GET("listings.json")
    fun getListings(): Call<ListingsJsonResponse>

    @GET("listings/{listingId}.json")
    fun getListing(
        @Path("listingId") listingId: Int,
    ): Call<ListingsJsonResponse.ListingResponse>
}
