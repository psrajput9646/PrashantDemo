package com.example.stocks.presentation.ui.utility

import java.text.DecimalFormat
import kotlin.math.abs


fun Double.decimalInTwoPlace(): Double {
    return DecimalFormat("#.##").format(this).toDouble()
}

fun Double.decimalInString(): String {
    return if (this>0) "₹ $this" else "-₹ ${abs(this)}"
}
