package io.idolz.transfermarketexplorer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    val idPlayer: String,
    val idTeam: String? = null,
    val strPlayer: String,
    val strPosition: String? = null,
    val strNationality: String? = null,
    val dateBorn: String? = null,
    val strCutout: String? = null,
    val strThumb: String? = null,
    val strDescriptionEN: String? = null
)

@Serializable
data class PlayerResponse(
    val player: List<PlayerDto>? = null
)

@Serializable
data class PlayerDetailResponse(
    val players: List<PlayerDto>? = null
)