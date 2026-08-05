package io.idolz.transfermarketexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.idolz.transfermarketexplorer.presentation.country_list.CountryListScreen
import io.idolz.transfermarketexplorer.presentation.navigation.Screen
import io.idolz.transfermarketexplorer.ui.theme.TransfermarketExplorerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TransfermarketExplorerTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
                            // TODO: Implement LeagueListScreen
                        }
                    }
                }
            }
        }
    }
}