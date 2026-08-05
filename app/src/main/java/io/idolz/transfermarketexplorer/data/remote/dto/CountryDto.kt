package io.idolz.transfermarketexplorer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(
    val id: String,
    val name: String,
    val flag: String
)