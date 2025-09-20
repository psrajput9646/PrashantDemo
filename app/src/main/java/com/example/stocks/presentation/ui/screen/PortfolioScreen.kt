package com.example.stocks.presentation.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
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
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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

    when (invested) {
        is InvestedUiState.Error -> {
            val message = (invested as InvestedUiState.Error).message
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = message, color = Color.Red)
            }
        }

        is InvestedUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is InvestedUiState.Success -> {
            val list = (invested as InvestedUiState.Success<List<UserHolding>>).data
            val sheetHeight = 200.dp
            val peekHeight = 50.dp

            val anchors = mapOf(
                0f to "Collapsed",
                with(LocalDensity.current) { (sheetHeight - peekHeight).toPx() } to "Expanded"
            )

            val swipeableState = rememberSwipeableState(initialValue = "Collapsed")//AnchoredDraggableState()

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column {
                    LazyColumn(modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 80.dp)) {
                        items(items = list) { item ->
                            PortfolioItem(item)
                            Divider()
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
                            Color.White,
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
                    val currentValue = remember { mutableDoubleStateOf(0.0) }
                    val totalInvestment = remember { mutableDoubleStateOf(0.0) }
                    val todayPfAndLs = remember { mutableDoubleStateOf(0.0) }
                    val pfAndLs = remember { mutableDoubleStateOf(0.0) }
                    LaunchedEffect(Unit) {
                        currentValue.doubleValue =
                            (invested as InvestedUiState.Success<List<UserHolding>>).data.sumOf { (it.ltp * it.quantity) }
                                .decimalInTwoPlace()
                        totalInvestment.doubleValue =
                            (invested as InvestedUiState.Success<List<UserHolding>>).data.sumOf { (it.avgPrice * it.quantity) }
                                .decimalInTwoPlace()
                        todayPfAndLs.doubleValue =
                            (invested as InvestedUiState.Success<List<UserHolding>>).data.sumOf { ((it.close - it.ltp) * it.quantity) }
                                .decimalInTwoPlace()
                        pfAndLs.doubleValue =
                            (currentValue.doubleValue - totalInvestment.doubleValue).decimalInTwoPlace()
                    }

                    LaunchedEffect(swipeableState.currentValue) {
                        state.value = swipeableState.currentValue
                    }


                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        val todayPfLsColor = if (todayPfAndLs.value >= 0) Color.Green else Color.Red
                        val pfLsColor = if (pfAndLs.doubleValue >= 0) Color.Green else Color.Red
                        if (state.value == "Collapsed") {
                            TitleAndValueAsRow(
                                stringResource(id = R.string.current_value),
                                currentValue.doubleValue.decimalInString()
                            ){}
                            Spacer(modifier = Modifier.height(20.dp))
                            TitleAndValueAsRow(
                                stringResource(id = R.string.total_investment),
                                totalInvestment.doubleValue.decimalInString()
                            ){}
                            Spacer(modifier = Modifier.height(20.dp))
                            TitleAndValueAsRow(
                                stringResource(id = R.string.today_profit_loss),
                                todayPfAndLs.doubleValue.decimalInString(),
                                colorValue = todayPfLsColor
                            ){}
                            Spacer(modifier = Modifier.height(14.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(14.dp))
                        }
                        TitleAndValueAsRow(
                            stringResource(id = R.string.profit_loss),
                            pfAndLs.doubleValue.decimalInString(),
                            true,
                            pfLsColor
                        ){}
                    }
                }
            }
        }
    }

}





