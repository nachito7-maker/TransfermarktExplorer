package io.idolz.transfermarketexplorer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TeamDto(
    val idTeam: String,
    val strTeam: String,
    val strBadge: String? = null,
    val strLogo: String? = null,
    val strTeamBadge: String? = null
)

@Serializable
data class TeamResponse(
    val teams: List<TeamDto>? = null
)