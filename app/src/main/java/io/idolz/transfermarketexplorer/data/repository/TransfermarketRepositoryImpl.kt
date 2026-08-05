package io.idolz.transfermarketexplorer.data.repository

import io.idolz.transfermarketexplorer.data.local.CountryDao
import io.idolz.transfermarketexplorer.data.local.LeagueDao
import io.idolz.transfermarketexplorer.data.local.PlayerDao
import io.idolz.transfermarketexplorer.data.local.TeamDao
import io.idolz.transfermarketexplorer.data.mapper.toCountry
import io.idolz.transfermarketexplorer.data.mapper.toCountryEntity
import io.idolz.transfermarketexplorer.data.mapper.toFavoritePlayerEntity
import io.idolz.transfermarketexplorer.data.mapper.toLeague
import io.idolz.transfermarketexplorer.data.mapper.toLeagueEntity
import io.idolz.transfermarketexplorer.data.mapper.toPlayer
import io.idolz.transfermarketexplorer.data.mapper.toTeam
import io.idolz.transfermarketexplorer.data.mapper.toTeamEntity
import io.idolz.transfermarketexplorer.data.remote.TransfermarketApi
import io.idolz.transfermarketexplorer.domain.model.Country
import io.idolz.transfermarketexplorer.domain.model.League
import io.idolz.transfermarketexplorer.domain.model.Player
import io.idolz.transfermarketexplorer.domain.model.Team
import io.idolz.transfermarketexplorer.domain.model.Transfer
import io.idolz.transfermarketexplorer.domain.repository.TransfermarketRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class TransfermarketRepositoryImpl @Inject constructor(
    private val api: TransfermarketApi,
    private val countryDao: CountryDao,
    private val leagueDao: LeagueDao,
    private val teamDao: TeamDao,
    private val playerDao: PlayerDao
) : TransfermarketRepository {

    override fun getCountries(): Flow<List<Country>> = flow {
        delay(1000) // Simular latencia de red
        val mockCountries = listOf(
            Country("1", "España", "https://flagcdn.com/w320/es.png"),
            Country("2", "Inglaterra", "https://flagcdn.com/w320/gb.png"),
            Country("3", "Alemania", "https://flagcdn.com/w320/de.png"),
            Country("4", "Italia", "https://flagcdn.com/w320/it.png"),
            Country("5", "Francia", "https://flagcdn.com/w320/fr.png")
        )
        emit(mockCountries)
    }

    override fun getLeagues(countryId: String): Flow<List<League>> = flow {
        delay(800)
        val mockLeagues = when(countryId) {
            "1" -> listOf(
                League("l1", "LaLiga", "1", "https://flagcdn.com/w320/es.png"),
                League("l2", "Segunda División", "1", "https://flagcdn.com/w320/es.png"),
                League("l10", "Copa del Rey", "1", "https://flagcdn.com/w320/es.png")
            )
            "2" -> listOf(
                League("l3", "Premier League", "2", "https://flagcdn.com/w320/gb.png"),
                League("l4", "Championship", "2", "https://flagcdn.com/w320/gb.png"),
                League("l11", "FA Cup", "2", "https://flagcdn.com/w320/gb.png")
            )
            "3" -> listOf(
                League("l5", "Bundesliga", "3", "https://flagcdn.com/w320/de.png"),
                League("l6", "2. Bundesliga", "3", "https://flagcdn.com/w320/de.png")
            )
            "4" -> listOf(
                League("l7", "Serie A", "4", "https://flagcdn.com/w320/it.png"),
                League("l8", "Serie B", "4", "https://flagcdn.com/w320/it.png")
            )
            "5" -> listOf(
                League("l9", "Ligue 1", "5", "https://flagcdn.com/w320/fr.png")
            )
            else -> emptyList()
        }
        emit(mockLeagues)
    }

    override fun getTeams(leagueId: String): Flow<List<Team>> = flow {
        delay(800)
        val mockTeams = when(leagueId) {
            "l1" -> listOf(
                Team("t1", "Real Madrid", "l1", "https://tm.example/rm.png", "€1.36bn"),
                Team("t2", "FC Barcelona", "l1", "https://tm.example/barca.png", "€944.00m"),
                Team("t3", "Atlético de Madrid", "l1", "https://tm.example/atleti.png", "€510.50m"),
                Team("t4", "Real Sociedad", "l1", "https://tm.example/rs.png", "€431.20m"),
                Team("t5", "Athletic Club", "l1", "https://tm.example/athletic.png", "€318.70m"),
                Team("t6", "Girona FC", "l1", "https://tm.example/girona.png", "€208.00m"),
                Team("t7", "Real Betis", "l1", "https://tm.example/betis.png", "€189.50m"),
                Team("t8", "Villarreal CF", "l1", "https://tm.example/villarreal.png", "€185.70m")
            )
            "l3" -> listOf(
                Team("t10", "Manchester City", "l3", "https://tm.example/mcity.png", "€1.26bn"),
                Team("t11", "Arsenal FC", "l3", "https://tm.example/arsenal.png", "€1.17bn"),
                Team("t12", "Liverpool FC", "l3", "https://tm.example/liverpool.png", "€923.00m")
            )
            else -> emptyList()
        }
        emit(mockTeams)
    }

    override fun getPlayers(teamId: String): Flow<List<Player>> = flow {
        delay(800)
        val mockPlayers = when(teamId) {
            "t1" -> listOf(
                // Porteros
                Player("p10", "Thibaut Courtois", "t1", "Portero", "Bélgica", 32, "€28.00m", "https://tm.example/courtois.png"),
                Player("p11", "Andriy Lunin", "t1", "Portero", "Ucrania", 25, "€25.00m", "https://tm.example/lunin.png"),
                // Defensas
                Player("p12", "Éder Militão", "t1", "Defensa Central", "Brasil", 26, "€60.00m", "https://tm.example/militao.png"),
                Player("p13", "Antonio Rüdiger", "t1", "Defensa Central", "Alemania", 31, "€25.00m", "https://tm.example/rudiger.png"),
                Player("p14", "Dani Carvajal", "t1", "Lateral Derecho", "España", 32, "€12.00m", "https://tm.example/carvajal.png"),
                // Mediocentros
                Player("p2", "Jude Bellingham", "t1", "Mediocentro", "Inglaterra", 21, "€180.00m", "https://tm.example/jude.png"),
                Player("p15", "Federico Valverde", "t1", "Mediocentro", "Uruguay", 26, "€120.00m", "https://tm.example/valverde.png"),
                Player("p16", "Eduardo Camavinga", "t1", "Mediocentro", "Francia", 21, "€100.00m", "https://tm.example/camavinga.png"),
                Player("p17", "Aurélien Tchouaméni", "t1", "Pivote", "Francia", 24, "€100.00m", "https://tm.example/tchouameni.png"),
                // Delanteros
                Player("p1", "Vinícius Júnior", "t1", "Extremo Izquierdo", "Brasil", 24, "€180.00m", "https://tm.example/vini.png"),
                Player("p3", "Kylian Mbappé", "t1", "Delantero", "Francia", 25, "€180.00m", "https://tm.example/mbappe.png"),
                Player("p18", "Rodrygo", "t1", "Extremo Derecho", "Brasil", 23, "€110.00m", "https://tm.example/rodrygo.png"),
                Player("p19", "Endrick", "t1", "Delantero", "Brasil", 18, "€60.00m", "https://tm.example/endrick.png")
            )
            else -> emptyList()
        }
        emit(mockPlayers)
    }

    override fun getPlayerDetails(playerId: String): Flow<Player?> = flow {
        delay(500)
        val player = when(playerId) {
            "p1" -> Player("p1", "Vinícius Júnior", "t1", "Extremo Izquierdo", "Brasil", 24, "€180.00m", "https://tm.example/vini.png")
            "p2" -> Player("p2", "Jude Bellingham", "t1", "Mediocentro", "Inglaterra", 21, "€180.00m", "https://tm.example/jude.png")
            "p3" -> Player("p3", "Kylian Mbappé", "t1", "Delantero", "Francia", 25, "€180.00m", "https://tm.example/mbappe.png")
            else -> Player(playerId, "Jugador Desconocido", "", "N/A", "N/A", 0, "€0", "")
        }
        emit(player)
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
        delay(1000)
        val mockTransfers = listOf(
            Transfer("tr1", "Kylian Mbappé", "https://tm.example/mbappe.png", "Paris SG", "https://tm.example/psg.png", "Real Madrid", "https://tm.example/rm.png", "€180.00m", "Libre", "01/07/2024"),
            Transfer("tr2", "Julián Álvarez", "https://tm.example/alvarez.png", "Man. City", "https://tm.example/mcity.png", "Atlético Madrid", "https://tm.example/atleti.png", "€90.00m", "€75.00m", "12/08/2024"),
            Transfer("tr3", "Dominic Solanke", "https://tm.example/solanke.png", "Bournemouth", "https://tm.example/bmouth.png", "Tottenham", "https://tm.example/spurs.png", "€40.00m", "€64.30m", "10/08/2024")
        )
        emit(mockTransfers)
    }

    override fun getTopTransfers(): Flow<List<Transfer>> = flow {
        delay(1000)
        val mockTopTransfers = listOf(
            Transfer("h1", "Neymar", "https://tm.example/neymar.png", "FC Barcelona", "https://tm.example/barca.png", "Paris SG", "https://tm.example/psg.png", "€100.00m", "€222.00m", "03/08/2017"),
            Transfer("h2", "Kylian Mbappé", "https://tm.example/mbappe.png", "Monaco", "https://tm.example/monaco.png", "Paris SG", "https://tm.example/psg.png", "€120.00m", "€180.00m", "01/07/2018"),
            Transfer("h3", "Philippe Coutinho", "https://tm.example/coutinho.png", "Liverpool", "https://tm.example/liverpool.png", "FC Barcelona", "https://tm.example/barca.png", "€90.00m", "€135.00m", "06/01/2018"),
            Transfer("h4", "Ousmane Dembélé", "https://tm.example/dembele.png", "B. Dortmund", "https://tm.example/bvb.png", "FC Barcelona", "https://tm.example/barca.png", "€33.00m", "€135.00m", "25/08/2017"),
            Transfer("h5", "João Félix", "https://tm.example/felix.png", "Benfica", "https://tm.example/benfica.png", "Atlético Madrid", "https://tm.example/atleti.png", "€70.00m", "€127.20m", "03/07/2019")
        )
        emit(mockTopTransfers)
    }
}