package io.idolz.transfermarketexplorer.domain.use_case

import io.idolz.transfermarketexplorer.domain.model.Player
import io.idolz.transfermarketexplorer.domain.repository.TransfermarketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: TransfermarketRepository
) {
    operator fun invoke(): Flow<List<Player>> = repository.getFavoritePlayers()
}