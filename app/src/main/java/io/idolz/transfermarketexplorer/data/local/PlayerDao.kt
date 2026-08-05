package io.idolz.transfermarketexplorer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.idolz.transfermarketexplorer.data.local.entity.FavoritePlayerEntity
import io.idolz.transfermarketexplorer.data.local.entity.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Query("SELECT * FROM players WHERE teamId = :teamId")
    fun getPlayers(teamId: String): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM players WHERE id = :playerId")
    fun getPlayerById(playerId: String): Flow<PlayerEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayers(players: List<PlayerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: PlayerEntity)

    // Favorites
    @Query("SELECT * FROM favorite_players")
    fun getFavoritePlayers(): Flow<List<FavoritePlayerEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_players WHERE id = :playerId)")
    fun isPlayerFavorite(playerId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritePlayer(player: FavoritePlayerEntity)

    @Query("DELETE FROM favorite_players WHERE id = :playerId")
    suspend fun deleteFavoritePlayer(playerId: String)
}