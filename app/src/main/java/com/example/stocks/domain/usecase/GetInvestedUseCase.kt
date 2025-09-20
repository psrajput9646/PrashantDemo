package com.example.stocks.domain.usecase

import com.example.stocks.data.local.UserHoldingEntity
import com.example.stocks.domain.model.Data
import com.example.stocks.domain.model.StocksData
import com.example.stocks.domain.model.UserHolding
import com.example.stocks.domain.repository.InvestedRepository
import com.example.stocks.presentation.ui.utility.InvestedUiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInvestedUseCase @Inject constructor(private val investedRepository: InvestedRepository) {
    suspend operator fun invoke(): Flow<InvestedUiState<List<UserHolding>>> = investedRepository.getInvestedList()
}