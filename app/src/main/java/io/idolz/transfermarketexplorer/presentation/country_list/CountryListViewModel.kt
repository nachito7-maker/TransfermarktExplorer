package io.idolz.transfermarketexplorer.presentation.country_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.idolz.transfermarketexplorer.domain.use_case.GetCountriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CountryListState())
    val state: StateFlow<CountryListState> = _state.asStateFlow()

    init {
        getCountries()
    }

    private fun getCountries() {
        getCountriesUseCase()
            .onStart {
                _state.value = _state.value.copy(isLoading = true)
            }
            .onEach { countries ->
                _state.value = _state.value.copy(
                    countries = countries,
                    isLoading = false,
                    error = null
                )
            }
            .launchIn(viewModelScope)
    }
}