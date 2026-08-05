package io.idolz.transfermarketexplorer.presentation.country_list

import io.idolz.transfermarketexplorer.domain.model.Country

data class CountryListState(
    val countries: List<Country> = emptyList(),
    val filteredCountries: List<Country> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val error: String? = null
)