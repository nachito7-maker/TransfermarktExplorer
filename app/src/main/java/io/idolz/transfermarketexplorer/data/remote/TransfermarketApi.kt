package io.idolz.transfermarketexplorer.data.remote

import io.idolz.transfermarketexplorer.data.remote.dto.CountryDto
import io.idolz.transfermarketexplorer.data.remote.dto.LeagueDto
import io.idolz.transfermarketexplorer.data.remote.dto.PlayerDto
import io.idolz.transfermarketexplorer.data.remote.dto.TeamDto
import retrofit2.http.GET
import retrofit2.http.Path

interface TransfermarketApi {
    @GET("countries")
    suspend fun getCountries(): List<CountryDto>

    @GET("countries/{countryId}/leagues")
    suspend fun getLeagues(@Path("countryId") countryId: String): List<LeagueDto>

    @GET("leagues/{leagueId}/teams")
    suspend fun getTeams(@Path("leagueId") leagueId: String): List<TeamDto>

    @GET("teams/{teamId}/players")
    suspend fun getPlayers(@Path("teamId") teamId: String): List<PlayerDto>

    @GET("players/{playerId}")
    suspend fun getPlayerDetails(@Path("playerId") playerId: String): PlayerDto
}