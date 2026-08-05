package io.idolz.transfermarketexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.idolz.transfermarketexplorer.data.local.entity.CountryEntity
import io.idolz.transfermarketexplorer.data.local.entity.LeagueEntity

@Database(
    entities = [CountryEntity::class, LeagueEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TransfermarketDatabase : RoomDatabase() {
    abstract val countryDao: CountryDao
    abstract val leagueDao: LeagueDao

    companion object {
        const val DATABASE_NAME = "transfermarket_db"
    }
}