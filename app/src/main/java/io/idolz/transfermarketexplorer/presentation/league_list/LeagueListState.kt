package io.idolz.transfermarketexplorer.presentation.league_list

import io.idolz.transfermarketexplorer.domain.model.League

data class LeagueListState(
    val leagues: List<League> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)