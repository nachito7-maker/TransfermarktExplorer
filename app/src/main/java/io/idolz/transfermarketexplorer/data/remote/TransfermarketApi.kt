package io.idolz.transfermarketexplorer.data.remote

import io.idolz.transfermarketexplorer.data.remote.dto.CountryDto
import io.idolz.transfermarketexplorer.data.remote.dto.LeagueDto
import retrofit2.http.GET
import retrofit2.http.Path

interface TransfermarketApi {
    @GET("countries")
    suspend fun getCountries(): List<CountryDto>

    @GET("countries/{countryId}/leagues")
    suspend fun getLeagues(@Path("countryId") countryId: String): List<LeagueDto>
}