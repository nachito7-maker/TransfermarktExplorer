package io.idolz.transfermarketexplorer.data.repository

import io.idolz.transfermarketexplorer.data.local.CountryDao
import io.idolz.transfermarketexplorer.data.local.LeagueDao
import io.idolz.transfermarketexplorer.data.local.TeamDao
import io.idolz.transfermarketexplorer.data.mapper.toCountry
import io.idolz.transfermarketexplorer.data.mapper.toCountryEntity
import io.idolz.transfermarketexplorer.data.mapper.toLeague
import io.idolz.transfermarketexplorer.data.mapper.toLeagueEntity
import io.idolz.transfermarketexplorer.data.mapper.toTeam
import io.idolz.transfermarketexplorer.data.mapper.toTeamEntity
import io.idolz.transfermarketexplorer.data.remote.TransfermarketApi
import io.idolz.transfermarketexplorer.domain.model.Country
import io.idolz.transfermarketexplorer.domain.model.League
import io.idolz.transfermarketexplorer.domain.model.Player
import io.idolz.transfermarketexplorer.domain.model.Team
import io.idolz.transfermarketexplorer.domain.repository.TransfermarketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransfermarketRepositoryImpl @Inject constructor(
    private val api: TransfermarketApi,
    private val countryDao: CountryDao,
    private val leagueDao: LeagueDao,
    private val teamDao: TeamDao
) : TransfermarketRepository {

    override fun getCountries(): Flow<List<Country>> = flow {
        val cachedFlow = countryDao.getCountries().map { entities ->
            entities.map { it.toCountry() }
        }
        
        try {
            val remoteCountries = api.getCountries()
            countryDao.insertCountries(remoteCountries.map { it.toCountryEntity() })
        } catch (e: Exception) {
            // Handle error
        }
        
        emitAll(cachedFlow)
    }

    override fun getLeagues(countryId: String): Flow<List<League>> = flow {
        val cachedFlow = leagueDao.getLeagues(countryId).map { entities ->
            entities.map { it.toLeague() }
        }

        try {
            val remoteLeagues = api.getLeagues(countryId)
            leagueDao.insertLeagues(remoteLeagues.map { it.toLeagueEntity(countryId) })
        } catch (e: Exception) {
            // Handle error
        }

        emitAll(cachedFlow)
    }

    override fun getTeams(leagueId: String): Flow<List<Team>> = flow {
        val cachedFlow = teamDao.getTeams(leagueId).map { entities ->
            entities.map { it.toTeam() }
        }

        try {
            val remoteTeams = api.getTeams(leagueId)
            teamDao.insertTeams(remoteTeams.map { it.toTeamEntity(leagueId) })
        } catch (e: Exception) {
            // Handle error
        }

        emitAll(cachedFlow)
    }

    override fun getPlayers(teamId: String): Flow<List<Player>> = flow {
        // Implementation for players
        emit(emptyList())
    }

    override fun getPlayerDetails(playerId: String): Flow<Player?> = flow {
        // Implementation for player details
        emit(null)
    }
}