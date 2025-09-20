package com.example.stocks.data.remote

import com.example.stocks.domain.model.Data
import com.example.stocks.domain.model.StocksData
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface InvestedApi {
    @GET(".")//posts
    suspend fun getInvestedList(): StocksData
}

data class StocksData(val data: Data){
    fun toDomain() = Data(data.userHolding)
}

