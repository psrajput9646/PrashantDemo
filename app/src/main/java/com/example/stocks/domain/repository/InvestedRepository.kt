package com.example.stocks.domain.repository

import com.example.stocks.data.local.UserHoldingEntity
import com.example.stocks.domain.model.StocksData
import com.example.stocks.domain.model.UserHolding
import com.example.stocks.presentation.ui.utility.InvestedUiState
import kotlinx.coroutines.flow.Flow

interface InvestedRepository {
   suspend fun getInvestedList(): Flow<InvestedUiState<List<UserHolding>>>
}