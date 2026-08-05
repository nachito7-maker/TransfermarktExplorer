package io.idolz.transfermarketexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.idolz.transfermarketexplorer.presentation.country_list.CountryListScreen
import io.idolz.transfermarketexplorer.presentation.league_list.LeagueListScreen
import io.idolz.transfermarketexplorer.presentation.navigation.Screen
import io.idolz.transfermarketexplorer.presentation.player_detail.PlayerDetailScreen
import io.idolz.transfermarketexplorer.presentation.player_list.PlayerListScreen
import io.idolz.transfermarketexplorer.presentation.team_list.TeamListScreen
import io.idolz.transfermarketexplorer.ui.theme.TransfermarketExplorerTheme

import io.idolz.transfermarketexplorer.presentation.favorites.FavoritesScreen
import io.idolz.transfermarketexplorer.presentation.transfers.TransfersScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TransfermarketExplorerTheme {
                val navController = rememberNavController()
                val items = listOf(
                    Triple(Screen.CountryList, "Explorar", Icons.Default.Explore),
                    Triple(Screen.Transfers, "Fichajes", Icons.Default.SwapHoriz),
                    Triple(Screen.Favorites, "Favoritos", Icons.Default.Favorite)
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        
                        // Show bottom bar only for top-level destinations
                        val topLevelRoutes = listOf(Screen.CountryList.route, Screen.Transfers.route, Screen.Favorites.route)
                        val showBottomBar = topLevelRoutes.contains(currentDestination?.route)

                        if (showBottomBar) {
                            NavigationBar {
                                items.forEach { (screen, label, icon) ->
                                    NavigationBarItem(
                                        icon = { Icon(icon, contentDescription = label) },
                                        label = { Text(label) },
                                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.CountryList.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.CountryList.route) {
                            CountryListScreen(
                                onCountryClick = { country ->
                                    navController.navigate(Screen.LeagueList.createRoute(country.id))
                                }
                            )
                        }
                        composable(Screen.LeagueList.route) {
                            LeagueListScreen(
                                onLeagueClick = { league ->
                                    navController.navigate(Screen.TeamList.createRoute(league.id))
                                },
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                        composable(Screen.TeamList.route) {
                            TeamListScreen(
                                onTeamClick = { team ->
                                    navController.navigate(Screen.PlayerList.createRoute(team.id))
                                },
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                        composable(Screen.PlayerList.route) {
                            PlayerListScreen(
                                onPlayerClick = { player ->
                                    navController.navigate(Screen.PlayerDetail.createRoute(player.id))
                                },
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                        composable(Screen.PlayerDetail.route) {
                            PlayerDetailScreen(
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(Screen.Transfers.route) {
                            TransfersScreen()
                        }
                        composable(Screen.Favorites.route) {
                            FavoritesScreen(
                                onPlayerClick = { player ->
                                    navController.navigate(Screen.PlayerDetail.createRoute(player.id))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}