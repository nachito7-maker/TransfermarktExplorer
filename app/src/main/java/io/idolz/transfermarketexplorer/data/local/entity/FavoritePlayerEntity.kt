package io.idolz.transfermarketexplorer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_players")
data class FavoritePlayerEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String,
    val position: String,
    val nationality: String,
    val age: Int,
    val marketValue: String,
    val description: String? = null
)