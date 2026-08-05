package io.idolz.transfermarketexplorer.presentation.player_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import io.idolz.transfermarketexplorer.domain.model.Player
import io.idolz.transfermarketexplorer.presentation.components.ErrorView
import io.idolz.transfermarketexplorer.presentation.components.PlayerItem
import io.idolz.transfermarketexplorer.presentation.components.TransfermarketTopAppBar
import io.idolz.transfermarketexplorer.presentation.components.shimmer

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PlayerListScreen(
    viewModel: PlayerListViewModel = hiltViewModel(),
    onPlayerClick: (Player) -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TransfermarketTopAppBar(
                title = "Players",
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
                        PlayerShimmerItem()
                    }
                }
            } else if (error != null) {
                ErrorView(
                    message = error,
                    onRetry = viewModel::refresh
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    state.groupedPlayers.forEach { (position, players) ->
                        stickyHeader {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = position,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        items(players) { player ->
                            PlayerItem(
                                player = player,
                                onClick = { onPlayerClick(player) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerShimmerItem() {
    ElevatedCard(
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
            Box(modifier = Modifier.size(60.dp).shimmer())
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Box(modifier = Modifier.height(20.dp).width(150.dp).shimmer())
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.height(16.dp).width(100.dp).shimmer())
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.height(20.dp).width(80.dp).shimmer())
            }
        }
    }
}
