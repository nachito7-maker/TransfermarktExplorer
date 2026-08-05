package io.idolz.transfermarketexplorer.data.repository

import io.idolz.transfermarketexplorer.data.local.CountryDao
import io.idolz.transfermarketexplorer.data.mapper.toCountry
import io.idolz.transfermarketexplorer.data.mapper.toCountryEntity
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
    private val countryDao: CountryDao
) : TransfermarketRepository {

    override fun getCountries(): Flow<List<Country>> = flow {
        // Emit cached data first
        val cachedFlow = countryDao.getCountries().map { entities ->
            entities.map { it.toCountry() }
        }
        
        try {
            val remoteCountries = api.getCountries()
            countryDao.insertCountries(remoteCountries.map { it.toCountryEntity() })
        } catch (e: Exception) {
            // Log error or handle as needed
        }
        
        emitAll(cachedFlow)
    }

    override fun getLeagues(countryId: String): Flow<List<League>> = flow {
        // Implementation for leagues
        emit(emptyList())
    }

    override fun getTeams(leagueId: String): Flow<List<Team>> = flow {
        // Implementation for teams
        emit(emptyList())
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