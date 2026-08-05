package io.idolz.transfermarketexplorer.presentation.favorites

import io.idolz.transfermarketexplorer.domain.model.Player

data class FavoritesState(
    val favoritePlayers: List<Player> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)