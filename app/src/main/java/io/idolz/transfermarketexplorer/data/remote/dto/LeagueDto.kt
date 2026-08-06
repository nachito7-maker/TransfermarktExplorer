package io.idolz.transfermarketexplorer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LeagueDto(
    val idLeague: String,
    val strLeague: String,
    val strBadge: String? = null
)

@Serializable
data class LeagueResponse(
    val countries: List<LeagueDto>? = null,
    val countrys: List<LeagueDto>? = null,
    val leagues: List<LeagueDto>? = null
)