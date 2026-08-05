package io.idolz.transfermarketexplorer.domain.use_case

import io.idolz.transfermarketexplorer.domain.repository.TransfermarketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val repository: TransfermarketRepository
) {
    operator fun invoke(playerId: String): Flow<Boolean> = repository.isPlayerFavorite(playerId)
}