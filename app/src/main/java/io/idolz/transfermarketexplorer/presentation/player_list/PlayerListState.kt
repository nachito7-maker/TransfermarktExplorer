package io.idolz.transfermarketexplorer.presentation.player_list

import io.idolz.transfermarketexplorer.domain.model.Player

data class PlayerListState(
    val players: List<Player> = emptyList(),
    val groupedPlayers: Map<String, List<Player>> = emptyMap(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val teamId: String = "",
    val error: String? = null
)