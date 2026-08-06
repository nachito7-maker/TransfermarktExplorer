package io.idolz.transfermarketexplorer.data.mapper

import io.idolz.transfermarketexplorer.data.local.entity.FavoritePlayerEntity
import io.idolz.transfermarketexplorer.data.local.entity.PlayerEntity
import io.idolz.transfermarketexplorer.data.remote.dto.PlayerDto
import io.idolz.transfermarketexplorer.domain.model.Player

fun PlayerDto.toPlayerEntity(teamId: String): PlayerEntity {
    return PlayerEntity(
        id = idPlayer,
        name = strPlayer,
        teamId = teamId,
        imageUrl = strCutout ?: strThumb ?: "",
        position = strPosition ?: "N/A",
        nationality = strNationality ?: "N/A",
        age = 0, // Age can be calculated from dateBorn
        marketValue = "N/A", // TheSportsDB doesn't usually provide market value
        description = strDescriptionEN
    )
}

fun PlayerEntity.toPlayer(): Player {
    return Player(
        id = id,
        name = name,
        teamId = teamId,
        imageUrl = imageUrl,
        position = position,
        nationality = nationality,
        age = age,
        marketValue = marketValue,
        description = description
    )
}

fun Player.toFavoritePlayerEntity(): FavoritePlayerEntity {
    return FavoritePlayerEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        position = position,
        nationality = nationality,
        age = age,
        marketValue = marketValue,
        description = description
    )
}

fun FavoritePlayerEntity.toPlayer(): Player {
    return Player(
        id = id,
        name = name,
        teamId = "",
        imageUrl = imageUrl,
        position = position,
        nationality = nationality,
        age = age,
        marketValue = marketValue,
        description = description
    )
}