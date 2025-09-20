package com.example.stocks.data.repository

import android.util.Log
import com.example.stocks.data.local.InvestedDao
import com.example.stocks.data.local.UserHoldingEntity
import com.example.stocks.data.remote.InvestedApi
import com.example.stocks.domain.model.UserHolding
import com.example.stocks.domain.repository.InvestedRepository
import com.example.stocks.presentation.ui.utility.InvestedUiState
import com.example.stocks.presentation.ui.utility.decimalInTwoPlace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class InvestedRepositoryImpl @Inject constructor(
    private val api: InvestedApi,
    private val dao: InvestedDao
) : InvestedRepository {

    override suspend fun getInvestedList(): Flow<InvestedUiState<List<UserHolding>>> = flow {
        emit(InvestedUiState.Loading())
        try {
            val remoteData = api.getInvestedList()
            val entities = remoteData.data.userHolding.map { holding ->
                UserHoldingEntity(
                    avgPrice = holding.avgPrice,
                    ltp = holding.ltp,
                    close = holding.close,
                    symbol = holding.symbol,
                    quantity = holding.quantity,
                    pnl = ((holding.ltp * holding.quantity)-(holding.avgPrice*holding.quantity)).decimalInTwoPlace()
                )
            }
            if (entities.isNotEmpty()) dao.deleteAll()
            // save in DB
            dao.insertAll(entities.ifEmpty { emptyList() })
            val cachedPosts = dao.getInvestedList().map { list -> list.map { it.toDomain() } }
            emit(InvestedUiState.Success(( cachedPosts.first() as List<UserHolding>)))
        } catch (e: Exception) {
            val cachedPosts = dao.getInvestedList().map { list -> list.map { it.toDomain() } }
            //emit(InvestedUiState.Error("Failed to load posts: ${e.localizedMessage}"))
            emit(InvestedUiState.Success(( cachedPosts.first() as List<UserHolding>)))
        }
    }

}
