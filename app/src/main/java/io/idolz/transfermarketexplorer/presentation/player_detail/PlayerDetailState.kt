package io.idolz.transfermarketexplorer.presentation.player_detail

import io.idolz.transfermarketexplorer.domain.model.Player

data class PlayerDetailState(
    val player: Player? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)