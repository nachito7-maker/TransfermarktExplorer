package io.idolz.transfermarketexplorer.presentation.transfers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import io.idolz.transfermarketexplorer.domain.model.Transfer
import io.idolz.transfermarketexplorer.presentation.components.ErrorView
import io.idolz.transfermarketexplorer.presentation.components.TransfermarketTopAppBar
import io.idolz.transfermarketexplorer.presentation.components.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransfersScreen(
    viewModel: TransfersViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Recientes", "Top Histórico")

    Scaffold(
        topBar = {
            Column {
                TransfermarketTopAppBar(title = "Mercado de Fichajes")
                SecondaryTabRow(selectedTabIndex = selectedTab) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = viewModel::refresh,
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            val transfers = if (selectedTab == 0) state.recentTransfers else state.topTransfers
            val error = state.error

            if (state.isLoading) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(10) { TransferShimmerItem() }
                }
            } else if (error != null) {
                ErrorView(message = error, onRetry = viewModel::refresh)
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(transfers) { transfer ->
                        TransferItem(transfer = transfer)
                    }
                }
            }
        }
    }
}

@Composable
fun TransferItem(transfer: Transfer) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = transfer.playerImageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = transfer.playerName, style = MaterialTheme.typography.titleMedium)
                    Text(text = transfer.date, style = MaterialTheme.typography.bodySmall)
                }
                Text(
                    text = transfer.fee,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamLogoName(logo = transfer.fromTeamLogoUrl, name = transfer.fromTeam)
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
                TeamLogoName(logo = transfer.toTeamLogoUrl, name = transfer.toTeam)
            }
        }
    }
}

@Composable
fun TeamLogoName(logo: String, name: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(model = logo, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = name, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun TransferShimmerItem() {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Box(modifier = Modifier.size(50.dp).shimmer())
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Box(modifier = Modifier.height(20.dp).width(120.dp).shimmer())
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier.height(14.dp).width(80.dp).shimmer())
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.height(24.dp).fillMaxWidth().shimmer())
        }
    }
}
