package io.idolz.transfermarketexplorer.domain.repository

import io.idolz.transfermarketexplorer.domain.model.Country
import io.idolz.transfermarketexplorer.domain.model.League
import io.idolz.transfermarketexplorer.domain.model.Player
import io.idolz.transfermarketexplorer.domain.model.Team
import kotlinx.coroutines.flow.Flow

interface TransfermarketRepository {
    fun getCountries(): Flow<List<Country>>
    fun getLeagues(countryId: String): Flow<List<League>>
    fun getTeams(leagueId: String): Flow<List<Team>>
    fun getPlayers(teamId: String): Flow<List<Player>>
    fun getPlayerDetails(playerId: String): Flow<Player?>
}