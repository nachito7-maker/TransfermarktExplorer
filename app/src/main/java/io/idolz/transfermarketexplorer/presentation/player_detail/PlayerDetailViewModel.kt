package io.idolz.transfermarketexplorer.presentation.player_detail

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
class PlayerDetailViewModel @Inject constructor(
    private val repository: TransfermarketRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(PlayerDetailState())
    val state: StateFlow<PlayerDetailState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("playerId")?.let { playerId ->
            getPlayerDetails(playerId)
        }
    }

    private fun getPlayerDetails(playerId: String) {
        repository.getPlayerDetails(playerId)
            .onStart {
                _state.value = _state.value.copy(isLoading = true)
            }
            .onEach { player ->
                _state.value = _state.value.copy(
                    player = player,
                    isLoading = false,
                    error = null
                )
            }
            .launchIn(viewModelScope)
    }
}