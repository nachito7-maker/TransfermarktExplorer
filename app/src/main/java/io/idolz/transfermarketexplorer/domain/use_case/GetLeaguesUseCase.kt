package io.idolz.transfermarketexplorer.domain.use_case

import io.idolz.transfermarketexplorer.domain.model.League
import io.idolz.transfermarketexplorer.domain.repository.TransfermarketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLeaguesUseCase @Inject constructor(
    private val repository: TransfermarketRepository
) {
    operator fun invoke(countryId: String): Flow<List<League>> = repository.getLeagues(countryId)
}