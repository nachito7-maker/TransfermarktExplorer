package io.idolz.transfermarketexplorer.presentation.team_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.idolz.transfermarketexplorer.domain.repository.TransfermarketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class TeamListViewModel @Inject constructor(
    private val repository: TransfermarketRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(TeamListState())
    val state: StateFlow<TeamListState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("leagueId")?.let { leagueId ->
            getTeams(leagueId)
        }
    }

    private fun getTeams(leagueId: String) {
        repository.getTeams(leagueId)
            .onStart {
                _state.value = _state.value.copy(isLoading = true)
            }
            .onEach { teams ->
                _state.value = _state.value.copy(
                    teams = teams,
                    isLoading = false,
                    error = null
                )
            }
            .launchIn(viewModelScope)
    }
}