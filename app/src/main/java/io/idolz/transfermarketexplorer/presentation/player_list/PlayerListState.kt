package io.idolz.transfermarketexplorer.presentation.player_list

import io.idolz.transfermarketexplorer.domain.model.Player

data class PlayerListState(
    val players: List<Player> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)