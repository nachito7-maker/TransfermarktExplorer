package io.idolz.transfermarketexplorer.presentation.team_list

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
import io.idolz.transfermarketexplorer.domain.model.Team
import io.idolz.transfermarketexplorer.presentation.components.ErrorView
import io.idolz.transfermarketexplorer.presentation.components.TransfermarketTopAppBar
import io.idolz.transfermarketexplorer.presentation.components.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamListScreen(
    viewModel: TeamListViewModel = hiltViewModel(),
    onTeamClick: (Team) -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TransfermarketTopAppBar(
                title = "Teams",
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
                        TeamShimmerItem()
                    }
                }
            } else if (error != null) {
                ErrorView(
                    message = error,
                    onRetry = viewModel::refresh
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.teams) { team ->
                        TeamItem(
                            team = team,
                            onClick = { onTeamClick(team) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TeamItem(
    team: Team,
    onClick: () -> Unit
) {
    ElevatedCard(
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
                model = team.logoUrl,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = team.name, style = MaterialTheme.typography.titleMedium)
                team.marketValue?.let {
                    AssistChip(
                        onClick = { },
                        label = { Text(text = it) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        border = null
                    )
                }
            }
        }
    }
}

@Composable
fun TeamShimmerItem() {
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
            Box(modifier = Modifier.size(50.dp).shimmer())
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Box(modifier = Modifier.height(20.dp).width(150.dp).shimmer())
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.height(32.dp).width(80.dp).shimmer())
            }
        }
    }
}
