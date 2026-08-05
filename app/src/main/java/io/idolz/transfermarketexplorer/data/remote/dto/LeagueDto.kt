package io.idolz.transfermarketexplorer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LeagueDto(
    val id: String,
    val name: String,
    val logo: String
)