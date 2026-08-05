package io.idolz.transfermarketexplorer.domain.model

data class Team(
    val id: String,
    val name: String,
    val leagueId: String,
    val logoUrl: String,
    val marketValue: String? = null
)