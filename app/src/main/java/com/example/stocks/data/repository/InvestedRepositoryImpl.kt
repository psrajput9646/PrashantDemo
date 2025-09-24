package com.example.stocks.data.repository

import com.example.stocks.data.local.InvestedDao
import com.example.stocks.data.local.UserHoldingEntity
import com.example.stocks.data.remote.InvestedApi
import com.example.stocks.domain.model.UserHolding
import com.example.stocks.domain.repository.InvestedRepository
import com.example.stocks.presentation.ui.utility.InvestedUiState
import com.example.stocks.presentation.ui.utility.decimalInTwoPlace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InvestedRepositoryImpl @Inject constructor(
    private val api: InvestedApi,
    private val dao: InvestedDao
) : InvestedRepository {

    override fun getInvestedList(): Flow<InvestedUiState<List<UserHolding>>> = flow {
        emit(InvestedUiState.Loading)

        try {
            // 1. Fetch remote
            val remoteData = api.getInvestedList()
            val entities = remoteData.data.userHolding.map { holding ->
                UserHoldingEntity(
                    avgPrice = holding.avgPrice,
                    ltp = holding.ltp,
                    close = holding.close,
                    symbol = holding.symbol,
                    quantity = holding.quantity,
                    pnl = ((holding.ltp * holding.quantity) -
                            (holding.avgPrice * holding.quantity)).decimalInTwoPlace()
                )
            }

            // 2. Save in DB (replace all only if data is not empty)
            if (entities.isNotEmpty()) {
                dao.deleteAll()
                dao.insertAll(entities)
            }

            // 3. Always emit from DB (acts as single source of truth)
            dao.getInvestedList()
                .map { list -> list.map { it.toDomain() } }
                .collect { domainList ->
                    emit(InvestedUiState.Success(domainList))
                }

        } catch (e: Exception) {
            // On error → fallback to cached DB
            dao.getInvestedList()
                .map { list -> list.map { it.toDomain() } }
                .collect { cachedList ->
                    if (cachedList.isNotEmpty()) {
                        emit(InvestedUiState.Success(cachedList))
                    } else {
                        emit(InvestedUiState.Error("Failed to load: ${e.localizedMessage}"))
                    }
                }
        }
    }
}

