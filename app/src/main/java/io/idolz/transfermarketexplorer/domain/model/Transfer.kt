package io.idolz.transfermarketexplorer.domain.model

data class Transfer(
    val id: String,
    val playerName: String,
    val playerImageUrl: String,
    val fromTeam: String,
    val fromTeamLogoUrl: String,
    val toTeam: String,
    val toTeamLogoUrl: String,
    val marketValue: String,
    val fee: String,
    val date: String
)