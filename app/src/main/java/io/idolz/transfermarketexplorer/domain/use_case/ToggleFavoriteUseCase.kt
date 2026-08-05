package io.idolz.transfermarketexplorer.domain.use_case

import io.idolz.transfermarketexplorer.domain.model.Player
import io.idolz.transfermarketexplorer.domain.repository.TransfermarketRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: TransfermarketRepository
) {
    suspend operator fun invoke(player: Player) = repository.toggleFavorite(player)
}