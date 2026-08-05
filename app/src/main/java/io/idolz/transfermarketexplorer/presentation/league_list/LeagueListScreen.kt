package io.idolz.transfermarketexplorer.presentation.league_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import io.idolz.transfermarketexplorer.domain.model.League
import io.idolz.transfermarketexplorer.presentation.components.ErrorView
import io.idolz.transfermarketexplorer.presentation.components.TransfermarketTopAppBar
import io.idolz.transfermarketexplorer.presentation.components.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeagueListScreen(
    viewModel: LeagueListViewModel = hiltViewModel(),
    onLeagueClick: (League) -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TransfermarketTopAppBar(
                title = "Leagues",
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = viewModel::refresh,
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            val error = state.error
            if (state.isLoading) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(10) {
                        LeagueShimmerItem()
                    }
                }
            } else if (error != null) {
                ErrorView(
                    message = error,
                    onRetry = viewModel::refresh
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.leagues) { league ->
                        LeagueItem(
                            league = league,
                            onClick = { onLeagueClick(league) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LeagueItem(
    league: League,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = league.logoUrl,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = league.name, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun LeagueShimmerItem() {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(40.dp).shimmer())
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.height(20.dp).fillMaxWidth(0.6f).shimmer())
        }
    }
}
