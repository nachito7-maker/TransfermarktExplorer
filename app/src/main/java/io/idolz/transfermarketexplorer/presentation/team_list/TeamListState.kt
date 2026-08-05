package io.idolz.transfermarketexplorer.presentation.team_list

import io.idolz.transfermarketexplorer.domain.model.Team

data class TeamListState(
    val teams: List<Team> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val leagueId: String = "",
    val error: String? = null
)