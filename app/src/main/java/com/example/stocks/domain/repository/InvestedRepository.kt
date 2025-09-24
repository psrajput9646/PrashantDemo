package com.example.stocks.domain.repository

import com.example.stocks.domain.model.UserHolding
import com.example.stocks.presentation.ui.utility.InvestedUiState
import kotlinx.coroutines.flow.Flow

interface InvestedRepository {
    fun getInvestedList(): Flow<InvestedUiState<List<UserHolding>>>
}