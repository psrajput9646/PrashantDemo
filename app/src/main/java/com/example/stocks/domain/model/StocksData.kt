package com.example.stocks.domain.model

import com.google.gson.annotations.SerializedName

data class StocksData(@SerializedName("data" ) val data : Data)