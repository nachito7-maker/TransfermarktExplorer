package io.idolz.transfermarketexplorer.presentation.player_list

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
class PlayerListViewModel @Inject constructor(
    private val repository: TransfermarketRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(PlayerListState())
    val state: StateFlow<PlayerListState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("teamId")?.let { teamId ->
            getPlayers(teamId)
        }
    }

    private fun getPlayers(teamId: String) {
        repository.getPlayers(teamId)
            .onStart {
                _state.value = _state.value.copy(isLoading = true)
            }
            .onEach { players ->
                _state.value = _state.value.copy(
                    players = players,
                    isLoading = false,
                    error = null
                )
            }
            .launchIn(viewModelScope)
    }
}