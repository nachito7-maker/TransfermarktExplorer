package io.idolz.transfermarketexplorer.presentation.team_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import io.idolz.transfermarketexplorer.domain.model.Team

@OptIn(ExperimentalMaterial3Api::class)
@androidx.compose.runtime.Composable
fun TeamListScreen(
    viewModel: TeamListViewModel = hiltViewModel(),
    onTeamClick: (Team) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Teams") })
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.teams) { team ->
                    TeamItem(
                        team = team,
                        onClick = { onTeamClick(team) }
                    )
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            state.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun TeamItem(
    team: Team,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = team.logoUrl,
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = team.name, style = MaterialTheme.typography.titleMedium)
            team.marketValue?.let {
                Text(text = it, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}