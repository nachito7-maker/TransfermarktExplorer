package io.idolz.transfermarketexplorer.presentation.navigation

sealed class Screen(val route: String) {
    object CountryList : Screen("country_list")
    object LeagueList : Screen("league_list/{countryId}") {
        fun createRoute(countryId: String) = "league_list/$countryId"
    }
    object TeamList : Screen("team_list/{leagueId}") {
        fun createRoute(leagueId: String) = "team_list/$leagueId"
    }
    object PlayerList : Screen("player_list/{teamId}") {
        fun createRoute(teamId: String) = "player_list/$teamId"
    }
    object PlayerDetail : Screen("player_detail/{playerId}") {
        fun createRoute(playerId: String) = "player_detail/$playerId"
    }
    object Transfers : Screen("transfers")
    object Favorites : Screen("favorites")
}