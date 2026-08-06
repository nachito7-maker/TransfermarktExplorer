package io.idolz.transfermarketexplorer.presentation.league_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.idolz.transfermarketexplorer.domain.use_case.GetLeaguesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class LeagueListViewModel @Inject constructor(
    private val getLeaguesUseCase: GetLeaguesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(LeagueListState())
    val state: StateFlow<LeagueListState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("countryId")?.let { countryId ->
            _state.value = _state.value.copy(countryId = countryId)
            getLeagues(countryId)
        }
    }

    fun refresh() {
        getLeagues(_state.value.countryId)
    }

    private fun getLeagues(countryId: String) {
        if (countryId.isBlank()) return
        getLeaguesUseCase(countryId)
            .onStart {
                _state.value = _state.value.copy(
                    isLoading = _state.value.leagues.isEmpty(),
                    isRefreshing = _state.value.leagues.isNotEmpty()
                )
            }
            .onEach { leagues ->
                _state.value = _state.value.copy(
                    leagues = leagues,
                    isLoading = false,
                    isRefreshing = false,
                    error = null // No mostramos error automático aquí para evitar falsos positivos
                )
            }
            .launchIn(viewModelScope)
    }
}