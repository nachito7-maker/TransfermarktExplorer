package io.idolz.transfermarketexplorer.data.mapper

import io.idolz.transfermarketexplorer.data.local.entity.LeagueEntity
import io.idolz.transfermarketexplorer.data.remote.dto.LeagueDto
import io.idolz.transfermarketexplorer.domain.model.League

fun LeagueDto.toLeagueEntity(countryId: String): LeagueEntity {
    return LeagueEntity(
        id = id,
        name = name,
        countryId = countryId,
        logoUrl = logo
    )
}

fun LeagueEntity.toLeague(): League {
    return League(
        id = id,
        name = name,
        countryId = countryId,
        logoUrl = logoUrl
    )
}