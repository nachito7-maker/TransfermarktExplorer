package io.idolz.transfermarketexplorer.data.repository

import io.idolz.transfermarketexplorer.data.local.entity.LeagueEntity
import io.idolz.transfermarketexplorer.data.local.entity.PlayerEntity
import io.idolz.transfermarketexplorer.data.local.entity.TeamEntity
import io.idolz.transfermarketexplorer.data.local.CountryDao
import io.idolz.transfermarketexplorer.data.local.LeagueDao
import io.idolz.transfermarketexplorer.data.local.PlayerDao
import io.idolz.transfermarketexplorer.data.local.TeamDao
import io.idolz.transfermarketexplorer.data.mapper.*
import io.idolz.transfermarketexplorer.data.remote.TheSportsDbApi
import io.idolz.transfermarketexplorer.domain.model.*
import io.idolz.transfermarketexplorer.domain.repository.TransfermarketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransfermarketRepositoryImpl @Inject constructor(
    private val api: TheSportsDbApi,
    private val countryDao: CountryDao,
    private val leagueDao: LeagueDao,
    private val teamDao: TeamDao,
    private val playerDao: PlayerDao
) : TransfermarketRepository {

    private val countries = listOf(
        Country("Spain", "España", "https://flagcdn.com/w320/es.png"),
        Country("England", "Inglaterra", "https://flagcdn.com/w320/gb.png"),
        Country("Germany", "Alemania", "https://flagcdn.com/w320/de.png"),
        Country("Italy", "Italia", "https://flagcdn.com/w320/it.png"),
        Country("France", "Francia", "https://flagcdn.com/w320/fr.png"),
        Country("Portugal", "Portugal", "https://flagcdn.com/w320/pt.png"),
        Country("Brazil", "Brasil", "https://flagcdn.com/w320/br.png"),
        Country("Argentina", "Argentina", "https://flagcdn.com/w320/ar.png")
    )

    override fun getCountries(): Flow<List<Country>> = flow {
        emit(countries)
    }

    override fun getLeagues(countryId: String): Flow<List<League>> = flow {
        // Emitir lo que haya en cache primero una sola vez
        val localLeagues = leagueDao.getLeagues(countryId).first()
        emit(localLeagues.map { it.toLeague() })

        try {
            val response = api.getLeagues(countryId)
            val remoteLeagues = (response.countries ?: response.countrys ?: response.leagues)
                ?.map { it.toLeagueEntity(countryId) } ?: emptyList()
            
            if (remoteLeagues.isNotEmpty()) {
                leagueDao.insertLeagues(remoteLeagues)
            }
        } catch (e: Exception) {
            android.util.Log.e("Transfermarket", "Error fetching leagues: ${e.message}")
        }
        
        // Seguir observando cambios en la base de datos
        emitAll(leagueDao.getLeagues(countryId).map { entities -> 
            entities.map { it.toLeague() } 
        })
    }

    override fun getTeams(leagueId: String): Flow<List<Team>> = flow {
        // Emitir cache inicial
        val localTeams = teamDao.getTeams(leagueId).first()
        emit(localTeams.map { it.toTeam() })

        val league = leagueDao.getLeagueById(leagueId)
        val leagueName = league?.name ?: ""

        if (leagueName.isNotBlank()) {
            try {
                // 1. Obtener los 10 primeros (límite API gratuita)
                val response = api.getTeams(leagueName)
                val remoteTeams = response.teams?.map { it.toTeamEntity(leagueId) } ?: emptyList()
                
                // 2. Smart Seeding: Si es una liga top y tenemos pocos equipos, pedimos los "olvidados"
                val extraTeams = mutableListOf<TeamEntity>()
                if (leagueId == "4335" || leagueName.contains("La Liga", ignoreCase = true)) {
                    val missingIds = listOf("133738", "133733", "133735", "133737", "133740", "133736", "133730", "133939", "134215", "133728")
                    missingIds.forEach { id ->
                        try {
                            val teamResponse = api.getTeam(id)
                            teamResponse.teams?.firstOrNull()?.toTeamEntity(leagueId)?.let { extraTeams.add(it) }
                        } catch (e: Exception) { /* Skip if fails */ }
                    }
                }

                val allTeams = (remoteTeams + extraTeams).distinctBy { it.id }
                if (allTeams.isNotEmpty()) {
                    teamDao.insertTeams(allTeams)
                }
            } catch (e: Exception) {
                android.util.Log.e("Transfermarket", "Error fetching teams: ${e.message}")
            }
        }

        emitAll(teamDao.getTeams(leagueId).map { entities -> entities.map { it.toTeam() } })
    }

    override fun getPlayers(teamId: String): Flow<List<Player>> = flow {
        val localPlayers = playerDao.getPlayers(teamId).first()
        emit(localPlayers.map { it.toPlayer() })

        try {
            // 1. Obtener por ID (Límite 10)
            val response = api.getPlayers(teamId)
            val remotePlayers = response.player?.map { it.toPlayerEntity(teamId) } ?: emptyList()
            
            // 2. Intentar buscar por nombre de equipo si hay pocos jugadores
            val extraPlayers = mutableListOf<PlayerEntity>()
            if (remotePlayers.size in 1..10) {
                val team = teamDao.getTeams("").first().find { it.id == teamId }
                team?.name?.let { teamName ->
                    try {
                        val searchResponse = api.searchPlayers(teamName)
                        searchResponse.player?.map { it.toPlayerEntity(teamId) }?.let { 
                            extraPlayers.addAll(it) 
                        }
                    } catch (e: Exception) { }
                }
            }

            val allPlayers = (remotePlayers + extraPlayers).distinctBy { it.id }
            if (allPlayers.isNotEmpty()) {
                playerDao.insertPlayers(allPlayers)
            }
        } catch (e: Exception) {
            android.util.Log.e("Transfermarket", "Error fetching players: ${e.message}")
        }

        emitAll(playerDao.getPlayers(teamId).map { entities -> entities.map { it.toPlayer() } })
    }

    override fun getPlayerDetails(playerId: String): Flow<Player?> = flow {
        val localPlayer = playerDao.getPlayerById(playerId).first()
        emit(localPlayer?.toPlayer())

        try {
            val response = api.getPlayerDetails(playerId)
            val player = response.players?.firstOrNull()
            if (player != null) {
                playerDao.insertPlayer(player.toPlayerEntity(""))
            }
        } catch (e: Exception) {
            android.util.Log.e("Transfermarket", "Error fetching player details: ${e.message}")
        }

        emitAll(playerDao.getPlayerById(playerId).map { it?.toPlayer() })
    }

    override fun getFavoritePlayers(): Flow<List<Player>> {
        return playerDao.getFavoritePlayers().map { entities ->
            entities.map { it.toPlayer() }
        }
    }

    override fun isPlayerFavorite(playerId: String): Flow<Boolean> {
        return playerDao.isPlayerFavorite(playerId)
    }

    override suspend fun toggleFavorite(player: Player) {
        val isFavorite = playerDao.isPlayerFavorite(player.id).first()
        if (isFavorite) {
            playerDao.deleteFavoritePlayer(player.id)
        } else {
            playerDao.insertFavoritePlayer(player.toFavoritePlayerEntity())
        }
    }

    override fun getRecentTransfers(): Flow<List<Transfer>> = flow {
        // TheSportsDB doesn't have a direct "recent transfers" endpoint in the free tier easily.
        // We'll keep mock for now or use a different endpoint if found.
        emit(emptyList())
    }

    override fun getTopTransfers(): Flow<List<Transfer>> = flow {
        emit(emptyList())
    }
}