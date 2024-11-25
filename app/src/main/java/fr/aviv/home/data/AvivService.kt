package fr.aviv.home.data

import retrofit2.Call
import retrofit2.http.GET

interface AvivService {
    @GET("listings.json")
    fun getListings(): Call<ListingsJsonResponse>
}
