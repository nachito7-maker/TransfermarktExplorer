package io.idolz.transfermarketexplorer.data.remote

import io.idolz.transfermarketexplorer.data.remote.dto.CountryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface TransfermarketApi {
    @GET("countries")
    suspend fun getCountries(): List<CountryDto>

    // Add more endpoints as needed
}