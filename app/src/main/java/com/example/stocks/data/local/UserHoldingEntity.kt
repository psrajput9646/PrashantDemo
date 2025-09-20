package com.example.stocks.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.stocks.domain.model.UserHolding

@Entity(tableName = "invested")
class UserHoldingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    val symbol   : String ,
    val quantity : Int    ,
    val ltp      : Double ,
    val avgPrice : Double ,
    val close    : Double ,
    val pnl      : Double
) {
    fun toDomain() = UserHolding(symbol, quantity, ltp, avgPrice, close, pnl)
}