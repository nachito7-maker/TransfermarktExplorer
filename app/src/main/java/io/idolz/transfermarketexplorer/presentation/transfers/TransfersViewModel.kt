package io.idolz.transfermarketexplorer.presentation.transfers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.idolz.transfermarketexplorer.domain.repository.TransfermarketRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TransfersViewModel @Inject constructor(
    private val repository: TransfermarketRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TransfersState())
    val state: StateFlow<TransfersState> = _state.asStateFlow()

    init {
        getTransfers()
    }

    fun refresh() {
        getTransfers()
    }

    private fun getTransfers() {
        combine(
            repository.getRecentTransfers(),
            repository.getTopTransfers()
        ) { recent, top ->
            _state.value = _state.value.copy(
                recentTransfers = recent,
                topTransfers = top,
                isLoading = false,
                isRefreshing = false
            )
        }.onStart {
            _state.value = _state.value.copy(
                isLoading = _state.value.recentTransfers.isEmpty(),
                isRefreshing = _state.value.recentTransfers.isNotEmpty()
            )
        }.launchIn(viewModelScope)
    }
}