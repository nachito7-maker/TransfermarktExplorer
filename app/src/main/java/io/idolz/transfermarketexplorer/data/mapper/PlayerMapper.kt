package io.idolz.transfermarketexplorer.data.mapper

import io.idolz.transfermarketexplorer.data.local.entity.FavoritePlayerEntity
import io.idolz.transfermarketexplorer.data.local.entity.PlayerEntity
import io.idolz.transfermarketexplorer.data.remote.dto.PlayerDto
import io.idolz.transfermarketexplorer.domain.model.Player

fun PlayerDto.toPlayerEntity(teamId: String): PlayerEntity {
    return PlayerEntity(
        id = id,
        name = name,
        teamId = teamId,
        imageUrl = image,
        position = position,
        nationality = nationality,
        age = age,
        marketValue = marketValue
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
        marketValue = marketValue
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
        marketValue = marketValue
    )
}

fun FavoritePlayerEntity.toPlayer(): Player {
    return Player(
        id = id,
        name = name,
        teamId = "", // Not needed for favorites list usually
        imageUrl = imageUrl,
        position = position,
        nationality = nationality,
        age = age,
        marketValue = marketValue
    )
}