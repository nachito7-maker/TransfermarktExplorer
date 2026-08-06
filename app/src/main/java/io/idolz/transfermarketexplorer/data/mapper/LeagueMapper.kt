package io.idolz.transfermarketexplorer.data.mapper

import io.idolz.transfermarketexplorer.data.local.entity.LeagueEntity
import io.idolz.transfermarketexplorer.data.remote.dto.LeagueDto
import io.idolz.transfermarketexplorer.domain.model.League

fun LeagueDto.toLeagueEntity(countryId: String): LeagueEntity {
    return LeagueEntity(
        id = idLeague,
        name = strLeague,
        countryId = countryId,
        logoUrl = strBadge ?: ""
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