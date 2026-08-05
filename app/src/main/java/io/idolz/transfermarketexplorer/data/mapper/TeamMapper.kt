package io.idolz.transfermarketexplorer.data.mapper

import io.idolz.transfermarketexplorer.data.local.entity.TeamEntity
import io.idolz.transfermarketexplorer.data.remote.dto.TeamDto
import io.idolz.transfermarketexplorer.domain.model.Team

fun TeamDto.toTeamEntity(leagueId: String): TeamEntity {
    return TeamEntity(
        id = id,
        name = name,
        leagueId = leagueId,
        logoUrl = logo,
        marketValue = marketValue
    )
}

fun TeamEntity.toTeam(): Team {
    return Team(
        id = id,
        name = name,
        leagueId = leagueId,
        logoUrl = logoUrl,
        marketValue = marketValue
    )
}