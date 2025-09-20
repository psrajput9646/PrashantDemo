package com.example.stocks.domain.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("userHolding" ) val userHolding : ArrayList<UserHolding>
)