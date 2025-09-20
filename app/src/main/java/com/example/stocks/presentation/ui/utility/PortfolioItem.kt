package com.example.stocks.presentation.ui.utility

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stocks.R
import com.example.stocks.domain.model.UserHolding

@Composable
fun PortfolioItem(holding: UserHolding) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(holding.symbol, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(25.dp))
            TitleAndValue(stringResource(id = R.string.net_qty)+stringResource(id = R.string.colon),"${holding.quantity}")
        }
        Column(horizontalAlignment = Alignment.End) {
            val pnlColor = if (holding.pnl >= 0) Color.Green else Color.Red
            TitleAndValue(stringResource(id = R.string.ltp)+stringResource(id = R.string.colon),"₹${holding.ltp}")
            Spacer(modifier = Modifier.height(25.dp))
            TitleAndValue(stringResource(id = R.string.p_l)+stringResource(id = R.string.colon),holding.pnl.decimalInString(),pnlColor)
        }
    }
}