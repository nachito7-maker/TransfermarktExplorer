package io.idolz.transfermarketexplorer.domain.use_case

import io.idolz.transfermarketexplorer.domain.model.Country
import io.idolz.transfermarketexplorer.domain.repository.TransfermarketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val repository: TransfermarketRepository
) {
    operator fun invoke(): Flow<List<Country>> = repository.getCountries()
}