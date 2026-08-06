package io.idolz.transfermarketexplorer.data.mapper

import io.idolz.transfermarketexplorer.data.local.entity.TeamEntity
import io.idolz.transfermarketexplorer.data.remote.dto.TeamDto
import io.idolz.transfermarketexplorer.domain.model.Team

fun TeamDto.toTeamEntity(leagueId: String): TeamEntity {
    return TeamEntity(
        id = idTeam,
        name = strTeam,
        leagueId = leagueId,
        logoUrl = strBadge ?: strLogo ?: strTeamBadge ?: "",
        marketValue = null 
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