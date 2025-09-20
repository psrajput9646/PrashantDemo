package com.example.stocks.presentation.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stocks.presentation.ui.screen.FundsScreen
import com.example.stocks.presentation.ui.screen.InvestScreen
import com.example.stocks.presentation.ui.screen.OrdersScreen
import com.example.stocks.presentation.ui.screen.PortfolioScreen
import com.example.stocks.presentation.ui.screen.WatchListScreen
import com.example.stocks.presentation.ui.utility.Screen

@Composable
fun NavGraphControllerScreen(navController: NavHostController, route: String) {
    NavHost(
        navController = navController,
        startDestination = Screen.Portfolio.route,
    ) {
        composable(Screen.Portfolio.route) { PortfolioScreen() }
        composable(Screen.WatchList.route) { WatchListScreen() }
        composable(Screen.Orders.route) { OrdersScreen() }
        composable(Screen.Funds.route) { FundsScreen() }
        composable(Screen.Invest.route) { InvestScreen() }
    }
}