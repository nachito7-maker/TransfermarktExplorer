package io.idolz.transfermarketexplorer.data.remote

import io.idolz.transfermarketexplorer.data.remote.dto.MarketValueResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TransfermarktCeApi {
    @GET("marketValueDevelopment/graph/{playerId}")
    suspend fun getMarketValueHistory(@Path("playerId") playerId: String): MarketValueResponse
}