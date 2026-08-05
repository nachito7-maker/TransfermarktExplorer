package io.idolz.transfermarketexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.idolz.transfermarketexplorer.data.local.entity.CountryEntity
import io.idolz.transfermarketexplorer.data.local.entity.LeagueEntity
import io.idolz.transfermarketexplorer.data.local.entity.TeamEntity

@Database(
    entities = [CountryEntity::class, LeagueEntity::class, TeamEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TransfermarketDatabase : RoomDatabase() {
    abstract val countryDao: CountryDao
    abstract val leagueDao: LeagueDao
    abstract val teamDao: TeamDao

    companion object {
        const val DATABASE_NAME = "transfermarket_db"
    }
}