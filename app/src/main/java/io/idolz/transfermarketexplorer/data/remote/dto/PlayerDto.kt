package io.idolz.transfermarketexplorer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    val id: String,
    val name: String,
    val image: String,
    val position: String,
    val nationality: String,
    val age: Int,
    val marketValue: String
)