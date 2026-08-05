package io.idolz.transfermarketexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.idolz.transfermarketexplorer.data.local.entity.CountryEntity

@Database(
    entities = [CountryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TransfermarketDatabase : RoomDatabase() {
    abstract val countryDao: CountryDao

    companion object {
        const val DATABASE_NAME = "transfermarket_db"
    }
}