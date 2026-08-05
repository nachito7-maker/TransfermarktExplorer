package io.idolz.transfermarketexplorer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TeamDto(
    val id: String,
    val name: String,
    val logo: String,
    val marketValue: String? = null
)