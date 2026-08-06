package io.idolz.transfermarketexplorer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MarketValuePointDto(
    val x: Long, // timestamp
    val y: Long, // value in cents or basic units
    val mw: String, // formatted market value
    val datum_mw: String, // date
    val verein: String // club
)

@Serializable
data class MarketValueResponse(
    val list: List<MarketValuePointDto>,
    val current: String? = null,
    val highest: String? = null
)