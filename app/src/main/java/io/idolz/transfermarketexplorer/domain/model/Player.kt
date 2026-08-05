package io.idolz.transfermarketexplorer.domain.model

data class Player(
    val id: String,
    val name: String,
    val teamId: String,
    val position: String,
    val nationality: String,
    val age: Int,
    val marketValue: String,
    val imageUrl: String
)