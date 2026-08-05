package io.idolz.transfermarketexplorer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey val id: String,
    val name: String,
    val teamId: String,
    val imageUrl: String,
    val position: String,
    val nationality: String,
    val age: Int,
    val marketValue: String
)