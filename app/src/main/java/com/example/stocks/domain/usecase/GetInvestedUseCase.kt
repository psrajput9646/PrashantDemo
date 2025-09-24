package com.example.stocks.domain.usecase

import com.example.stocks.di.IoDispatcher
import com.example.stocks.domain.model.UserHolding
import com.example.stocks.domain.repository.InvestedRepository
import com.example.stocks.presentation.ui.utility.InvestedUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetInvestedUseCase @Inject constructor(private val investedRepository: InvestedRepository,
                                             @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
     operator fun invoke(): Flow<InvestedUiState<List<UserHolding>>> {
       return investedRepository.getInvestedList().flowOn(dispatcher)}
}