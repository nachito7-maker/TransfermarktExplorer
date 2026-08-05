package io.idolz.transfermarketexplorer.presentation.transfers

import io.idolz.transfermarketexplorer.domain.model.Transfer

data class TransfersState(
    val recentTransfers: List<Transfer> = emptyList(),
    val topTransfers: List<Transfer> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
)