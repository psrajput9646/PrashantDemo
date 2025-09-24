package com.example.stocks.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stocks.R
import com.example.stocks.domain.model.UserHolding
import com.example.stocks.presentation.ui.utility.InvestedUiState
import com.example.stocks.presentation.ui.utility.PortfolioItem
import com.example.stocks.presentation.ui.utility.TitleAndValueAsRow
import com.example.stocks.presentation.ui.utility.decimalInString
import com.example.stocks.presentation.ui.utility.decimalInTwoPlace
import com.example.stocks.presentation.viewmodel.InvestedViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PortfolioScreen(viewModel: InvestedViewModel = hiltViewModel()) {
    val invested by viewModel.invested.collectAsState()
   // val invested by viewModel.investedUiState.collectAsState()

    when (invested) {
        is InvestedUiState.Error -> {
            ErrorScreen((invested as InvestedUiState.Error).message)

        }

        is InvestedUiState.Loading -> {
            LoadingScreen()
        }

        is InvestedUiState.Success -> {
            val list = (invested as InvestedUiState.Success<List<UserHolding>>).data
            val sheetHeight = 200.dp
            val peekHeight = 50.dp

            val anchors = mapOf(
                0f to "Collapsed",
                with(LocalDensity.current) { (sheetHeight - peekHeight).toPx() } to "Expanded"
            )

            val swipeableState =
                rememberSwipeableState(initialValue = "Collapsed")//AnchoredDraggableState()

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
                        items(items = list) { item ->
                            PortfolioItem(item)
                            HorizontalDivider(
                                Modifier,
                                DividerDefaults.Thickness,
                                DividerDefaults.color
                            )
                        }
                    }
                }
                // Bottom sheet
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(sheetHeight)
                        .align(Alignment.BottomCenter)
                        .offset { IntOffset(0, swipeableState.offset.value.roundToInt()) }
                        .background(
                            if (isSystemInDarkTheme()) Color.Black else Color.White,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .swipeable(
                            state = swipeableState,
                            anchors = anchors,
                            orientation = Orientation.Vertical,
                            reverseDirection = false
                        )
                ) {
                    val state = remember { mutableStateOf(swipeableState.currentValue) }
                    LaunchedEffect(swipeableState.currentValue) {
                        state.value = swipeableState.currentValue
                    }
                    PortfolioSummary(list, state.value)
                }
            }
        }
    }

}


@Composable
private fun ErrorScreen(message: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message, color = Color.Red)
    }
}

@Composable
private fun LoadingScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun PortfolioSummary(list: List<UserHolding>, state: String) {
    // calculate values
    val currentValue = list.sumOf { it.ltp * it.quantity }.decimalInTwoPlace()
    val totalInvestment = list.sumOf { it.avgPrice * it.quantity }.decimalInTwoPlace()
    val todayPfAndLs = list.sumOf { (it.close - it.ltp) * it.quantity }.decimalInTwoPlace()
    val pfAndLs = (currentValue - totalInvestment).decimalInTwoPlace()

    val todayPfLsColor = if (todayPfAndLs >= 0) Color.Green else Color.Red
    val pfLsColor = if (pfAndLs >= 0) Color.Green else Color.Red

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (state == "Collapsed") {
            TitleAndValueAsRow(
                title = stringResource(id = R.string.current_value),
                value = currentValue.decimalInString()
            ) {}
            Spacer(Modifier.height(12.dp))

            TitleAndValueAsRow(
                stringResource(id = R.string.total_investment),
                totalInvestment.decimalInString()
            ) {}
            Spacer(Modifier.height(12.dp))

            TitleAndValueAsRow(
                stringResource(id = R.string.today_profit_loss),
                todayPfAndLs.decimalInString(),
                colorValue = todayPfLsColor
            ) {}
            Spacer(Modifier.height(12.dp))
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            Spacer(Modifier.height(12.dp))
        }
        TitleAndValueAsRow(
            stringResource(id = R.string.profit_loss),
            pfAndLs.decimalInString(),
            status = true,
            colorValue = pfLsColor
        ) {}
    }
}




