package io.idolz.transfermarketexplorer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey val id: String,
    val name: String,
    val leagueId: String,
    val logoUrl: String,
    val marketValue: String? = null
)