package io.github.iamvukasin.hacktravel.api

import io.github.iamvukasin.hacktravel.models.Flight
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val SERVER_ADDRESS = "http://192.168.15.112:5001"

interface FlightService {
    @GET("get")
    fun getFlights(
        @Query("src") fromLocation: String,
        @Query("dst") toLocation: String,
        @Query("from_date") fromDate: String,
        @Query("to_date") toDate: String,
        @Query("num_people") numAdults: Int,
        @Query("num_children") numChildren: Int,
        @Query("pets") pets: String
    ): Call<List<Flight>>
}

fun createFlightService(): FlightService {
    val retrofit = Retrofit.Builder()
        .baseUrl(SERVER_ADDRESS)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(FlightService::class.java)
}
